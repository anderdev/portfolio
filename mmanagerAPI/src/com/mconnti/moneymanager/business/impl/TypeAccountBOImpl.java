package com.mconnti.moneymanager.business.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.TypeAccountBO;
import com.mconnti.moneymanager.business.UserBO;
import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.TypeAccount;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.utils.MessageFactory;
import com.mconnti.moneymanager.utils.Utils;

public class TypeAccountBOImpl extends GenericBOImpl<TypeAccount> implements TypeAccountBO {
	
	@Autowired
	private UserBO userBO;

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

	public List<TypeAccount> list(TypeAccount typeAccount) throws Exception {
		Map<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put(" where x.locale = ", "'" + typeAccount.getLocale() + "'");
		if(typeAccount.getShowType() != null){
			queryParams.put(" and x.showType = ", Utils.setBooleanValue(typeAccount.getShowType())+"");
		}
		return list(TypeAccount.class, queryParams, null);
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

	@Override
	public MessageReturn getByDescription(Description description) {
		User user = userBO.getSuperUser(description.getUser());
		String strDescription = "";
		if(!user.getId().equals(description.getUser().getId()) && !user.getLanguage().equals(description.getUser().getLanguage())){
			if(user.getLanguage().equals("pt_BR")){
				switch (description.getTypeAccount().getDescription().toLowerCase()) {
				case "credit":
					strDescription = "Credito";
					break;
				case "debit":
					strDescription = "Debito";
					break;
				case "group":
					strDescription = "Grupo";
					break;
				case "super group":
					strDescription = "Super Grupo";
					break;
				}
			} else {
				switch (description.getTypeAccount().getDescription().toLowerCase()) {
				case "credito":
					strDescription = "Credit";
					break;
				case "debito":
					strDescription = "Debit";
					break;
				case "grupo":
					strDescription = "Group";
					break;
				case "super grupo":
					strDescription = "Super Group";
					break;
				}
			}
		} else {
			strDescription = description.getTypeAccount().getDescription();
		}
		
		
		MessageReturn ret = new MessageReturn();
		try {
			Map<String, String> queryParams = new LinkedHashMap<>();
			queryParams.put(" where lower(x.description) = ", "'" + strDescription.toLowerCase() + "'");
			ret.setAccount(findByParameter(TypeAccount.class, queryParams));
		} catch (Exception e) {
			ret.setMessage(e.getMessage());
		}
		return ret;
	}

}
