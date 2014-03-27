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

import com.mconnti.moneymanager.entity.Config;
import com.mconnti.moneymanager.entity.Currency;
import com.mconnti.moneymanager.entity.TypeClosure;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.util.FacesUtil;

@SessionScoped
@ManagedBean(name = "configMBean")
public class ConfigMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{userMBean}")
	private UserMBean userMBean;

	private Config config;

	private TypeClosure typeClosure;

	private Currency currency;

	private String host = null;

	private List<TypeClosure> typeClosureList;

	private List<Currency> currencyList;

	private SelectItem[] typeClosures;

	private SelectItem[] currencies;
	
	public ConfigMBean() {
		this.config = new Config();
		this.config.setCurrency(new Currency());
		this.config.setTypeClosure(new TypeClosure());
		this.typeClosure = new TypeClosure();
		this.currency = new Currency();
		
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			String[] str = ((HttpServletRequest) request).getRequestURL().toString().split("mmanager");
			host = str[0];
		}
	}

	private void createConfig() {
		this.config = new Config();
		this.config.setCurrency(new Currency());
		this.config.setTypeClosure(new TypeClosure());
	}

	public String load() {
		createConfig();
		if(userMBean.getLoggedUser().getConfig() != null){
			this.config = userMBean.getLoggedUser().getConfig();
		}
		loadCombos();
		return "/common/formConfig.xhtml?faces-redirect=true";
	}

	public String cancel() {
		return "/common/index.xhtml?faces-redirect=true";
	}

	public void save() {
		MessageReturn ret = new MessageReturn();

		try {
			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/config/save");

			config.setUser(userMBean.getLoggedUser());
			request.body(MediaType.APPLICATION_JSON, config);

			ClientResponse<Config> response = request.put(Config.class);

			ret = response.getEntity(MessageReturn.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			if (ret.getConfig() == null) {
				throw new Exception(ret.getMessage());
			} else {
				FacesUtil.showSuccessMessage(ret.getMessage());
			}
			createConfig();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(e.getMessage());
		}
	}

	private void loadCombos() {
		this.typeClosures = loadTypeClosures();
		this.currencies = loadCurrencies();
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
	
	public UserMBean getUserMBean() {
		return userMBean;
	}

	public void setUserMBean(UserMBean userMBean) {
		this.userMBean = userMBean;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public TypeClosure getTypeClosure() {
		return typeClosure;
	}

	public void setTypeClosure(TypeClosure typeClosure) {
		this.typeClosure = typeClosure;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public List<TypeClosure> getTypeClosureList() {
		return typeClosureList;
	}

	public void setTypeClosureList(List<TypeClosure> typeClosureList) {
		this.typeClosureList = typeClosureList;
	}

	public List<Currency> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<Currency> currencyList) {
		this.currencyList = currencyList;
	}

	public SelectItem[] getTypeClosures() {
		return typeClosures;
	}

	public void setTypeClosures(SelectItem[] typeClosures) {
		this.typeClosures = typeClosures;
	}

	public SelectItem[] getCurrencies() {
		return currencies;
	}

	public void setCurrencies(SelectItem[] currencies) {
		this.currencies = currencies;
	}
}