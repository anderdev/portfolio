package com.mconnti.moneymanager.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.AccountBO;
import com.mconnti.moneymanager.entity.Account;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.AccountDAO;
import com.mconnti.moneymanager.utils.MessageFactory;

public class AccountBOImpl extends GenericBOImpl<Account> implements AccountBO {

	@Autowired
	private AccountDAO accountDAO;

	@Override
	@Transactional
	public MessageReturn save(Account account) {
		MessageReturn libReturn = new MessageReturn();
		Account c = null;
		try {
			c = new Account();
			c.setId(account.getId());
			c.setDescription(account.getDescription());
			c.setLocale(account.getLocale());
			saveGeneric(c);
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setAccount(c);
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null && account.getId() == null) {
			libReturn.setMessage( MessageFactory.getMessage("lb_account_saved", account.getLocale()));
			libReturn.setAccount(c);
		} else if (libReturn.getMessage() == null && account.getId() != null) {
			libReturn.setMessage( MessageFactory.getMessage("lb_account_updated", account.getLocale()));
			libReturn.setAccount(c);
		}
		return libReturn;
	}

	public List<Account> list() throws Exception {
		return list(Account.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Account account = null;
		try {
			account = findById(Account.class, id);
			if (account == null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_account_not_found", "en"));
			} else {
				String locale = account.getLocale();
				remove(account);
				libReturn.setMessage( MessageFactory.getMessage("lb_account_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public Account getById(Long id) {
		try {
			return findById(Account.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
