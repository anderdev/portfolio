package com.mconnti.moneymanager.business.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.DescriptionBO;
import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.TypeAccount;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.DescriptionDAO;
import com.mconnti.moneymanager.persistence.TypeAccountDAO;
import com.mconnti.moneymanager.persistence.UserDAO;
import com.mconnti.moneymanager.utils.MessageFactory;

public class DescriptionBOImpl extends GenericBOImpl<Description> implements DescriptionBO {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private DescriptionDAO descriptionDAO;

	@Autowired
	private TypeAccountDAO typeAccountDAO;

	private User getUser(Description description) {
		try {
			return userDAO.findById(User.class, description.getUser().getId());
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
		if (user != null) {
			try {
				Map<String, String> queryParams = new HashMap<>();

				if (description.getUser().getSuperUser() != null) {
					queryParams.put(" where x.locale = ", "'" + description.getUser().getSuperUser().getLanguage() + "'");
					description.setUser(description.getUser().getSuperUser());
				} else {
					queryParams.put(" where x.locale = ", "'" + description.getUser().getLanguage() + "'");
				}

				List<TypeAccount> typeAccountList = typeAccountDAO.list(TypeAccount.class, queryParams, "");

				Long descriptionId = description.getId();

				for (TypeAccount typeAccount : typeAccountList) {
					if (description.getIsCredit() && typeAccount.getDescription().startsWith("C")) {
						if (description.getIsCreditOriginal() == null || !description.getIsCreditOriginal()) {
							description.setId(null);
						} else {
							description.setId(descriptionId);
						}
						description.setTypeAccount(typeAccount);
						saveGeneric(description);
					} else if (description.getIsDebit() && typeAccount.getDescription().startsWith("D")) {
						if (description.getIsDebitOriginal() == null || !description.getIsDebitOriginal()) {
							description.setId(null);
						} else {
							description.setId(descriptionId);
						}
						description.setTypeAccount(typeAccount);
						saveGeneric(description);
					} else if (description.getIsGroup() && typeAccount.getDescription().startsWith("G")) {
						if (description.getIsGroupOriginal() == null || !description.getIsGroupOriginal()) {
							description.setId(null);
						} else {
							description.setId(descriptionId);
						}
						description.setTypeAccount(typeAccount);
						saveGeneric(description);
					} else if (description.getIsSuperGroup() && typeAccount.getDescription().startsWith("S")) {
						if (description.getIsSuperGroupOriginal() == null || !description.getIsSuperGroupOriginal()) {
							description.setId(null);
						} else {
							description.setId(descriptionId);
						}
						description.setTypeAccount(typeAccount);
						saveGeneric(description);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setDescription(description);
				libReturn.setMessage(e.getMessage());
			}
			if (libReturn.getMessage() == null && description.getId() == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_description_saved", user.getCity().getState().getCountry().getLocale()));
				libReturn.setDescription(description);
			} else if (libReturn.getMessage() == null && description.getId() != null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_description_updated", user.getCity().getState().getCountry().getLocale()));
				libReturn.setDescription(description);
			}
		} else {
			libReturn.setMessage(MessageFactory.getMessage("lb_description_not_found", "en"));
		}

		return libReturn;
	}

	public List<Description> list() throws Exception {
		List<Description> descriptionList = list(Description.class, null, null);
		for (Description description : descriptionList) {
			if (description.getTypeAccount().getDescription().startsWith("C")) {
				description.setIsCredit(true);
				description.setIsCreditOriginal(true);
			} else if (description.getTypeAccount().getDescription().startsWith("D")) {
				description.setIsDebit(true);
				description.setIsDebitOriginal(true);
			} else if (description.getTypeAccount().getDescription().startsWith("G")) {
				description.setIsGroup(true);
				description.setIsGroupOriginal(true);
			} else if (description.getTypeAccount().getDescription().startsWith("S")) {
				description.setIsSuperGroup(true);
				description.setIsSuperGroupOriginal(true);
			}
		}
		return descriptionList;
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Description description = null;
		try {
			description = findById(Description.class, id);
			if (description == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_description_not_found", "en"));
			} else {
				String locale = description.getUser().getCity().getState().getCountry().getLocale();
				remove(description);
				libReturn.setMessage(MessageFactory.getMessage("lb_description_deleted", locale));
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

	@Override
	public List<Description> listByParameter(Description description) throws Exception {
		Map<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put(" where "," 1=1 ");
		queryParams.put(" and x.user.id = ", description.getUser().getId()+"");
		if( description.getTypeAccount() != null){
			queryParams.put(" and x.typeAccount.id = ", description.getTypeAccount().getId()+ "");
		}
		
		List<Description> list = list(Description.class, queryParams, "x.description");
		
		for (Description desc : list) {
			if (desc.getTypeAccount().getDescription().startsWith("C")) {
				desc.setIsCredit(true);
				desc.setIsCreditOriginal(true);
			} else if (desc.getTypeAccount().getDescription().startsWith("D")) {
				desc.setIsDebit(true);
				desc.setIsDebitOriginal(true);
			} else if (desc.getTypeAccount().getDescription().startsWith("G")) {
				desc.setIsGroup(true);
				desc.setIsGroupOriginal(true);
			} else if (desc.getTypeAccount().getDescription().startsWith("S")) {
				desc.setIsSuperGroup(true);
				desc.setIsSuperGroupOriginal(true);
			}
		}

		return list;
	}
}
