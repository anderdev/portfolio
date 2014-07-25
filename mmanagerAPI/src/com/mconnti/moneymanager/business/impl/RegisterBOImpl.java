package com.mconnti.moneymanager.business.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.RegisterBO;
import com.mconnti.moneymanager.business.UserBO;
import com.mconnti.moneymanager.entity.CreditCard;
import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.Parcel;
import com.mconnti.moneymanager.entity.Register;
import com.mconnti.moneymanager.entity.TypeAccount;
import com.mconnti.moneymanager.entity.TypeClosure;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.CreditCardDAO;
import com.mconnti.moneymanager.persistence.CurrencyDAO;
import com.mconnti.moneymanager.persistence.DescriptionDAO;
import com.mconnti.moneymanager.persistence.ParcelDAO;
import com.mconnti.moneymanager.persistence.RegisterDAO;
import com.mconnti.moneymanager.persistence.TypeClosureDAO;
import com.mconnti.moneymanager.utils.Constants;
import com.mconnti.moneymanager.utils.MessageFactory;
import com.mconnti.moneymanager.utils.Utils;

public class RegisterBOImpl extends GenericBOImpl<Register> implements RegisterBO {

	@Autowired
	private RegisterDAO registerDAO;

	@Autowired
	private CurrencyDAO currencyDAO;

	@Autowired
	private DescriptionDAO descriptionDAO;

	@Autowired
	private TypeClosureDAO typeClosureDAO;

	@Autowired
	private ParcelDAO parcelDAO;

	@Autowired
	private CreditCardDAO creditCardDAO;

	@Autowired
	private UserBO userBO;
	
	private User getSuperUser(Register register) {
		return userBO.getSuperUser(register.getUser());
	}

