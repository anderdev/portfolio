package com.mconnti.moneymanager.web.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

import com.mconnti.moneymanager.entity.Closure;
import com.mconnti.moneymanager.entity.Currency;
import com.mconnti.moneymanager.entity.TypeClosure;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.util.FacesUtil;
import com.mconnti.moneymanager.util.MessageFactory;

@SessionScoped
@ManagedBean(name = "closureMBean")
public class ClosureMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{userMBean}")
	private UserMBean userMBean;

	private List<Closure> closureList;
	
	private List<Closure> searchClosureList;

	private Closure closure;
	
	private Closure searchClosure;

	private Closure[] selectedClosure;

	private String host = null;

	private List<Currency> currencyList;

	private List<TypeClosure> typeClosureList;
	
	private SelectItem[] currencies;

	private SelectItem[] typeClosures;

	private Currency currency;

	private TypeClosure typeClosure;
	
	private Boolean showMaths = false;
	
	private BigDecimal debitTotal;

	private BigDecimal creditTotal;

	private BigDecimal searchTotal;

	private Boolean loadSearchFooter = false;

	public ClosureMBean() {
		this.closure = new Closure();
		this.currency = new Currency();
		this.typeClosure = new TypeClosure();
		this.searchClosure = new Closure();
		this.searchClosure.setTypeClosure(typeClosure);
		this.searchClosure.setCurrency(currency);
		
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			String[] str = ((HttpServletRequest) request).getRequestURL().toString().split("mmanager");
			host = str[0];
		}
	}

	private void loadCombos() {
		this.typeClosures = loadTypeClosures();
		this.currencies = loadCurrencies();
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

	private void createClosure(Boolean search) {
		this.closure = new Closure();
		this.closure.setTypeClosure(new TypeClosure());
		this.closure.setCurrency(new Currency());
		if(!search){
			setDefaultValues();
		}
	}
	
	private void setDefaultValues() {
		if(userMBean.getConfigLoggedUser() != null){
			closure.setTypeClosure(userMBean.getConfigLoggedUser().getTypeClosure());
			closure.setCurrency(userMBean.getConfigLoggedUser().getCurrency());
		}
		closure.setDate(new Date());
	}
	
	private List<Closure> loadList(Boolean useSearch) {
		try {
			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/closure/list");
			
			if (useSearch) {
				closure = searchClosure;
			}
			
			closure.setUser(userMBean.getLoggedUser().getSuperUser() == null ? userMBean.getLoggedUser() : userMBean.getLoggedUser().getSuperUser());
			request.body(MediaType.APPLICATION_JSON, closure);
			
			ClientResponse<Closure> response = request.put(Closure.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			if (useSearch) {
				searchClosureList = response.getEntity(new GenericType<List<Closure>>() { });
			}else{
				closureList = response.getEntity(new GenericType<List<Closure>>() { });
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return closureList;
	}

	public String list() {
		showMaths = false;
		createClosure(true);
		this.searchClosure = this.closure;
		this.searchClosure.setSearch(true);
		loadCombos();
		searchClosureList = new ArrayList<>();
		return "/common/listClosure.xhtml?faces-redirect=true";
	}
	
	public void search() {
		loadSearchFooter = false;
		searchTotal = new BigDecimal(0.0);
		debitTotal = new BigDecimal(0.0);
		creditTotal = new BigDecimal(0.0);
		searchClosure.setSearch(true);
		loadList(true);
		showMaths = true;
		searchClosure.setSearch(false);
	}
	
	
	public String closure(){
		createClosure(false);
		closure.setSearch(false);
		loadList(false);
		loadCombos();
		searchClosure = null;
		return "/common/formClosure.xhtml?faces-redirect=true";
	}


	public String cancel() {
		showMaths = false;
		this.closure = new Closure();
		return "/common/index.xhtml/faces-redirect=true";
	}
	
	public void cancelMaths(){
		showMaths = false;
	}
	
	public void calculate(){
		MessageReturn ret = new MessageReturn();
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/closure");

			closure.setUser(userMBean.getLoggedUser().getSuperUser() == null ? userMBean.getLoggedUser() : userMBean.getLoggedUser().getSuperUser());
			request.body(MediaType.APPLICATION_JSON, closure);
			
			ClientResponse<Closure> response = request.put(Closure.class);

			ret = response.getEntity(MessageReturn.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			if (ret.getClosure() == null) {
				throw new Exception(ret.getMessage());
			} else {
				closure = ret.getClosure();
			}
			showMaths = true;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void edit() {
	}
	
	public void save() {
		MessageReturn ret = new MessageReturn();

		try {
			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/closure");

			closure.setUser(userMBean.getLoggedUser().getSuperUser() == null ? userMBean.getLoggedUser() : userMBean.getLoggedUser().getSuperUser());
			request.body(MediaType.APPLICATION_JSON, closure);

			ClientResponse<Closure> response = request.post(Closure.class);

			ret = response.getEntity(MessageReturn.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			if (ret.getClosure() == null) {
				throw new Exception(ret.getMessage());
			} else {
				FacesUtil.showSuccessMessage(ret.getMessage());
			}
			createClosure(closure.getSearch());
			loadList(closure.getSearch());
			showMaths = false;
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
			for (Closure closure : selectedClosure) {
				ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/closure");
				request.body(MediaType.APPLICATION_JSON, closure);

				ClientResponse<Closure> response = request.delete(Closure.class);

				ret = response.getEntity(MessageReturn.class);
			}

			if (selectedClosure.length > 1) {
				 FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_closure_successfully_mult", userMBean.getLoggedUser().getLanguage(),null));
			} else {
				 FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_deleted_successfully", userMBean.getLoggedUser().getLanguage(),null));
			}
			loadList(true);
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

	public List<Closure> getClosureList() {
		return closureList;
	}

	public void setClosureList(List<Closure> closureList) {
		this.closureList = closureList;
	}

	public Closure getClosure() {
		return closure;
	}

	public void setClosure(Closure closure) {
		this.closure = closure;
	}

	public Closure[] getSelectedClosure() {
		return selectedClosure;
	}

	public void setSelectedClosure(Closure[] selectedClosure) {
		this.selectedClosure = selectedClosure;
	}

	public SelectItem[] getCurrencies() {
		return currencies;
	}

	public void setCurrencies(SelectItem[] currencies) {
		this.currencies = currencies;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
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

	public SelectItem[] getTypeClosures() {
		return typeClosures;
	}

	public void setTypeClosures(SelectItem[] typeClosures) {
		this.typeClosures = typeClosures;
	}

	public Boolean getShowMaths() {
		return showMaths;
	}

	public void setShowMaths(Boolean showMaths) {
		this.showMaths = showMaths;
	}

	public Closure getSearchClosure() {
		return searchClosure;
	}

	public void setSearchClosure(Closure searchClosure) {
		this.searchClosure = searchClosure;
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

	public BigDecimal getSearchTotal() {
		return searchTotal;
	}

	public void setSearchTotal(BigDecimal searchTotal) {
		this.searchTotal = searchTotal;
	}

	public Boolean getLoadSearchFooter() {
		return loadSearchFooter;
	}

	public void setLoadSearchFooter(Boolean loadSearchFooter) {
		this.loadSearchFooter = loadSearchFooter;
	}

	public List<Closure> getSearchClosureList() {
		return searchClosureList;
	}

	public void setSearchClosureList(List<Closure> searchClosureList) {
		this.searchClosureList = searchClosureList;
	}
}