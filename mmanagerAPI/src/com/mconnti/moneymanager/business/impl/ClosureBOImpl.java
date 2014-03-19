package com.mconnti.moneymanager.business.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.ClosureBO;
import com.mconnti.moneymanager.business.DescriptionBO;
import com.mconnti.moneymanager.business.RegisterBO;
import com.mconnti.moneymanager.entity.Closure;
import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.Register;
import com.mconnti.moneymanager.entity.TypeAccount;
import com.mconnti.moneymanager.entity.TypeClosure;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.ClosureDAO;
import com.mconnti.moneymanager.persistence.RegisterDAO;
import com.mconnti.moneymanager.persistence.TypeAccountDAO;
import com.mconnti.moneymanager.persistence.UserDAO;
import com.mconnti.moneymanager.utils.Constants;
import com.mconnti.moneymanager.utils.Crypt;
import com.mconnti.moneymanager.utils.MessageFactory;
import com.mconnti.moneymanager.utils.Utils;

public class ClosureBOImpl extends GenericBOImpl<Closure> implements ClosureBO {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RegisterBO registerBO;
	
	@Autowired
	private RegisterDAO registerDAO;

	@Autowired
	private ClosureDAO closureDAO;

	@Autowired
	private DescriptionBO descriptionBO;

	@Autowired
	private TypeAccountDAO typeAccountDAO;