	private CreditCard getCreditCard(Register debit) {
		try {
			return creditCardDAO.findById(CreditCard.class, debit.getCreditCard().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void setCreditCardDate(Register register) {
		CreditCard creditCard = getCreditCard(register);
		if (register.getId() == null) {
			setupDate(register, creditCard);
		}
	}

	private void setupDate(Register register, CreditCard creditCard) {
		String[] temp = Utils.dateToString(register.getDate()).split("/");
		Integer dayOfBuy = Integer.parseInt(temp[0]);
		Integer monthOfBuy = Integer.parseInt(temp[1]);
		Integer yearOfBuy = Integer.parseInt(temp[2]);
		if ((dayOfBuy < creditCard.getPayday()) || (dayOfBuy > creditCard.getPayday() && dayOfBuy < creditCard.getLastDayToBuy())) {
			register.setDate(Utils.stringToDate(creditCard.getPayday().toString() + "/" + (monthOfBuy + 1) + "/" + yearOfBuy.toString(), false));
		} else {
			register.setDate(Utils.stringToDate(creditCard.getPayday().toString() + "/" + (monthOfBuy + 1) + "/" + yearOfBuy.toString(), false));
		}
	}

	private void setParcel(Register register, Date currentDate, Integer interval, Integer x) {
		Date newDate = currentDate;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(newDate);
		calendar.add(interval, x);
		register.setDate(calendar);
	}

	@Override
	@Transactional
	public MessageReturn save(Register register) {
		MessageReturn libReturn = new MessageReturn();
		User user = getSuperUser(register);
		if (user != null && register.getCurrency() != null && register.getSuperGroup() != null) {
			try {
				if (register.getCreditCard() != null) {
					Map<String, String> queryParams = new LinkedHashMap<>();
					queryParams.put(" where x.type = ", "'"+MessageFactory.getMessage("lb_monthly", user.getLanguage())+"'");
					TypeClosure typeClosure = typeClosureDAO.findByParameter(TypeClosure.class, queryParams);
					register.setTypeClosure(typeClosure);
					setCreditCardDate(register);
				}
				
				if(register.getId() == null){
					register.setClosed(false);
				}

				if (register.getMultipleParcel() != null && register.getMultipleParcel()) {
					if(register.getId() == null){
						Parcel parcel = new Parcel();
						parcel.setParcels(register.getNumberParcel());
						parcel.setUser(user);
						register.setParcel(parcelDAO.save(parcel));
						register.setTypeClosure(typeClosureDAO.findById(TypeClosure.class, register.getTypeClosure().getId()));
						Date currentDate = register.getDate().getTime();

						for (int x = 0; x < register.getNumberParcel(); x++) {
							if (register.getTypeClosure().getType().toLowerCase().equals(Constants.ANUAL) || register.getTypeClosure().getType().toLowerCase().equals(Constants.YEARLY)) {
								setParcel(register, currentDate, Calendar.YEAR, x);
							} else if (register.getTypeClosure().getType().toLowerCase().equals(Constants.MENSAL) || register.getTypeClosure().getType().toLowerCase().equals(Constants.MONTHLY)) {
								setParcel(register, currentDate, Calendar.MONTH, x);
							} else if (register.getTypeClosure().getType().toLowerCase().equals(Constants.QUINZENAL) || register.getTypeClosure().getType().toLowerCase().equals(Constants.FORTNIGHTLY)) {
								setParcel(register, currentDate, Calendar.DAY_OF_WEEK, x * 14);
							} else if (register.getTypeClosure().getType().toLowerCase().equals(Constants.SEMANAL) || register.getTypeClosure().getType().toLowerCase().equals(Constants.WEEKLY)) {
								setParcel(register, currentDate, Calendar.WEEK_OF_MONTH, x);
							} else if (register.getTypeClosure().getType().toLowerCase().equals(Constants.DIARIO) || register.getTypeClosure().getType().toLowerCase().equals(Constants.DAILY)) {
								setParcel(register, currentDate, Calendar.DAY_OF_WEEK, x);
							}
							//save description created on the register form
							register = saveDescription(register);
							
							register.setCurrentDate(Calendar.getInstance());
							saveGeneric(register);
						}
					} else {
						Map<String, String> queryParams = new LinkedHashMap<>();
						queryParams.put(" where "," 1=1 ");
						queryParams.put(" and x.user = ", user.getId()+"");
						queryParams.put(" and x.parcel = ", user.getId()+"");
						
						List<Register> registerParcels = list(Register.class, queryParams, " x.date");
						
						for (Register reg : registerParcels) {
							Long id = reg.getId();
							Calendar date = reg.getDate();
							reg = register;
							reg.setId(id);
							reg.setDate(date);
							//save description created on the register form
							reg = saveDescription(register);
							reg.setCurrentDate(Calendar.getInstance());
							saveGeneric(reg);
						}
					}
				} else {
					//save description created on the register form
					register = saveDescription(register);
					
					register.setCurrentDate(Calendar.getInstance());
					saveGeneric(register);
				}
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setRegister(register);
				libReturn.setMessage(e.getMessage());
			}
			if (libReturn.getMessage() == null && register.getId() == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_debit_saved", register.getUser().getLanguage()));
				libReturn.setRegister(register);
			} else if (libReturn.getMessage() == null && register.getId() != null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_debit_updated", register.getUser().getLanguage()));
				libReturn.setRegister(register);
			}
		} else {
			libReturn.setMessage(MessageFactory.getMessage("lb_debit_not_found", "en"));
		}

		return libReturn;
	}
	
	private Register saveDescription(Register register) throws Exception{
		if(register.getDescription() != null && register.getDescription().getId() == null){
			register.getDescription().setUser(getSuperUser(register));
			register.setDescription(descriptionDAO.save(register.getDescription()));
		} 
		if(register.getGroup() != null && register.getGroup().getId() == null){
			register.getGroup().setUser(getSuperUser(register));
			register.setGroup(descriptionDAO.save(register.getGroup()));
		} 
		if(register.getSuperGroup() != null && register.getSuperGroup().getId() == null) {
			register.getSuperGroup().setUser(getSuperUser(register));
			register.setSuperGroup(descriptionDAO.save(register.getSuperGroup()));
		}
		return register;
	}

	@Override
	public List<Register> list(Register register) throws Exception {
		Map<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put(" where x.user.id = ", getSuperUser(register).getId()+"");
		return list(Register.class, queryParams, " x.date ");
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Register debit = null;
		try {
			debit = findById(Register.class, id);
			if (debit == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_debit_not_found", "en"));
			} else {
				String locale = debit.getUser().getLanguage();
				remove(debit);
				libReturn.setMessage(MessageFactory.getMessage("lb_debit_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}
	
	private String addAndOr(Boolean isAnd){
		String andOr = "";
		if(isAnd){
			andOr = "and";
		}else{
			andOr = "or";
		}
		return andOr;
	}

	@Override
	public List<Register> listByParameter(Register register) throws Exception {
		
		User user = getSuperUser(register);
		
		Map<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put(" where "," 1=1 ");
		queryParams.put(" and x.user = ", user.getId()+"");
		
		if( register.getTypeAccount() != null && register.getTypeAccount().getId() != null && register.getTypeAccount().getId() > 0){
			queryParams.put(" and x.typeAccount = ", register.getTypeAccount().getId()+ "");
		}
		
		
		Boolean isAnd = true;
		
		
		if (register.getSearch() != null && register.getSearch()){
			if(register.getDescription() != null && register.getDescription().getId() != null  && register.getDescription().getId() > 0){
				queryParams.put(" "+addAndOr(isAnd)+"  x.description = ", register.getDescription().getId()+ "");
				isAnd = false;
			}
			if(register.getGroup() != null && register.getGroup().getId() != null  && register.getGroup().getId() > 0){
				queryParams.put(" "+addAndOr(isAnd)+"   x.group = ", register.getGroup().getId()+ "");
				isAnd = false;
			}
			if(register.getSuperGroup() != null && register.getSuperGroup().getId() != null  && register.getSuperGroup().getId() > 0){
				queryParams.put(" "+addAndOr(isAnd)+"  x.superGroup = ", register.getSuperGroup().getId()+ "");
				isAnd = false;
			}
			
			if(register.getCreditCard() != null && register.getCreditCard().getId() != null && register.getCreditCard().getId() > 0){
				queryParams.put(" "+addAndOr(isAnd)+"  x.creditCard = ", register.getCreditCard().getId()+ "");
				isAnd = false;
			}
			if(register.getCurrency() != null && register.getCurrency().getId() != null && register.getCurrency().getId() > 0){
				queryParams.put(" "+addAndOr(isAnd)+"  x.currency = ", register.getCurrency().getId()+ "");
				isAnd = false;
			}
			
			if(register.getTypeClosure() != null && register.getTypeClosure().getId() != null && register.getTypeClosure().getId() > 0){
				queryParams.put(" "+addAndOr(isAnd)+"  x.typeClosure = ", register.getTypeClosure().getId()+ "");
				isAnd = false;
			}
			
			if(register.getStrDate() != null && register.getDate() == null){
				queryParams.put(" and x.date = ", " str_to_date('"+Utils.dateToString(register.getStrDate())+ "', '%d/%m/%Y')");
			}
			
			if(register.getStrDate() == null && register.getDate() != null){
				queryParams.put(" and x.date = ", " str_to_date('"+Utils.dateToString(register.getDate())+"', '%d/%m/%Y') ");
			}
			
			if(register.getStrDate() != null && register.getDate() != null){
				queryParams.put(" and x.date between ", " str_to_date('"+Utils.dateToString(register.getStrDate())+ "', '%d/%m/%Y') and str_to_date('"+Utils.dateToString(register.getDate())+"', '%d/%m/%Y') ");
			}
		}
		
		List<Register> list = list(Register.class, queryParams, " x.date desc");

		return list;
	}

	@Override
	public Register getByDescription(Map<String, String> request) {
		User user = userBO.getById(new Long(request.get("userId")));
		
		Register register = new Register();
		register.setUser(user);

		Long typeAccountId = new Long(request.get("typeAccountId"));
		Long descriptionId = new Long(request.get("descriptionId"));
		Description description;
		try {
			description = findById(Description.class, descriptionId);
			TypeAccount typeAccount = findById(TypeAccount.class, typeAccountId);
			register = registerDAO.getByDescription(description, getSuperUser(register), typeAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return register;
	}

}
