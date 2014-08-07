package com.mconnti.cashtrack.business.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.cashtrack.business.TypeAccountBO;
import com.mconnti.cashtrack.business.UserBO;
import com.mconnti.cashtrack.entity.Description;
import com.mconnti.cashtrack.entity.TypeAccount;
import com.mconnti.cashtrack.entity.User;
import com.mconnti.cashtrack.entity.xml.MessageReturn;
import com.mconnti.cashtrack.utils.MessageFactory;
import com.mconnti.cashtrack.utils.Utils;

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
			libReturn.setTypeAccount(typeAccount);
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null && typeAccount.getId() == null) {
			libReturn.setMessage(MessageFactory.getMessage("lb_typeaccount_saved", typeAccount.getLocale()));
			libReturn.setTypeAccount(typeAccount);
		} else if (libReturn.getMessage() == null && typeAccount.getId() != null) {
			libReturn.setMessage(MessageFactory.getMessage("lb_typeaccount_updated", typeAccount.getLocale()));
			libReturn.setTypeAccount(typeAccount);
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
		String strDescription = (String) getTypeAccountDescriptionByUserAndDescription(description.getUser(), description.getTypeAccount().getDescription(), false).get("description");
		
		MessageReturn ret = new MessageReturn();
		try {
			Map<String, String> queryParams = new LinkedHashMap<>();
			queryParams.put(" where lower(x.description) = ", "'" + strDescription.toLowerCase() + "'");
			ret.setTypeAccount(findByParameter(TypeAccount.class, queryParams));
		} catch (Exception e) {
			ret.setMessage(e.getMessage());
		}
		return ret;
	}
	
	private TypeAccount getByDescription(String description){
		TypeAccount typeAccount = null;
		try {
			Map<String, String> queryParams = new LinkedHashMap<>();
			queryParams.put(" where lower(x.description) = ", "'" + description.toLowerCase() + "'");
			typeAccount = findByParameter(TypeAccount.class, queryParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return typeAccount;
	}

	@Override
	public Map<String, Object> getTypeAccountDescriptionByUserAndDescription(User user, String description, Boolean withTypeAccount) {
		User tempUser = userBO.getSuperUser(user);
		Map<String, Object> map = new HashMap<String, Object>();
		if(description != null){
			String strDescription = "";
			if(!tempUser.getId().equals(user.getId()) && !tempUser.getLanguage().equals(user.getLanguage())){
				if(tempUser.getLanguage().equals("pt_BR")){
					switch (description.toLowerCase()) {
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
					default:
						strDescription = description;
						break;
					}
				} else {
					switch (description.toLowerCase()) {
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
					default:
						strDescription = description;
						break;
					}
				}
			} else {
				strDescription = description;
			}
			
			map.put("description", strDescription);
			
			if(withTypeAccount){
				map.put("typeAccount", getByDescription(strDescription));
			}
		}
		
		map.put("user", tempUser);
		
		return map;
	}

}