	private User getUser(Closure closure) {
		try {
			return userDAO.findById(User.class, closure.getUser().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Description getDescriptionByParameter(Map<String, String> queryParams) {
		try {
			return descriptionBO.findByParameter(Description.class, queryParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private TypeAccount getTypeAccountByParameter(Map<String, String> queryParams) {
		try {
			return typeAccountDAO.findByParameter(TypeAccount.class, queryParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public MessageReturn save(Closure closure) {
		MessageReturn libReturn = new MessageReturn();
		User user = getUser(closure);
		if (user != null) {
			try {

				closure.setUser(user);

				closeRegisters(closure, Utils.dateToString(closure.getStartDate()), Utils.dateToString(closure.getDate()));

				createRegister(closure, user);
				saveGeneric(closure);
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setClosure(closure);
				libReturn.setMessage(e.getMessage());
			}
			if (libReturn.getMessage() == null && closure.getId() == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_closure_saved", closure.getUser().getCity().getState().getCountry().getLocale()));
				libReturn.setClosure(closure);
			} else if (libReturn.getMessage() == null && closure.getId() != null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_closure_updated", closure.getUser().getCity().getState().getCountry().getLocale()));
				libReturn.setClosure(closure);
			}
		} else {
			libReturn.setMessage(MessageFactory.getMessage("lb_closure_not_found", "en"));
		}

		return libReturn;
	}

	private void createRegister(Closure closure, User user) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(closure.getDate());
		calendar.add(Calendar.DAY_OF_MONTH, +1);
		
		Map<String, String> queryParams = new LinkedHashMap<>();
		TypeAccount typeAccountRegister = null;
		TypeAccount typeAccount = null;

		Register register = new Register();
		register.setDate(calendar.getTime());
		
		String language = null;
		if (closure.getUser().getLanguage().equals("pt_BR")) {
			language = "pt_BR";
		} else {
			language = "en";
		}
		
		//Getting typeAccount for description
		queryParams.clear();
		if(closure.getTotalGeneral().compareTo(BigDecimal.ZERO) > 0){
			queryParams.put(" where x.description = ", "'"+MessageFactory.getMessage("lb_credit", language)+"'");
		} else{
			queryParams.put(" where x.description = ", "'"+MessageFactory.getMessage("lb_debit", language)+"'");
		}
		typeAccount = getTypeAccountByParameter(queryParams);
		typeAccountRegister = typeAccount;
		
		//getting description by typeAccount
		queryParams.clear();
		queryParams.put(" where x.description = ", "'"+Crypt.encrypt(MessageFactory.getMessage("lb_previous_month_balance", language))+"'");
		queryParams.put(" and x.typeAccount = ", typeAccount.getId()+"");
		Description description = getDescriptionByParameter(queryParams);
		
		//if description does not exist, then create
		if (description == null) {
			description = new Description();
			description.setDescription(MessageFactory.getMessage("lb_previous_month_balance", language));
			description.setTypeAccount(typeAccount);
			description.setUser(user);
		}
		
		//getting typeAccount for group
		queryParams.clear();
		queryParams.put(" where x.description = ", "'"+MessageFactory.getMessage("lb_group", language)+"'");
		typeAccount = getTypeAccountByParameter(queryParams);
		
		//getting group description by typeAccount
		queryParams.clear();
		queryParams.put(" where x.description = ", "'"+Crypt.encrypt(MessageFactory.getMessage("lb_closing_of_accounts", language))+"'");
		queryParams.put(" and x.typeAccount = ", typeAccount.getId()+"");
		Description group = getDescriptionByParameter(queryParams);
		
		// if group does not exist, then create
		if (group == null) {
			group = new Description();
			group.setDescription(MessageFactory.getMessage("lb_closing_of_accounts", language));
			group.setTypeAccount(typeAccount);
			group.setUser(user);
		}
		
		//getting typeAccount for super group
		queryParams.clear();
		queryParams.put(" where x.description = ", "'"+MessageFactory.getMessage("lb_super_group", language)+"'");
		typeAccount = getTypeAccountByParameter(queryParams);
		
		//getting super group description by typeAccount
		queryParams.clear();
		queryParams.put(" where x.description = ", "'"+Crypt.encrypt(MessageFactory.getMessage("lb_closing_of_accounts", language))+"'");
		queryParams.put(" and x.typeAccount = ", typeAccount.getId()+"");
		Description superGroup = getDescriptionByParameter(queryParams);
		
		// if super group does not exist, then create
		if (superGroup == null) {
			superGroup = new Description();
			superGroup.setDescription(MessageFactory.getMessage("lb_closing_of_accounts", language));
			superGroup.setTypeAccount(typeAccount);
			superGroup.setUser(user);
		}
		
		//create register
		register.setDescription(description);
		register.setGroup(group);
		register.setSuperGroup(superGroup);
		register.setNumberParcel(Constants.SINGLE_PARCEL);
		register.setTypeAccount(typeAccountRegister);
		register.setTypeClosure(closure.getTypeClosure());
		register.setCurrency(closure.getCurrency());
		register.setUser(closure.getUser());
		register.setAmount(closure.getTotalGeneral().compareTo(BigDecimal.ZERO) > 0 ? closure.getTotalGeneral() : new BigDecimal(closure.getTotalGeneral().toString().split("-")[1]) );
		registerBO.save(register);
	}

	private void closeRegisters(Closure closure, String startDate, String endDate) throws Exception {

		closure.setCreditsAlreadyClosed(closureDAO.getRegisters(closure.getUser(), startDate, endDate, true, Constants.TYPE_ACCOUNT_CREDIT));

		closure.setDebitsAlreadyClosed(closureDAO.getRegisters(closure.getUser(), startDate, endDate, true, Constants.TYPE_ACCOUNT_DEBIT));

		Collection<Register> collectionCredit = closureDAO.getRegisters(closure.getUser(), startDate, endDate, false, Constants.TYPE_ACCOUNT_CREDIT);
		
		Collection<Register> collectionDebit = closureDAO.getRegisters(closure.getUser(), startDate, endDate, false, Constants.TYPE_ACCOUNT_DEBIT);

		for (Register register : collectionCredit) {
			saveRegister(register, true);
		}
		
		for (Register register : collectionDebit) {
			saveRegister(register, true);
		}
	}
	
	private void saveRegister(Register register, Boolean closed) throws Exception{
		register.setClosed(closed);
		registerDAO.save(register);
	}

	public List<Closure> list() throws Exception {
		return list(Closure.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Closure closure = null;
		try {
			closure = findById(Closure.class, id);
			if (closure == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_closure_not_found", "en"));
			} else {
				String locale = closure.getUser().getCity().getState().getCountry().getLocale();
				remove(closure);
				libReturn.setMessage(MessageFactory.getMessage("lb_closure_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public Closure getById(Long id) {
		try {
			return findById(Closure.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public MessageReturn getValuesToClose(Closure closure) {
		MessageReturn libReturn = new MessageReturn();
		try {
			Date date = closure.getDate();

			TypeClosure typeClosure = findById(TypeClosure.class, closure.getTypeClosure().getId());
			
			HashMap<String, String> ret = new HashMap<String, String>();

			if (Constants.MENSAL.equalsIgnoreCase(typeClosure.getType().toLowerCase()) || Constants.MONTHLY.equalsIgnoreCase(typeClosure.getType().toLowerCase())) {

				ret = loadDates(date, Calendar.DAY_OF_MONTH, -Utils.getLastDayOfMonth(Utils.dateToString(date)));

			} else if (Constants.QUINZENAL.equalsIgnoreCase(typeClosure.getType().toLowerCase()) || Constants.FORTNIGHTLY.equalsIgnoreCase(typeClosure.getType().toLowerCase())) {

				ret = loadDates(date, Calendar.DAY_OF_MONTH, -14);

			} else if (Constants.SEMANAL.equalsIgnoreCase(typeClosure.getType().toLowerCase()) || Constants.WEEKLY.equalsIgnoreCase(typeClosure.getType().toLowerCase())) {

				ret = loadDates(date, Calendar.DAY_OF_MONTH, -6);

			} else if (Constants.DIARIO.equalsIgnoreCase(typeClosure.getType().toLowerCase()) || Constants.DAILY.equalsIgnoreCase(typeClosure.getType().toLowerCase())) {

				ret = loadDates(date, Calendar.DAY_OF_MONTH, 0);
			}

			closure = getClosureValues(closure, ret.get(Constants.DATE_START), ret.get(Constants.DATE_END));
			libReturn.setClosure(closure);
			
		} catch (Exception e) {
			libReturn.setClosure(null);
			libReturn.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return libReturn;
	}

	private HashMap<String, String> loadDates(Date date, Integer type, Integer days) {
		HashMap<String, String> map = new HashMap<String, String>();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, days+1);
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		String startDate = dataFormatada.format(calendar.getTime());
		calendar.setTime(date);
		String endDate = dataFormatada.format(calendar.getTime());

		map.put(Constants.DATE_START, startDate);
		map.put(Constants.DATE_END, endDate);
		return map;
	}

	private Closure getClosureValues(Closure closure, String startDate, String endDate) {
		BigDecimal sumCredit = new BigDecimal(0.00);
		BigDecimal sumDebit = new BigDecimal(0.00);
		

		closure.setCreditsAlreadyClosed(closureDAO.getRegisters(closure.getUser(), startDate, endDate, true, Constants.TYPE_ACCOUNT_CREDIT));

		closure.setDebitsAlreadyClosed(closureDAO.getRegisters(closure.getUser(), startDate, endDate, true, Constants.TYPE_ACCOUNT_DEBIT));

		Collection<Register> collectionCredit = closureDAO.getRegisters(closure.getUser(), startDate, endDate, false, Constants.TYPE_ACCOUNT_CREDIT);

		Collection<Register> collectionDebit = closureDAO.getRegisters(closure.getUser(), startDate, endDate, false, Constants.TYPE_ACCOUNT_DEBIT);

		for (Register debit : collectionDebit) { 
			sumDebit =sumDebit.add(debit.getAmount());
		}

		for (Register credit : collectionCredit) {
			sumCredit = sumCredit.add(credit.getAmount());
		}

		BigDecimal total = sumCredit.subtract(sumDebit);
		closure.setTotalCredit(sumCredit);
		closure.setTotalDebit(sumDebit);
		closure.setTotalGeneral(total);
		closure.setStartDate(Utils.stringToDate(startDate, true));
		closure.setEndDate(Utils.stringToDate(endDate, true));

		return closure;
	}

}
