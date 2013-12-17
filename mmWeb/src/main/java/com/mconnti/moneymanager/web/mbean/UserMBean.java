package com.mconnti.moneymanager.web.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;

import com.mconnti.moneymanager.entity.City;
import com.mconnti.moneymanager.entity.Country;
import com.mconnti.moneymanager.entity.Role;
import com.mconnti.moneymanager.entity.State;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.util.FacesUtil;
import com.mconnti.moneymanager.util.MessageFactory;

@SessionScoped
@ManagedBean(name = "userMBean")
public class UserMBean implements Serializable {

	public static Long ADMIN = 1L;

	public static Long SUPER_USER = 2L;

	public static Long USER = 3L;
	
	public static String DEFAUL_PASSWORD = "123456";

	private static final long serialVersionUID = 1L;

	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	private List<User> userList;

	private List<Country> countryList;

	private List<State> stateList;

	private List<City> cityList;

	private User user;

	private User loggedUser;

	private User[] selectedUsers;

	private SelectItem[] countries;

	private SelectItem[] states;

	private SelectItem[] cities;

	private Country country = new Country();

	private State state = new State();

	private City city = new City();

	private Role role = new Role();

	private Boolean refreshList = false;

	private Boolean showPassword = true;
	
	private Boolean showEditNewButton = true;

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
		this.country = new Country();
		this.state = new State();
		this.city = new City();
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
	}

	public SelectItem[] getCountriesByLocale(String locale) {
		Collection<Country> countryList = loadCountryList(locale);
		List<SelectItem> itens = new ArrayList<SelectItem>(countryList.size());

		this.countries = new SelectItem[itens.size()];

		for (Country c : countryList) {
			itens.add(new SelectItem(c.getId(), c.getName()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	public void loadStates() {
		this.cities = new SelectItem[1];

		this.states = getStateByCountry(country);
	}

	public void loadCities() {
		this.cities = getCityByState(state);
	}

	public SelectItem[] getStateByCountry(Country country) {
		Collection<State> stateList = loadStateListByCountry(country);
		List<SelectItem> itens = new ArrayList<SelectItem>(countryList.size());

		this.states = new SelectItem[itens.size()];

		for (State c : stateList) {
			itens.add(new SelectItem(c.getId(), c.getName()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	public SelectItem[] getCityByState(State state) {
		Collection<City> cityList = loadCityListByState(state);
		List<SelectItem> itens = new ArrayList<SelectItem>(countryList.size());

		this.cities = new SelectItem[itens.size()];

		for (City c : cityList) {
			itens.add(new SelectItem(c.getId(), c.getName()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	public String login() {
		MessageReturn ret = new MessageReturn();
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/user/login");
			request.body(MediaType.APPLICATION_JSON, user);
			ClientResponse<User> response = request.put(User.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			ret = response.getEntity(MessageReturn.class);

			if (ret.getUser() == null) {
				throw new Exception(ret.getMessage());
			} else {
				loggedUser = ret.getUser();
				FacesUtil.showSuccessMessage(ret.getMessage());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(e.getMessage());
			return "";
		}

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
	
	private void loadListByParameter() {
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/user");
			request.body(MediaType.APPLICATION_JSON, loggedUser);
			ClientResponse<User> response = request.put(User.class);

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
			request.body(MediaType.APPLICATION_JSON, "'" + locale + "'");
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

	private Collection<State> loadStateListByCountry(Country country) {
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/state");
			request.body(MediaType.APPLICATION_JSON, country);
			ClientResponse<Country> response = request.put(Country.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			stateList = response.getEntity(new GenericType<List<State>>() {
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stateList;
	}

	private Collection<City> loadCityListByState(State state) {
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/city");
			request.body(MediaType.APPLICATION_JSON, state);
			ClientResponse<State> response = request.put(State.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			cityList = response.getEntity(new GenericType<List<City>>() {
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityList;
	}

	public String listAdm() {
		showEditNewButton = false;
		loadList();
		return "/common/listUser.xhtml?faces-redirect=true";
	}
	
	public String listSuperUser() {
		Map<String, String> queryParams = new LinkedHashMap<String, String>();
		queryParams.put(" where x.superUser", "= "+loggedUser.getId());
		queryParams.put(" or x.id", "= "+loggedUser.getId());
		
		loggedUser.setOrderBy("x.name asc");
		loggedUser.setQueryParams(queryParams);
		showEditNewButton = true;
		loadListByParameter();

		return "/common/listUser.xhtml?faces-redirect=true";
	}

	public String logout() {
		this.loggedUser = null;
		return "/index.xhtml?faces-redirect=true";
	}

	private void loadDefaultCombos() {
		this.countries = getCountriesByLocale(locale);
	}

	public void newUser() {
		this.user = new User();
		this.country = new Country();
		this.state = new State();
		this.city = new City();
		this.user.setAdministrator(false);
		this.role = new Role();
		this.role.setId(SUPER_USER);
		this.user.setRole(role);
		this.showPassword = true;
		loadDefaultCombos();
	}

	public void newParentUser() {
		this.user = new User();
		this.country = new Country();
		this.state = new State();
		this.city = new City();
		this.user.setAdministrator(false);
		this.role = new Role();
		this.role.setId(USER);
		this.user.setRole(role);
		this.user.setSuperUser(loggedUser);
		this.user.setPassword(DEFAUL_PASSWORD);
		this.showPassword = false;
		this.refreshList = true;
		loadDefaultCombos();
	}

	public String cancel() {
		this.user = new User();
		return "index.xhtml\faces-redirect=true";
	}

	public void edit() {
		showPassword = false;
	}

	public void save() {
		MessageReturn ret = new MessageReturn();

		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/user");
			user.setCity(city);
			request.body(MediaType.APPLICATION_JSON, user);

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
			if (refreshList) {
				loadListByParameter();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(e.getMessage());
		}
//		if (loggedUser != null) {
//			return "/common/listUser.xhtml?faces-redirect=true";
//		}
//		return "/index.xhtml";
	}

	public void delete() {
		MessageReturn ret = new MessageReturn();
		try {
			for (User user : selectedUsers) {
				ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/user");
				request.body(MediaType.APPLICATION_JSON, user);

				ClientResponse<User> response = request.delete(User.class);

				ret = response.getEntity(MessageReturn.class);
			}

			if (selectedUsers.length > 1) {
				FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_user_deleted_successfully_mult", loggedUser.getLanguage()));
			} else {
				FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_deleted_successfully", loggedUser.getLanguage()));
			}
			loadList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(ret.getMessage());
		}
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

	public List<Country> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<Country> countryList) {
		this.countryList = countryList;
	}

	public List<State> getStateList() {
		return stateList;
	}

	public void setStateList(List<State> stateList) {
		this.stateList = stateList;
	}

	public List<City> getCityList() {
		return cityList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}

	public Boolean getRefreshList() {
		return refreshList;
	}

	public void setRefreshList(Boolean refreshList) {
		this.refreshList = refreshList;
	}

	public Boolean getShowEditNewButton() {
		return showEditNewButton;
	}

	public void setShowEditNewButton(Boolean showEditNewButton) {
		this.showEditNewButton = showEditNewButton;
	}

}
