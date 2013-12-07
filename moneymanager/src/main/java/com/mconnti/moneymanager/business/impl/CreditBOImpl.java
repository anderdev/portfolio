package com.mconnti.moneymanager.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.CreditBO;
import com.mconnti.moneymanager.entity.Credit;
import com.mconnti.moneymanager.entity.Currency;
import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.CreditDAO;
import com.mconnti.moneymanager.persistence.CurrencyDAO;
import com.mconnti.moneymanager.persistence.DescriptionDAO;
import com.mconnti.moneymanager.persistence.UserDAO;
import com.mconnti.moneymanager.utils.MessageFactory;
import com.mconnti.moneymanager.utils.Utils;

public class CreditBOImpl extends GenericBOImpl<Credit> implements CreditBO {

	@Autowired
	private CreditDAO creditDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CurrencyDAO currencyDAO;

	@Autowired
	private DescriptionDAO descriptionDAO;

	private User getUser(Credit credit) {
		try {
			return userDAO.findById(User.class, credit.getUser().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Currency getCurrency(Credit credit) {
		try {
			return currencyDAO.findById(Currency.class, credit.getCurrency().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Description getSuperGroup(Credit credit) {
		try {
			return descriptionDAO.findById(Description.class, credit.getSuperGroup().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public MessageReturn save(Credit credit) {
		MessageReturn libReturn = new MessageReturn();
		User user = getUser(credit);
		Currency currency = getCurrency(credit);
		Description superGroup = getSuperGroup(credit);
		if (user != null && currency != null && superGroup != null) {
			try {
				credit.setDate(Utils.stringToDate(credit.getStrDate(), false));
				credit.setCurrency(currency);
				credit.setSuperGroup(superGroup);
				credit.setUser(user);
				saveGeneric(credit);
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setCredit(credit);
				libReturn.setMessage(e.getMessage());
			}
			if (libReturn.getMessage() == null && credit.getId() == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_credit_saved", user.getCity().getState().getCountry().getLocale()));
				libReturn.setCredit(credit);
			} else if (libReturn.getMessage() == null && credit.getId() != null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_credit_updated", user.getCity().getState().getCountry().getLocale()));
				libReturn.setCredit(credit);
			}
		} else {
			libReturn.setMessage(MessageFactory.getMessage("lb_credit_not_found", "en"));
		}

		return libReturn;
	}

	public List<Credit> list() throws Exception {
		return list(Credit.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Credit credit = null;
		try {
			credit = findById(Credit.class, id);
			if (credit == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_credit_not_found", "en"));
			} else {
				String locale = credit.getUser().getCity().getState().getCountry().getLocale();
				remove(credit);
				libReturn.setMessage(MessageFactory.getMessage("lb_credit_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public Credit getById(Long id) {
		try {
			return findById(Credit.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
