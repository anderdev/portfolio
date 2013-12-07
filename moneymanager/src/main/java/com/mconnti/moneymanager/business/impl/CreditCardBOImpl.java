package com.mconnti.moneymanager.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.CreditCardBO;
import com.mconnti.moneymanager.entity.CreditCard;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.UserDAO;
import com.mconnti.moneymanager.utils.MessageFactory;
import com.mconnti.moneymanager.utils.Utils;

public class CreditCardBOImpl extends GenericBOImpl<CreditCard> implements CreditCardBO {

	@Autowired
	private UserDAO userDAO;
	
	private User getUser(CreditCard creditCard) {
		try {
			return userDAO.findById(User.class, creditCard.getUser().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private User getMasterUser(CreditCard creditCard) {
		try {
			return userDAO.findById(User.class, creditCard.getMasterUser().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public MessageReturn save(CreditCard creditCard) {
		MessageReturn libReturn = new MessageReturn();
		User user = getUser(creditCard);
		User masterUser = getMasterUser(creditCard);
		if(user != null){
			try {
				creditCard.setExpireDate(Utils.lastDayMonth(creditCard.getExpire()));
				creditCard.setMasterUser(masterUser);
				creditCard.setUser(user);
				saveGeneric(creditCard);
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setCreditCard(creditCard);
				libReturn.setMessage(e.getMessage());
			}
			
			if (libReturn.getMessage() == null && creditCard.getId() == null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_creditCard_saved", user.getCity().getState().getCountry().getLocale()));
				libReturn.setCreditCard(creditCard);
			} else if (libReturn.getMessage() == null && creditCard.getId() != null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_creditCard_updated", user.getCity().getState().getCountry().getLocale()));
				libReturn.setCreditCard(creditCard);
			}
		}else{
			libReturn.setMessage(MessageFactory.getMessage("lb_user_not_found", "en"));
		}
		return libReturn;
	}

	public List<CreditCard> list() throws Exception {
		return list(CreditCard.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		CreditCard creditCard = null;
		try {
			creditCard = findById(CreditCard.class, id);
			if (creditCard == null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_creditCard_not_found", "en"));
			} else {
				String locale = creditCard.getUser().getCity().getState().getCountry().getLocale();
				remove(creditCard);
				libReturn.setMessage( MessageFactory.getMessage("lb_creditCard_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public CreditCard getById(Long id) {
		try {
			return findById(CreditCard.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
