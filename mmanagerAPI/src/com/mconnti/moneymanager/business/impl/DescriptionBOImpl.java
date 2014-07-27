package com.mconnti.moneymanager.business.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.DescriptionBO;
import com.mconnti.moneymanager.business.TypeAccountBO;
import com.mconnti.moneymanager.business.UserBO;
import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.TypeAccount;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.DescriptionDAO;
import com.mconnti.moneymanager.utils.Crypt;
import com.mconnti.moneymanager.utils.MessageFactory;

public class DescriptionBOImpl extends GenericBOImpl<Description> implements DescriptionBO {

	@Autowired
	private UserBO userBO;

	@Autowired
	private DescriptionDAO descriptionDAO;

	@Autowired
	private TypeAccountBO typeAccountBO;

	private User getUser(Description description) {
		return userBO.getSuperUser(description.getUser());
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

				List<TypeAccount> typeAccountList = typeAccountBO.list(TypeAccount.class, queryParams, "");

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
				libReturn.setMessage(MessageFactory.getMessage("lb_description_saved", user.getLanguage()));
				libReturn.setDescription(description);
			} else if (libReturn.getMessage() == null && description.getId() != null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_description_updated", user.getLanguage()));
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
				String locale = description.getUser().getLanguage();
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

	private List<TypeAccount> getTypeAccountByDescription(Description description) throws Exception {
		Map<String, String> queryParamsTypeAccoount = new LinkedHashMap<>();

		if (description.getTypeAccount().getDescription().toLowerCase().equals("credit_debit")) {
			if (description.getUser().getLanguage().equals("pt_BR")) {
				queryParamsTypeAccoount.put(" where lower(x.description) in ", "('credito','debito')");
			} else {
				queryParamsTypeAccoount.put(" where lower(x.description) in ", "('credit','debit')");
			}
		} else {
			queryParamsTypeAccoount.put(" where lower(x.description) = ", "'" + description.getTypeAccount().getDescription().toLowerCase() + "'");
		}

		List<TypeAccount> typeAccountList = typeAccountBO.list(TypeAccount.class, queryParamsTypeAccoount, "x.description");
		return typeAccountList;
	}

	@Override
	public List<Description> listByParameter(Description description) throws Exception {
		Map<String, Object> map = typeAccountBO.getTypeAccountDescriptionByUserAndDescription(description.getUser(), description.getTypeAccount().getDescription(), false);

		description.setUser((User) map.get("user"));
		description.getTypeAccount().setDescription((String) map.get("description"));

		Map<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put(" where ", " 1=1 ");
		queryParams.put(" and x.user.id = ", description.getUser().getId() + "");

		if (description.getTypeAccount() != null && description.getTypeAccount().getDescription() != null) {
			List<TypeAccount> typeAccountList = getTypeAccountByDescription(description);

			if (typeAccountList.size() > 1) {
				queryParams.put(" and x.typeAccount.id in ", "(" + typeAccountList.get(0).getId() + "," + typeAccountList.get(1).getId() + ")");
			} else {
				queryParams.put(" and x.typeAccount.id = ", typeAccountList.get(0).getId() + "");
			}
		} else if (description.getTypeAccount() != null && description.getTypeAccount().getId() != null) {
			queryParams.put(" and x.typeAccount.id = ", description.getTypeAccount().getId() + "");
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Description getByDescription(Map<String, String> request) throws Exception {
		String userId = null;
		String typeAccountId = null;
		String description = null;
		Iterator it = request.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pairs = (Entry<String, String>) it.next();

			switch (pairs.getKey()) {
			case "user":
				userId = (String) pairs.getValue();
				break;
			case "typeAccount":
				typeAccountId = (String) pairs.getValue();
				break;
			case "description":
				description = (String) pairs.getValue();
				break;
			}

			it.remove(); // avoids a ConcurrentModificationException
		}
		User user = new User();
		user.setId(Long.decode(userId));
		TypeAccount typeAccount = typeAccountBO.getById(Long.decode(typeAccountId));
		Description descriptionTemp = new Description();
		descriptionTemp.setTypeAccount(typeAccount);
		descriptionTemp.setUser(user);

		Map<String, Object> map = typeAccountBO.getTypeAccountDescriptionByUserAndDescription(user, typeAccount.getDescription(), true);
		user = (User) map.get("user");
		typeAccount = (TypeAccount) map.get("typeAccount");

		Map<String, String> queryParams = new LinkedHashMap<>();

		queryParams.put(" where x.user = ", user.getId() + "");
		queryParams.put(" and x.typeAccount = ", typeAccount.getId() + "");
		queryParams.put(" and lower(x.description) = '", Crypt.encrypt(description) + "'");

		Description desc = findByParameter(Description.class, queryParams);
		return desc;
	}
}
