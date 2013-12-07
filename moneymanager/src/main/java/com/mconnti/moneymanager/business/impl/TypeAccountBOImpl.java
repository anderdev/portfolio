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
	private TypeAccountDAO typeAccountDAO;

	@Override
	@Transactional
	public MessageReturn save(TypeAccount typeAccount) {
		MessageReturn libReturn = new MessageReturn();
		TypeAccount c = null;
		try {
			String[] nameSplit = typeAccount.getDescription().split(";");
			if (nameSplit.length > 1) {
				for (int x = 0; x < nameSplit.length; x++) {
					c = new TypeAccount();
					c.setId(typeAccount.getId());
					c.setDescription(nameSplit[x]);
					c.setLocale(typeAccount.getLocale());
					saveGeneric(c);
				}
			} else {
				saveGeneric(typeAccount);
			}

		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setAccount(typeAccount);
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null && typeAccount.getId() == null) {
			libReturn.setMessage(MessageFactory.getMessage("lb_typeaccount_saved", typeAccount.getLocale()));
			libReturn.setAccount(typeAccount);
		} else if (libReturn.getMessage() == null && typeAccount.getId() != null) {
			libReturn.setMessage(MessageFactory.getMessage("lb_typeaccount_updated", typeAccount.getLocale()));
			libReturn.setAccount(typeAccount);
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
		TypeAccount typeAccount = null;
		try {
			typeAccount = findById(TypeAccount.class, id);
			if (typeAccount == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_typeaccount_not_found", "en"));
			} else {
				String locale = typeAccount.getLocale();
				remove(typeAccount);
				libReturn.setMessage(MessageFactory.getMessage("lb_typeaccount_deleted", locale));
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
