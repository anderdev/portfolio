package com.mconnti.moneymanager.business.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.ClosureBO;
import com.mconnti.moneymanager.entity.Closure;
import com.mconnti.moneymanager.entity.Credit;
import com.mconnti.moneymanager.entity.Debit;
import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.TypeAccount;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.ClosureDAO;
import com.mconnti.moneymanager.persistence.CreditDAO;
import com.mconnti.moneymanager.persistence.DebitDAO;
import com.mconnti.moneymanager.persistence.DescriptionDAO;
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
	private CreditDAO creditDAO;

	@Autowired
	private DebitDAO debitDAO;
	
	@Autowired
	private ClosureDAO closureDAO;
	
	@Autowired
	private DescriptionDAO descriptionDAO;
	
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
	
	private Description getDescriptionByParameter(Map<String,String> queryParams){
		try {
			return descriptionDAO.findByParameter(Description.class, queryParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private TypeAccount getTypeAccountByParameter(Map<String,String> queryParams){
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

				closeValues(closure, Utils.dateToString(closure.getStartDate()), Utils.dateToString(closure.getDate()));

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(closure.getDate());
				calendar.add(Calendar.DAY_OF_MONTH, +1);
				
				Map<String,String> queryParams = new HashMap<>();
				
				if (closure.getTotalGeneral() > 0) {
					Credit credit = new Credit();
					credit.setDate(calendar.getTime());
					if (closure.getUser().getLanguage().equals("pt_BR")) {
						queryParams.clear();
						queryParams.put("description", "Saldo mês anterior");
						Description description = getDescriptionByParameter(queryParams);
						
						if(description == null){
							description = new Description();
							description.setDescription("Saldo mês anterior");
							queryParams.clear();
							queryParams.put("description", "Crédito");
							TypeAccount typeAccount = getTypeAccountByParameter(queryParams);
							description.setTypeAccount(typeAccount);
							description.setUser(user);
							descriptionDAO.save(description);
						}
						
						queryParams.clear();
						queryParams.put("description", "Fechamento de Contas");
						
						Description superGroup = getDescriptionByParameter(queryParams);
						
						if(superGroup == null){
							superGroup = new Description();
							superGroup.setDescription("Fechamento de Contas");
							queryParams.clear();
							queryParams.put("description", "Super Grupo");
							TypeAccount typeAccount = getTypeAccountByParameter(queryParams);
							superGroup.setTypeAccount(typeAccount);
							superGroup.setUser(user);
							descriptionDAO.save(superGroup);
						}
						credit.setDescription(description);
						credit.setSuperGroup(superGroup);
					} else {
						queryParams.clear();
						queryParams.put("description", "Previous month balance");
						Description description = getDescriptionByParameter(queryParams);
						
						if(description == null){
							description = new Description();
							description.setDescription("Previous month balance");
							queryParams.clear();
							queryParams.put("description", "Credit");
							TypeAccount typeAccount = getTypeAccountByParameter(queryParams);
							description.setTypeAccount(typeAccount);
							description.setUser(user);
							descriptionDAO.save(description);
						}
						
						queryParams.clear();
						queryParams.put("description", "Closing of accounts");
						
						Description superGroup = getDescriptionByParameter(queryParams);
						
						if(superGroup == null){
							superGroup = new Description();
							superGroup.setDescription("Closing of accounts");
							queryParams.clear();
							queryParams.put("description", "Super Group");
							TypeAccount typeAccount = getTypeAccountByParameter(queryParams);
							superGroup.setTypeAccount(typeAccount);
							superGroup.setUser(user);
							descriptionDAO.save(superGroup);
						}
						credit.setDescription(description);
						credit.setSuperGroup(superGroup);
					}
					credit.setCurrency(closure.getUser().getConfig().getCurrency());
					credit.setUser(closure.getUser());
					credit.setAmount(Crypt.encryptValor(closure.getTotalGeneral()));
					credit.setClosed(false);
					creditDAO.save(credit);
				} else {
					Debit debit = new Debit();
					debit.setDate(calendar.getTime());
					if (closure.getUser().getLanguage().equals("pt_BR")) {
						queryParams.clear();
						queryParams.put("description", "Saldo mês anterior");
						Description description = getDescriptionByParameter(queryParams);
						
						if(description == null){
							description = new Description();
							description.setDescription("Saldo mês anterior");
							queryParams.clear();
							queryParams.put("description", "Crédito");
							TypeAccount typeAccount = getTypeAccountByParameter(queryParams);
							description.setTypeAccount(typeAccount);
							description.setUser(user);
							descriptionDAO.save(description);
						}
						
						queryParams.clear();
						queryParams.put("description", "Fechamento de Contas");
						
						Description group = getDescriptionByParameter(queryParams);
						
						if(group == null){
							group = new Description();
							group.setDescription("Fechamento de Contas");
							queryParams.clear();
							queryParams.put("description", "Grupo");
							TypeAccount typeAccount = getTypeAccountByParameter(queryParams);
							group.setTypeAccount(typeAccount);
							group.setUser(user);
							descriptionDAO.save(group);
						}
						
						queryParams.clear();
						queryParams.put("description", "Fechamento de Contas");
						
						Description superGroup = getDescriptionByParameter(queryParams);
						
						if(superGroup == null){
							superGroup = new Description();
							superGroup.setDescription("Fechamento de Contas");
							queryParams.clear();
							queryParams.put("description", "Super Grupo");
							TypeAccount typeAccount = getTypeAccountByParameter(queryParams);
							superGroup.setTypeAccount(typeAccount);
							superGroup.setUser(user);
							descriptionDAO.save(superGroup);
						}
						debit.setDescription(description);
						debit.setGroup(group);
						debit.setSuperGroup(superGroup);
					} else {
						queryParams.clear();
						queryParams.put("description", "Previous month balance");
						Description description = getDescriptionByParameter(queryParams);
						
						if(description == null){
							description = new Description();
							description.setDescription("Previous month balance");
							queryParams.clear();
							queryParams.put("description", "Credit");
							TypeAccount typeAccount = getTypeAccountByParameter(queryParams);
							description.setTypeAccount(typeAccount);
							description.setUser(user);
							descriptionDAO.save(description);
						}
						
						queryParams.clear();
						queryParams.put("description", "Closing of accounts");
						
						Description group = getDescriptionByParameter(queryParams);
						
						if(group == null){
							group = new Description();
							group.setDescription("Closing of accounts");
							queryParams.clear();
							queryParams.put("description", "Group");
							TypeAccount typeAccount = getTypeAccountByParameter(queryParams);
							group.setTypeAccount(typeAccount);
							group.setUser(user);
							descriptionDAO.save(group);
						}
						
						queryParams.clear();
						queryParams.put("description", "Closing of accounts");
						
						Description superGroup = getDescriptionByParameter(queryParams);
						
						if(superGroup == null){
							superGroup = new Description();
							superGroup.setDescription("Closing of accounts");
							queryParams.clear();
							queryParams.put("description", "Super Group");
							TypeAccount typeAccount = getTypeAccountByParameter(queryParams);
							superGroup.setTypeAccount(typeAccount);
							superGroup.setUser(user);
							descriptionDAO.save(superGroup);
						}
						debit.setDescription(description);
						debit.setGroup(group);
						debit.setSuperGroup(superGroup);
					}
					
					debit.setCurrency(closure.getUser().getConfig().getCurrency());
					debit.setUser(closure.getUser());
					debit.setAmount(Crypt.encryptValor(closure.getTotalGeneral()));
					debit.setClosed(false);
					debitDAO.save(debit);
				}
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
	
	private void closeValues(Closure closure, String startDate, String endDate) {

		Credit creditoBean = new Credit();

		Debit debitoBean = new Debit();

//		closure.setCreditsAlreadyClosed(closureDAO.getCredits(closure.getUser(), startDate, endDate,true));
		
//		closure.setDebitsAlreadyClosed(closureDAO.getDebts(closure.getUser(), startDate, endDate,true));

		Collection<Credit> collectionCredit = closureDAO.getCredits(closure.getUser(), startDate, endDate, false);

		Collection<Debit> collectionDebit = closureDAO.getDebts(closure.getUser(), startDate, endDate, false);

		for (Credit credit : collectionCredit) {
			try {
				creditoBean = creditDAO.findById(Credit.class, credit.getId());
				creditoBean.setClosed(true);
				creditDAO.save(creditoBean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (Debit debit : collectionDebit) {
			try {
				debitoBean = debitDAO.findById(Debit.class, debit.getId());
				debitoBean.setClosed(true);
				debitDAO.save(debitoBean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

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
	public Closure getClosure(Closure closure) throws ParseException {
		Date date = closure.getDate();

		HashMap<String, String> ret = new HashMap<String, String>();

		if (Constants.MENSAL.equalsIgnoreCase(closure.getType().toLowerCase()) || Constants.MONTHLY.equalsIgnoreCase(closure.getType().toLowerCase())) {

			ret = loadDates(date, Calendar.DAY_OF_MONTH, -Utils.getLastDayOfMonth(Utils.dateToString(date)));

		} else if (Constants.QUINZENAL.equalsIgnoreCase(closure.getType().toLowerCase()) || Constants.FORTNIGHTLY.equalsIgnoreCase(closure.getType().toLowerCase())) {

			ret = loadDates(date, Calendar.DAY_OF_MONTH, -14);

		} else if (Constants.SEMANAL.equalsIgnoreCase(closure.getType().toLowerCase()) || Constants.WEEKLY.equalsIgnoreCase(closure.getType().toLowerCase())) {

			ret = loadDates(date, Calendar.DAY_OF_MONTH, -6);

		} else if (Constants.DIARIO.equalsIgnoreCase(closure.getType().toLowerCase()) || Constants.DAYLY.equalsIgnoreCase(closure.getType().toLowerCase())) {

			ret = loadDates(date, Calendar.DAY_OF_MONTH, 0);
		}
		
		closure = getClosureValues(closure, ret.get(Constants.DATA_INICIAL), ret.get(Constants.DATA_FINAL));
		
		
		//TODO setar messageReturn
		return closure;
	}
	
	private HashMap<String, String> loadDates(Date date, Integer type, Integer days) {
		HashMap<String, String> map = new HashMap<String, String>();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, days);
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		String startDate = dataFormatada.format(calendar.getTime());
		calendar.setTime(date);
		String endDate = dataFormatada.format(calendar.getTime());

		map.put(Constants.DATA_INICIAL, startDate);
		map.put(Constants.DATA_FINAL, endDate);
		return map;
	}
	
	private Closure getClosureValues(Closure closure, String startDate, String endDate) {
		Double somaCredito = 0.0;
		Double somaDebito = 0.0;

		closure.setCreditsAlreadyClosed(closureDAO.getCredits(closure.getUser(), startDate, endDate, true));

		closure.setDebitsAlreadyClosed(closureDAO.getDebts(closure.getUser(), startDate, endDate, true));

		Collection<Credit> collectionCredit = closureDAO.getCredits(closure.getUser(), startDate, endDate, false);

		Collection<Debit> collectionDebit = closureDAO.getDebts(closure.getUser(), startDate, endDate, false);

		for (Credit credit : collectionCredit) {
			somaCredito += Crypt.decryptValor(credit.getAmount());
		}

		for (Debit debit : collectionDebit) {
			somaDebito += Crypt.decryptValor(debit.getAmount());
		}

		Double totalGeral = somaCredito - somaDebito;
		closure.setTotalCredit(somaCredito);
		closure.setTotalDebit(somaDebito);
		closure.setTotalGeneral(totalGeral);
		closure.setStartDate(Utils.stringToDate(startDate, true));
		closure.setEndDate(Utils.stringToDate(endDate, true));

		return closure;
	}

}
