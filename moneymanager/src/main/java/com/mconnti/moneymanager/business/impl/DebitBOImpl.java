package com.mconnti.moneymanager.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.DebitBO;
import com.mconnti.moneymanager.entity.Debit;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.CreditCardDAO;
import com.mconnti.moneymanager.persistence.CurrencyDAO;
import com.mconnti.moneymanager.persistence.DebitDAO;
import com.mconnti.moneymanager.persistence.DescriptionDAO;
import com.mconnti.moneymanager.persistence.ParcelDAO;
import com.mconnti.moneymanager.persistence.TypeClosureDAO;
import com.mconnti.moneymanager.persistence.UserDAO;
import com.mconnti.moneymanager.utils.MessageFactory;
import com.mconnti.moneymanager.utils.Utils;

public class DebitBOImpl extends GenericBOImpl<Debit> implements DebitBO {

	@Autowired
	private DebitDAO debitDAO;

	@Autowired
	private UserDAO userDAO;

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

	private User getUser(Debit debit) {
		try {
			return userDAO.findById(User.class, debit.getUser().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public MessageReturn save(Debit debit) {
		MessageReturn libReturn = new MessageReturn();
		User user = getUser(debit);
		if (user != null && debit.getCurrency() != null && debit.getSuperGroup() != null) {
			try {
				debit.setDate(Utils.stringToDate(debit.getStrDate(), false));
				saveGeneric(debit);
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setDebit(debit);
				libReturn.setMessage(e.getMessage());
			}
			if (libReturn.getMessage() == null && debit.getId() == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_debit_saved", user.getCity().getState().getCountry().getLocale()));
				libReturn.setDebit(debit);
			} else if (libReturn.getMessage() == null && debit.getId() != null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_debit_updated", user.getCity().getState().getCountry().getLocale()));
				libReturn.setDebit(debit);
			}
		} else {
			libReturn.setMessage(MessageFactory.getMessage("lb_debit_not_found", "en"));
		}

		return libReturn;
	}

	public List<Debit> list() throws Exception {
		return list(Debit.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Debit debit = null;
		try {
			debit = findById(Debit.class, id);
			if (debit == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_debit_not_found", "en"));
			} else {
				String locale = debit.getUser().getCity().getState().getCountry().getLocale();
				remove(debit);
				libReturn.setMessage(MessageFactory.getMessage("lb_debit_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public Debit getById(Long id) {
		try {
			return findById(Debit.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
