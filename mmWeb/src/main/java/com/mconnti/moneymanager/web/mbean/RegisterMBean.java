package com.mconnti.moneymanager.web.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.util.FacesUtil;

@SessionScoped
@ManagedBean(name = "registerMBean")
public class RegisterMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{userMBean}")
	private UserMBean userMBean;

	private List<Register> registerList;

	private Register register;

	private Register[] selectedRegister;

	private String host = null;

	private List<Description> descriptionList;

	private List<Description> groupList;

	private List<Description> superGroupList;

	private List<CreditCard> creditCardList;

	private List<Currency> currencyList;

	private List<TypeClosure> typeClosureList;

	private SelectItem[] descriptions;

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

	public RegisterMBean() {
		this.register = new Register();
		this.description = new Description();
		this.group = new Description();
		this.superGroup = new Description();
		this.currency = new Currency();
		this.creditCard = new CreditCard();
		this.typeClosure = new TypeClosure();

		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			String[] str = ((HttpServletRequest) request).getRequestURL().toString().split("mmanager");
			host = str[0];
		}
	}

	private void loadCombos() {
		this.descriptions = loadDescriptions();
		this.groups = loadGroups();
		this.superGroups = loadSuperGroups();
		this.creditCards = loadCreditCards();
		this.typeClosures = loadTypeClosures();
		this.currencies = loadCurrencies();
	}

	public SelectItem[] loadDescriptions() {
		TypeAccount typeAccount = new TypeAccount();
		if (loadDebits) {
			typeAccount.setId(2L);
		} else {
			typeAccount.setId(1L);
		}

		descriptionList = loadDescriptionList(typeAccount);

		List<SelectItem> itens = new ArrayList<SelectItem>(descriptionList.size());

		this.descriptions = new SelectItem[itens.size()];

		for (Description desc : descriptionList) {
			itens.add(new SelectItem(desc.getId(), desc.getDescription()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	public SelectItem[] loadGroups() {
		TypeAccount typeAccount = new TypeAccount();
		typeAccount.setId(3L);

		groupList = loadDescriptionList(typeAccount);

		List<SelectItem> itens = new ArrayList<SelectItem>(groupList.size());

		this.groups = new SelectItem[itens.size()];

		for (Description desc : groupList) {
			itens.add(new SelectItem(desc.getId(), desc.getDescription()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	public SelectItem[] loadSuperGroups() {
		TypeAccount typeAccount = new TypeAccount();
		typeAccount.setId(4L);

		superGroupList = loadDescriptionList(typeAccount);

		List<SelectItem> itens = new ArrayList<SelectItem>(superGroupList.size());

		this.superGroups = new SelectItem[itens.size()];

		for (Description desc : superGroupList) {
			itens.add(new SelectItem(desc.getId(), desc.getDescription()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	private List<Description> loadDescriptionList(TypeAccount typeAccount) {
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/description");

			description.setUser(userMBean.getLoggedUser().getSuperUser() == null ? userMBean.getLoggedUser() : userMBean.getLoggedUser().getSuperUser());
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

		this.groups = new SelectItem[itens.size()];

		for (Currency c : currencyList) {
			itens.add(new SelectItem(c.getId(), c.getAcronym()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	private List<Currency> loadCurrencyList() {
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/currency");
			ClientResponse<Currency> response = request.get(Currency.class);

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
			creditCard.setUser(userMBean.getLoggedUser().getSuperUser() == null ? userMBean.getLoggedUser() : userMBean.getLoggedUser().getSuperUser());

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
			ClientResponse<TypeClosure> response = request.get(TypeClosure.class);

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

	private void loadList() {
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/register");
			ClientResponse<Register> response = request.get(Register.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			registerList = (List<Register>) response.getEntity(new GenericType<List<Register>>() {
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String list() {
		loadList();
		return "/common/listRegister.xhtml?faces-redirect=true";
	}

	private void createRegister() {
		this.register = new Register();
		register.setDescription(new Description());
		register.setGroup(new Description());
		register.setSuperGroup(new Description());
		register.setCurrency(new Currency());
		register.setTypeClosure(new TypeClosure());
	}

	public String newCredit() {
		createRegister();
		loadDebits = false;
		loadCredits = true;
		loadCombos();
		return "/common/formRegister.xhtml?faces-redirect=true";
	}
	
	private void createTypeAccount(final Long type){
		TypeAccount typeAccount = new TypeAccount();
		typeAccount.setId(type);
		register.setTypeAccount(typeAccount);
	}

	public String newDebit() {
		createRegister();
		register.setCreditCard(new CreditCard());
		loadDebits = true;
		loadCredits = false;
		loadCombos();
		return "/common/formRegister.xhtml?faces-redirect=true";
	}

	public String cancel() {
		loadDebits = false;
		loadCredits = false;
		this.register = new Register();
		return "/common/index.xhtml/faces-redirect=true";
	}

	public void edit() {
	}

	public void save() {
		MessageReturn ret = new MessageReturn();

		try {
			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/register");
			
			if(loadDebits){
				createTypeAccount(2L);//debit
			} else {
				createTypeAccount(1L);//credit
			}
			
			register.setUser(userMBean.getLoggedUser().getSuperUser() == null ? userMBean.getLoggedUser() : userMBean.getLoggedUser().getSuperUser());
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
			// if (refreshList) {
			// loadList();
			// }
			createRegister();
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
			loadList();
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

	public SelectItem[] getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(SelectItem[] descriptions) {
		this.descriptions = descriptions;
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
}
