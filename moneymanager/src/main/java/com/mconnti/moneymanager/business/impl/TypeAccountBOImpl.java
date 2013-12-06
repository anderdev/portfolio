package com.mconnti.moneymanager.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.TypeAccountBO;
import com.mconnti.moneymanager.entity.TypeAccount;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.TypeAccountDAO;
import com.mconnti.moneymanager.utils.MessageFactory;

public class TypeAccountBOImpl extends GenericBOImpl<TypeAccount> implements TypeAccountBO {

	@Autowired
	private TypeAccountDAO accountDAO;

	@Override
	@Transactional
	public MessageReturn save(TypeAccount account) {
		MessageReturn libReturn = new MessageReturn();
		TypeAccount c = null;
		try {
			c = new TypeAccount();
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
			libReturn.setMessage( MessageFactory.getMessage("lb_typeaccount_saved", account.getLocale()));
			libReturn.setAccount(c);
		} else if (libReturn.getMessage() == null && account.getId() != null) {
			libReturn.setMessage( MessageFactory.getMessage("lb_typeaccount_updated", account.getLocale()));
			libReturn.setAccount(c);
		}
		return libReturn;
	}

	public List<TypeAccount> list() throws Exception {
		return list(TypeAccount.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		TypeAccount account = null;
		try {
			account = findById(TypeAccount.class, id);
			if (account == null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_typeaccount_not_found", "en"));
			} else {
				String locale = account.getLocale();
				remove(account);
				libReturn.setMessage( MessageFactory.getMessage("lb_typeaccount_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public TypeAccount getById(Long id) {
		try {
			return findById(TypeAccount.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
