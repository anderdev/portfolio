package com.mconnti.moneymanager.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.DescriptionBO;
import com.mconnti.moneymanager.entity.Account;
import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.AccountDAO;
import com.mconnti.moneymanager.persistence.DescriptionDAO;
import com.mconnti.moneymanager.persistence.UserDAO;
import com.mconnti.moneymanager.utils.MessageFactory;

public class DescriptionBOImpl extends GenericBOImpl<Description> implements DescriptionBO {

	@Autowired
	private DescriptionDAO descriptionDAO;

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private AccountDAO accountDAO;
	
	private User getUser(Description description) {
		try {
			return userDAO.findById(User.class, description.getUser().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Account getAccount(Description description) {
		try {
			return accountDAO.findById(Account.class, description.getAccount().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	@Transactional
	public MessageReturn save(Description description) {
		MessageReturn libReturn = new MessageReturn();
		User user = getUser(description);
		if (user != null){
			Account account = getAccount(description);
			if(account != null){
				Description c = null;
				try {
					c = new Description();
					c.setId(description.getId());
					c.setName(description.getName());
					c.setAccount(account);
					c.setUser(user);
					saveGeneric(c);
				} catch (Exception e) {
					e.printStackTrace();
					libReturn.setDescription(c);
					libReturn.setMessage(e.getMessage());
				}
				if (libReturn.getMessage() == null && description.getId() == null) {
					libReturn.setMessage( MessageFactory.getMessage("lb_description_saved", user.getCity().getState().getCountry().getLocale()));
					libReturn.setDescription(c);
				} else if (libReturn.getMessage() == null && description.getId() != null) {
					libReturn.setMessage( MessageFactory.getMessage("lb_description_updated", user.getCity().getState().getCountry().getLocale()));
					libReturn.setDescription(c);
				}
			}else{
				libReturn.setMessage(MessageFactory.getMessage("lb_account_not_found", "en"));
			}
		}else{
			libReturn.setMessage(MessageFactory.getMessage("lb_description_not_found", "en"));
		}
		
		return libReturn;
	}

	public List<Description> list() throws Exception {
		return list(Description.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Description description = null;
		try {
			description = findById(Description.class, id);
			if (description == null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_description_not_found", "en"));
			} else {
				String locale = description.getUser().getCity().getState().getCountry().getLocale();
				remove(description);
				libReturn.setMessage( MessageFactory.getMessage("lb_description_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public Description getById(Long id) {
		try {
			return findById(Description.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
