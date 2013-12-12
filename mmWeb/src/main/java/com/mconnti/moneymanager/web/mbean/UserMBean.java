package com.mconnti.moneymanager.web.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;

import com.mconnti.moneymanager.entity.City;
import com.mconnti.moneymanager.entity.Country;
import com.mconnti.moneymanager.entity.State;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.util.FacesUtil;

@SessionScoped
@ManagedBean(name = "userMBean")
public class UserMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// captura a sess�o do contexto criado pelo JavaServer Faces
	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	private List<User> userList;
	
	private List<Country> countryList;

	private User user;

	private User loggedUser;

	private User[] selectedUsers;
	
	private SelectItem[] countries;

	private SelectItem[] states;

	private SelectItem[] cities;

	private Country country = new Country();

	private State state = new State();

	private City city = new City();

	private Boolean isAdmin = false;

	private Boolean showPassword = true;

	private String host = null;

	private String logoutURL = null;

	private String locale;

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getLogoutURL() {
		return logoutURL;
	}

	public void setLogoutURL(String logoutURL) {
		this.logoutURL = logoutURL;
	}

	public UserMBean() {
		this.user = new User();
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			String[] str = ((HttpServletRequest) request).getRequestURL().toString().split("mmanager");
			host = str[0];
			logoutURL = host + "mmanager/index.jsf";
		}
		if (loggedUser == null) {
			locale = fc.getExternalContext().getRequestLocale().toString();
			if (!locale.equals("pt_BR")) {
				locale = "en";
			}
		} else {
			locale = loggedUser.getLanguage();
		}
		
		this.countries = getCountriesByLocale(locale);
		this.states = new SelectItem[1];
		states[0] = new SelectItem("0", locale.equals("pt_BR") ? "Selecione..." : "Select...");
		this.cities = new SelectItem[1];
		cities[0] = new SelectItem("0", locale.equals("pt_BR") ? "Selecione..." : "Select...");
	}
	
	public SelectItem[] getCountriesByLocale(String locale) {
		Collection<Country> countryList = loadCountryList(locale);
		List<SelectItem> itens = new ArrayList<SelectItem>(countryList.size());

		this.countries = new SelectItem[itens.size()];

		itens.add(new SelectItem(0, locale.equals("pt_BR") ? "Selecione..." : "Select..."));
		for (Country c : countryList) {
			itens.add(new SelectItem(c.getId(), c.getName()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	public String login() {
		MessageReturn ret = new MessageReturn();
		loadList();
		// try {
		//
		// WebResource webResource = client.resource(host + "libraryWS/user");
		// user.setLanguage(locale);
		// ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, user);
		//
		// if (response.getStatus() != 201 && response.getStatus() != 200 && response.getStatus() != 500) {
		// throw new Exception("Failed : HTTP error code : " + response.getStatus());
		// }
		//
		// ret = response.getEntity(MessageReturn.class);
		//
		// if (ret.getUser() == null) {
		// throw new Exception(ret.getMessage());
		// } else {
		// loggedUser = ret.getUser();
		// FacesUtil.showSuccessMessage(ret.getMessage());
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// FacesUtil.showAErrorMessage(e.getMessage());
		// return "";
		// }

		return "/common/index.xhtml?faces-redirect=true";
	}

	private void loadList() {
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/user");
			ClientResponse<User> response = request.get(User.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			
			userList = (List<User>) response.getEntity(new GenericType<List<User>>() {
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Collection<Country> loadCountryList(String locale) {
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/country");
			request.body("application/json", locale);
			ClientResponse<String> response = request.put(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			countryList = response.getEntity(new GenericType<List<Country>>() {
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countryList;
	}

	public String list() {

		loadList();

		return "/common/listUser.xhtml?faces-redirect=true";
	}

	public String logout() {
		this.loggedUser = null;
		return "/index.xhtml?faces-redirect=true";
	}

	public void newUser() {
		this.user = new User();
		this.user.setAdministrator(true);
		showPassword = true;
	}

	public void newAdminUser() {
		this.user = new User();
		isAdmin = true;
		showPassword = true;
	}

	public String cancel() {
		this.user = new User();
		return "index.xhtml\faces-redirect=true";
	}

	public String cancelLogged() {
		return "/common/listUser.xhtml?faces-redirect=true";
	}

	public void edit() {
		isAdmin = true;
		showPassword = false;
	}

	public String save() {
		MessageReturn ret = new MessageReturn();

		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/user");
			
			request.body("application/json", user);
	 
			ClientResponse<User> response = request.post(User.class);

			ret = response.getEntity(MessageReturn.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			if (ret.getUser() == null) {
				throw new Exception(ret.getMessage());
			} else {
				FacesUtil.showSuccessMessage(ret.getMessage());
			}
			if (isAdmin) {
				loadList();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(e.getMessage());
			return "/index.xhtml";
		}
		if (loggedUser != null) {
			return "/common/listUser.xhtml?faces-redirect=true";
		}
		return "/common/listUser.xhtml?faces-redirect=true";
	}

	public void delete() {
		MessageReturn ret = new MessageReturn();
		// try {
		// for (User user : selectedUsers) {
		// WebResource webResource = client.resource(host + "libraryWS/user/" + user.getId());
		//
		// ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
		//
		// if (response.getStatus() != 201 && response.getStatus() != 200) {
		// ret.setMessage("Failed : HTTP error code : " + response.getStatus());
		// throw new Exception(ret.getMessage());
		// }
		//
		// ret = response.getEntity(MessageReturn.class);
		// }
		//
		// if (selectedUsers.length > 1) {
		// FacesUtil.showSuccessMessage(loggedUser.getLanguage().equals("pt_BR") ? "Usu�rios excluidos com sucesso!" : "User successfully deleted!");
		// } else {
		// FacesUtil.showSuccessMessage(loggedUser.getLanguage().equals("pt_BR") ? "Removido com sucesso!" : "Successfully deleted!");
		// }
		// loadList();
		// } catch (Exception e) {
		// e.printStackTrace();
		// FacesUtil.showAErrorMessage(ret.getMessage());
		// }
	}

	public void checkEmail() throws ValidatorException {
		String digitado = this.user.getEmail();
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(digitado);
		boolean matchFound = m.matches();

		if (!matchFound) {
			FacesMessage message = new FacesMessage("Invalid email address, please check!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", message);
		}
	}

	public void existsEmail() throws ValidatorException {
		String email = this.user.getEmail();
		this.user.setEmail(email);
		Boolean exists = false; // userBO.existsEmail(this.user);
		if (this.user.getId() == null && exists) {
			FacesMessage message = new FacesMessage("Thos email is already been used in our database!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", message);
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public User[] getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(User[] selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Boolean getShowPassword() {
		return showPassword;
	}

	public void setShowPassword(Boolean showPassword) {
		this.showPassword = showPassword;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	public SelectItem[] getCountries() {
		return countries;
	}

	public void setCountries(SelectItem[] countries) {
		this.countries = countries;
	}

	public SelectItem[] getStates() {
		return states;
	}

	public void setStates(SelectItem[] states) {
		this.states = states;
	}

	public SelectItem[] getCities() {
		return cities;
	}

	public void setCities(SelectItem[] cities) {
		this.cities = cities;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
