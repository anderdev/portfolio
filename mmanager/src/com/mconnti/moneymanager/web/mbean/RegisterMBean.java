package com.mconnti.moneymanager.web.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;

import com.mconnti.moneymanager.entity.CreditCard;
import com.mconnti.moneymanager.entity.Currency;
import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.Register;
import com.mconnti.moneymanager.entity.TypeAccount;
import com.mconnti.moneymanager.entity.TypeClosure;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.util.FacesUtil;
import com.mconnti.moneymanager.util.MessageFactory;

@SessionScoped
@ManagedBean(name = "registerMBean")
public class RegisterMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{userMBean}")
	private UserMBean userMBean;

	private List<Register> registerList;
	
	private List<Register> registerSearchList;

	private Register register;

	private Register[] selectedRegister;

	private String host = null;

	private List<Description> descriptionList;
	
	private List<Description> descriptionFullList;

	private List<Description> creditList;

	private List<Description> debitList;

	private List<Description> groupList;

	private List<Description> superGroupList;

	private List<CreditCard> creditCardList;

	private List<Currency> currencyList;

	private List<TypeClosure> typeClosureList;
	
	private SelectItem[] creditsDebits;
	
	private SelectItem[] groups;
	
	private SelectItem[] superGroups;

	private SelectItem[] creditCards;

	private SelectItem[] currencies;

	private SelectItem[] typeClosures;

	private Description description;

	private Description group;

	private Description superGroup;

	private CreditCard creditCard;

	private Currency currency;

	private TypeClosure typeClosure;

	private Boolean loadDebits = false;

	private Boolean loadCredits = false;
	
	private BigDecimal debitTotal;
	
	private BigDecimal creditTotal;
	
	private BigDecimal searchTotal;
	
	private Boolean loadSearchFooter = false;

	public RegisterMBean() {
		this.register = new Register();
		this.description = new Description();
		this.group = new Description();
		this.superGroup = new Description();
		this.currency = new Currency();
		this.creditCard = new CreditCard();
		this.typeClosure = new TypeClosure();
		creditTotal = new BigDecimal(0.0);
		debitTotal = new BigDecimal(0.0);
		searchTotal = new BigDecimal(0.0);

		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			String[] str = ((HttpServletRequest) request).getRequestURL().toString().split("mmanager");
			host = str[0];
		}
	}

	private void loadAutocompleteLists(Boolean search) {
		if(search){
			this.creditsDebits = loadCreditDebits();
			this.groups = loadGroups();
			this.superGroups = loadSuperGroups();
		} else {
			this.creditList = loadDescriptionList(MessageFactory.getMessage("lb_credit_", superUser().getLanguage()));
			this.debitList = loadDescriptionList(MessageFactory.getMessage("lb_debit_", superUser().getLanguage()));
			this.groupList = loadDescriptionList(MessageFactory.getMessage("lb_group_", superUser().getLanguage()));
			this.superGroupList = loadDescriptionList(MessageFactory.getMessage("lb_super_group_", superUser().getLanguage()));
		}
	}

	private void loadDefaultCombos(Boolean search) {
		if(search){
			loadAutocompleteLists(true);
		}else{
			loadAutocompleteLists(false);
		}
		this.creditCards = loadCreditCards();
		this.typeClosures = loadTypeClosures();
		this.currencies = loadCurrencies();
	}

	private void newDescription(String description, String typeAccount) {
		Description desc = new Description();
		desc.setUser(superUser());
		desc.setDescription(description);
		desc.setTypeAccount(getTypeAccount(typeAccount));
		switch (typeAccount.toLowerCase()) {
		case "credit":
			desc.setIsCredit(true);
			register.setDescription(desc);
			break;
		case "debit":
			desc.setIsDebit(true);
			register.setDescription(desc);
			break;
		case "group":
			desc.setIsGroup(true);
			register.setGroup(desc);
			break;
		case "super group":
			desc.setIsSuperGroup(true);
			register.setSuperGroup(desc);
			break;
		}
	}

	public void checkCreditDescription() {
		boolean contain = false;
		if (register.getDescription().getDescription() != null && !"".equals(register.getDescription().getDescription())) {
			if (creditList.size() > 0) {
				ArrayList<String> descLista = new ArrayList<String>();
				for (Description desc : creditList) {
					if (desc.getDescription().equalsIgnoreCase(register.getDescription().getDescription())) {
						register.setDescription(desc);
						contain = true;
						break;
					}
					descLista.add(desc.getDescription());
				}

				if (!contain && !descLista.contains(register.getDescription().getDescription())) {
					newDescription(description.getDescription(), MessageFactory.getMessage("lb_credit_", superUser().getLanguage()));
				}
			} else {
				newDescription(register.getDescription().getDescription(), MessageFactory.getMessage("lb_credit_", superUser().getLanguage()));
			}
		}

	}

	public void checkDebitDescription() {
		boolean contain = false;
		if (register.getDescription().getDescription() != null && !"".equals(register.getDescription().getDescription())) {
			if (debitList.size() > 0) {
				ArrayList<String> descLista = new ArrayList<String>();
				for (Description desc : debitList) {
					if (desc.getDescription().equalsIgnoreCase(register.getDescription().getDescription())) {
						register.setDescription(desc);
						contain = true;
						break;
					}
					descLista.add(desc.getDescription());
				}

				if (!contain && !descLista.contains(register.getDescription().getDescription())) {
					newDescription(description.getDescription(), MessageFactory.getMessage("lb_debit_", superUser().getLanguage()));
				}
			} else {
				newDescription(register.getDescription().getDescription(), MessageFactory.getMessage("lb_debit_", superUser().getLanguage()));
			}
		}
	}

	private User superUser() {
		return userMBean.getLoggedUser().getSuperUser() == null ? userMBean.getLoggedUser() : userMBean.getLoggedUser().getSuperUser();
	}

	public void checkGroupDescription() {
		boolean contain = false;
		if (register.getGroup().getDescription() != null && !"".equals(register.getGroup().getDescription())) {
			if (groupList.size() > 0) {
				ArrayList<String> descLista = new ArrayList<String>();
				for (Description desc : groupList) {
					if (desc.getDescription().equalsIgnoreCase(register.getGroup().getDescription())) {
						register.setGroup(desc);
						contain = true;
						break;
					}
					descLista.add(desc.getDescription());
				}
				if (!contain && !descLista.contains(register.getGroup().getDescription())) {
					newDescription(register.getGroup().getDescription(), MessageFactory.getMessage("lb_group_", superUser().getLanguage()));
				}
			} else {
				newDescription(register.getGroup().getDescription(), MessageFactory.getMessage("lb_group_", superUser().getLanguage()));
			}
		}
	}

	public void checkSuperGroupDescription() {
		boolean contain = false;
		if (register.getSuperGroup().getDescription() != null && !"".equals(register.getSuperGroup().getDescription())) {
			if (superGroupList.size() > 0) {
				ArrayList<String> descLista = new ArrayList<String>();
				for (Description desc : superGroupList) {
					if (desc.getDescription().equalsIgnoreCase(register.getSuperGroup().getDescription())) {
						register.setSuperGroup(desc);
						contain = true;
						break;
					}
					descLista.add(desc.getDescription());
				}
				if (!contain && !descLista.contains(register.getSuperGroup().getDescription())) {
					newDescription(register.getSuperGroup().getDescription(), MessageFactory.getMessage("lb_super_group_", superUser().getLanguage()));
				}
			} else {
				newDescription(register.getSuperGroup().getDescription(), MessageFactory.getMessage("lb_super_group_", superUser().getLanguage()));
			}
		}
	}

	private TypeAccount getTypeAccount(String description) {
		MessageReturn ret = new MessageReturn();
		try {
			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/typeaccount/getbydescription");

			request.body(MediaType.APPLICATION_JSON, description);
			ClientResponse<String> response = request.put(String.class);
			
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			
			ret = response.getEntity(MessageReturn.class);
			
			if (ret.getAccount() == null) {
				throw new Exception(ret.getMessage());
			} else {
				return ret.getAccount();
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<Description> loadDescriptionList(String typeAccountDescription) {
		try {
			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/description");
			TypeAccount typeAccount = new TypeAccount();
			typeAccount.setDescription(typeAccountDescription);
			
			description.setUser(superUser());
			description.setTypeAccount(typeAccount);

			request.body(MediaType.APPLICATION_JSON, description);
			ClientResponse<Description> response = request.put(Description.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			descriptionList = response.getEntity(new GenericType<List<Description>>() {
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return descriptionList;
	}

	public SelectItem[] loadCurrencies() {
		currencyList = loadCurrencyList();

		List<SelectItem> itens = new ArrayList<SelectItem>(currencyList.size());

		this.currencies = new SelectItem[itens.size()];

		for (Currency c : currencyList) {
			itens.add(new SelectItem(c.getId(), c.getAcronym()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	private List<Currency> loadCurrencyList() {
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/currency");
			currency.setLocale(userMBean.getLoggedUser().getLanguage());
			request.body(MediaType.APPLICATION_JSON, currency);
			ClientResponse<Currency> response = request.put(Currency.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			currencyList = response.getEntity(new GenericType<List<Currency>>() {
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currencyList;
	}
	
	public SelectItem[] loadCreditDebits() {
		descriptionFullList = loadDescriptionList("credit_debit");

		List<SelectItem> itens = new ArrayList<SelectItem>(descriptionFullList.size());

		this.creditsDebits = new SelectItem[itens.size()];

		for (Description d : descriptionFullList) {
			itens.add(new SelectItem(d.getId(), d.getDescription()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}
	
	public SelectItem[] loadGroups() {
		groupList = loadDescriptionList(MessageFactory.getMessage("lb_group_", superUser().getLanguage()));
		
		List<SelectItem> itens = new ArrayList<SelectItem>(groupList.size());

		this.groups = new SelectItem[itens.size()];

		for (Description d : groupList) {
			itens.add(new SelectItem(d.getId(), d.getDescription()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}
	
	public SelectItem[] loadSuperGroups() {
		
		superGroupList = loadDescriptionList(MessageFactory.getMessage("lb_super_group_", superUser().getLanguage()));

		List<SelectItem> itens = new ArrayList<SelectItem>(superGroupList.size());

		this.superGroups = new SelectItem[itens.size()];

		for (Description d : superGroupList) {
			itens.add(new SelectItem(d.getId(), d.getDescription()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	public SelectItem[] loadCreditCards() {
		creditCardList = loadCreditCardList();

		List<SelectItem> itens = new ArrayList<SelectItem>(creditCardList.size());

		this.creditCards = new SelectItem[itens.size()];

		for (CreditCard c : creditCardList) {
			itens.add(new SelectItem(c.getId(), c.getName()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	private List<CreditCard> loadCreditCardList() {
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/creditcard");
			creditCard.setUser(superUser());

			request.body(MediaType.APPLICATION_JSON, creditCard);
			ClientResponse<CreditCard> response = request.put(CreditCard.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			creditCardList = response.getEntity(new GenericType<List<CreditCard>>() {
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return creditCardList;
	}

	public SelectItem[] loadTypeClosures() {
		typeClosureList = loadTypeClosureList();

		List<SelectItem> itens = new ArrayList<SelectItem>(typeClosureList.size());

		this.typeClosures = new SelectItem[itens.size()];

		for (TypeClosure c : typeClosureList) {
			itens.add(new SelectItem(c.getId(), c.getType()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	private List<TypeClosure> loadTypeClosureList() {
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/typeclosure");
			typeClosure.setLocale(userMBean.getLoggedUser().getLanguage());
			request.body(MediaType.APPLICATION_JSON, typeClosure);
			ClientResponse<TypeClosure> response = request.put(TypeClosure.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			typeClosureList = response.getEntity(new GenericType<List<TypeClosure>>() {
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return typeClosureList;
	}

	private void loadList(TypeAccount typeAccount) {
		try {
			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/register");
			if(typeAccount != null){
				register.setTypeAccount(typeAccount);
			}
			register.setUser(superUser());
			request.body(MediaType.APPLICATION_JSON, register);
			ClientResponse<Register> response = request.put(Register.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			
			if(typeAccount != null){
				registerList = (List<Register>) response.getEntity(new GenericType<List<Register>>() {});
			} else {
				registerSearchList = (List<Register>) response.getEntity(new GenericType<List<Register>>() {});
				for (Register reg : registerSearchList) {
					if(reg.getTypeAccount().getDescription().equals(MessageFactory.getMessage("lb_credit_", superUser().getLanguage()))){
						creditTotal = creditTotal.add(reg.getAmount());
					} else if (reg.getTypeAccount().getDescription().equals(MessageFactory.getMessage("lb_debit_", superUser().getLanguage()))){
						debitTotal = debitTotal.add(reg.getAmount());
					}
				}
				searchTotal = creditTotal.subtract(debitTotal);
				loadSearchFooter = true;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String list() {
		loadSearchFooter = false;
		searchTotal = new BigDecimal(0.0);
		debitTotal = new BigDecimal(0.0);
		creditTotal = new BigDecimal(0.0);
		loadDefaultCombos(true);
		createRegister(true);
		this.registerSearchList = new ArrayList<>();
		return "/common/listRegister.xhtml?faces-redirect=true";
	}
	
	public void search(){
		loadSearchFooter = false;
		searchTotal = new BigDecimal(0.0);
		debitTotal = new BigDecimal(0.0);
		creditTotal = new BigDecimal(0.0);
		register.setSearch(true);
		loadList(null);
		register.setSearch(false);
	}

	private void createRegister(Boolean search) {
		this.register = new Register();
		register.setDescription(new Description());
		register.setGroup(new Description());
		register.setSuperGroup(new Description());
		register.setCurrency(new Currency());
		register.setTypeClosure(new TypeClosure());
		register.setCreditCard(new CreditCard());
		register.setSearch(false);
		if(!search){
			setDefaultValues();
		}
	}

	private void setDefaultValues() {
		if(userMBean.getConfigLoggedUser() != null){
			register.setTypeClosure(userMBean.getConfigLoggedUser().getTypeClosure());
			register.setCurrency(userMBean.getConfigLoggedUser().getCurrency());
		}
		Calendar calendar = Calendar.getInstance();
		register.setDate(calendar.getTime());
		register.setNumberParcel(1);
	}

	public String newCredit() {
		createRegister(false);
		loadDebits = false;
		loadCredits = true;
		loadDefaultCombos(false);
		createTypeAccount(MessageFactory.getMessage("lb_credit_", superUser().getLanguage()));
		loadList(register.getTypeAccount());
		return "/common/formRegister.xhtml?faces-redirect=true";
	}

	private void createTypeAccount(final String description) {
		TypeAccount typeAccount = getTypeAccount(description);
		register.setTypeAccount(typeAccount);
	}

	public String newDebit() {
		createRegister(false);
		loadDebits = true;
		loadCredits = false;
		loadDefaultCombos(false);
		createTypeAccount(MessageFactory.getMessage("lb_debit_", superUser().getLanguage()));
		loadList(register.getTypeAccount());
		return "/common/formRegister.xhtml?faces-redirect=true";
	}

	public String cancel() {
		loadDebits = false;
		loadCredits = false;
		this.register = new Register();
		return "/common/index.xhtml/faces-redirect=true";
	}

	public void edit() {
		if(register.getCreditCard() == null){
			register.setCreditCard(new CreditCard());
		}
	}

	private List<String> returnResults(List<Description> list, String query) {
		List<String> results = new ArrayList<>();
		for (Description description : list) {
			if (description.getDescription().toLowerCase().startsWith(query.toLowerCase())) {
				results.add(description.getDescription());
			}
		}
		return results;
	}

	public List<String> creditDebitComplete(String query) {
		List<String> results = returnResults(descriptionFullList, query);
		return results;
	}
	
	public List<String> creditComplete(String query) {
		List<String> results = returnResults(creditList, query);
		return results;
	}

	public List<String> debitComplete(String query) {
		List<String> results = returnResults(debitList, query);
		return results;
	}

	public List<String> groupComplete(String query) {
		List<String> results = returnResults(groupList, query);
		return results;
	}

	public List<String> superGroupComplete(String query) {
		List<String> results = returnResults(superGroupList, query);
		return results;
	}

	public void save() {
		MessageReturn ret = new MessageReturn();

		try {
			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/register");

			if (loadDebits) {
				createTypeAccount(MessageFactory.getMessage("lb_debit_", superUser().getLanguage()));// debit
				if (register.getCreditCard().getId() == null || register.getCreditCard().getId() == 0) {
					register.setCreditCard(null);
				}
			} else {
				register.setGroup(null);
				register.setCreditCard(null);
				createTypeAccount(MessageFactory.getMessage("lb_credit_", superUser().getLanguage()));// credit
			}

			register.setUser(superUser());
			request.body(MediaType.APPLICATION_JSON, register);

			ClientResponse<Register> response = request.post(Register.class);

			ret = response.getEntity(MessageReturn.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			if (ret.getRegister() == null) {
				throw new Exception(ret.getMessage());
			} else {
				FacesUtil.showSuccessMessage(ret.getMessage());
			}
			loadList(ret.getRegister().getTypeAccount());
			createRegister(false);
			loadAutocompleteLists(false);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(e.getMessage());
		}
	}

	public void delete() {
		MessageReturn ret = new MessageReturn();
		try {
			for (Register register : selectedRegister) {
				ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/register");
				request.body(MediaType.APPLICATION_JSON, register);

				ClientResponse<Register> response = request.delete(Register.class);

				ret = response.getEntity(MessageReturn.class);
			}

			if (selectedRegister.length > 1) {
				// FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_debit_deleted_successfully_mult", userMBean.getLoggedUser().getLanguage()));
			} else {
				// FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_deleted_successfully", userMBean.getLoggedUser().getLanguage()));
			}
			// loadList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(ret.getMessage());
		}

	}

	public UserMBean getUserMBean() {
		return userMBean;
	}

	public void setUserMBean(UserMBean userMBean) {
		this.userMBean = userMBean;
	}

	public List<Register> getRegisterList() {
		return registerList;
	}

	public void setRegisterList(List<Register> registerList) {
		this.registerList = registerList;
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public Register[] getSelectedRegister() {
		return selectedRegister;
	}

	public void setSelectedRegister(Register[] selectedRegister) {
		this.selectedRegister = selectedRegister;
	}

	public SelectItem[] getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(SelectItem[] creditCards) {
		this.creditCards = creditCards;
	}

	public SelectItem[] getCurrencies() {
		return currencies;
	}

	public void setCurrencies(SelectItem[] currencies) {
		this.currencies = currencies;
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
		this.description = description;
	}

	public Description getGroup() {
		return group;
	}

	public void setGroup(Description group) {
		this.group = group;
	}

	public Description getSuperGroup() {
		return superGroup;
	}

	public void setSuperGroup(Description superGroup) {
		this.superGroup = superGroup;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public List<Description> getDescriptionList() {
		return descriptionList;
	}

	public void setDescriptionList(List<Description> descriptionList) {
		this.descriptionList = descriptionList;
	}

	public List<Description> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Description> groupList) {
		this.groupList = groupList;
	}

	public List<Description> getSuperGroupList() {
		return superGroupList;
	}

	public void setSuperGroupList(List<Description> superGroupList) {
		this.superGroupList = superGroupList;
	}

	public List<CreditCard> getCreditcardList() {
		return creditCardList;
	}

	public void setCreditcardList(List<CreditCard> creditcardList) {
		this.creditCardList = creditcardList;
	}

	public List<Currency> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<Currency> currencyList) {
		this.currencyList = currencyList;
	}

	public TypeClosure getTypeClosure() {
		return typeClosure;
	}

	public void setTypeClosure(TypeClosure typeClosure) {
		this.typeClosure = typeClosure;
	}

	public List<TypeClosure> getTypeClosureList() {
		return typeClosureList;
	}

	public void setTypeClosureList(List<TypeClosure> typeClosureList) {
		this.typeClosureList = typeClosureList;
	}

	public List<CreditCard> getCreditCardList() {
		return creditCardList;
	}

	public void setCreditCardList(List<CreditCard> creditCardList) {
		this.creditCardList = creditCardList;
	}

	public Boolean getLoadDebits() {
		return loadDebits;
	}

	public void setLoadDebits(Boolean loadDebits) {
		this.loadDebits = loadDebits;
	}

	public Boolean getLoadCredits() {
		return loadCredits;
	}

	public void setLoadCredits(Boolean loadCredits) {
		this.loadCredits = loadCredits;
	}

	public SelectItem[] getTypeClosures() {
		return typeClosures;
	}

	public void setTypeClosures(SelectItem[] typeClosures) {
		this.typeClosures = typeClosures;
	}

	public List<Description> getCreditList() {
		return creditList;
	}

	public void setCreditList(List<Description> creditList) {
		this.creditList = creditList;
	}

	public List<Description> getDebitList() {
		return debitList;
	}

	public void setDebitList(List<Description> debitList) {
		this.debitList = debitList;
	}

	public List<Register> getRegisterSearchList() {
		return registerSearchList;
	}

	public void setRegisterSearchList(List<Register> registerSearchList) {
		this.registerSearchList = registerSearchList;
	}

	public List<Description> getDescriptionFullList() {
		return descriptionFullList;
	}

	public void setDescriptionFullList(List<Description> descriptionFullList) {
		this.descriptionFullList = descriptionFullList;
	}

	public SelectItem[] getCreditsDebits() {
		return creditsDebits;
	}

	public void setCreditsDebits(SelectItem[] creditsDebits) {
		this.creditsDebits = creditsDebits;
	}

	public SelectItem[] getGroups() {
		return groups;
	}

	public void setGroups(SelectItem[] groups) {
		this.groups = groups;
	}

	public SelectItem[] getSuperGroups() {
		return superGroups;
	}

	public void setSuperGroups(SelectItem[] superGroups) {
		this.superGroups = superGroups;
	}

	public BigDecimal getSearchTotal() {
		return searchTotal;
	}

	public void setSearchTotal(BigDecimal searchTotal) {
		this.searchTotal = searchTotal;
	}

	public BigDecimal getDebitTotal() {
		return debitTotal;
	}

	public void setDebitTotal(BigDecimal debitTotal) {
		this.debitTotal = debitTotal;
	}

	public BigDecimal getCreditTotal() {
		return creditTotal;
	}

	public void setCreditTotal(BigDecimal creditTotal) {
		this.creditTotal = creditTotal;
	}

	public Boolean getLoadSearchFooter() {
		return loadSearchFooter;
	}

	public void setLoadSearchFooter(Boolean loadSearchFooter) {
		this.loadSearchFooter = loadSearchFooter;
	}
}