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
import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;

import com.mconnti.moneymanager.entity.Config;
import com.mconnti.moneymanager.entity.Role;
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

	private static final long serialVersionUID = 1L;

	FacesContext fc = FacesContext.getCurrentInstance();
	
	HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);

	private List<User> userList;

	private List<Role> roleList;

	private User user;

	private User loggedUser;
	
	private Config configLoggedUser;

	private User[] selectedUsers;

	private SelectItem[] roles;

	private Role role = new Role();

	private Boolean refreshList = false;

	private Boolean showPassword = true;

	private Boolean showEditNewButton = true;
	
	private Boolean showFormUser = false;

	private String host = null;

	private String logoutURL = null;

	private String locale;
	
	private String confirmPassword;
	
	private Boolean emailInUse = false;
	
	private Boolean usernameInUse = false;
	
	private Boolean disableSecretPhrase = false;
	
	private Boolean disableUsername = false;
	
	public UserMBean() {
		this.user = new User();
		this.user.setRole(new Role());
		this.role = new Role();
		
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
	
	private User superUser() {
		return getLoggedUser();
	}

	public SelectItem[] getRolesSI() {
		Collection<Role> roleList = loadRoleList();
		List<SelectItem> itens = new ArrayList<SelectItem>(roleList.size());

		this.roles = new SelectItem[itens.size()];

		for (Role c : roleList) {
			itens.add(new SelectItem(c.getId(), c.getRole()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	public void loadRoles() {
		this.roles = getRolesSI();
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
				session.setAttribute("loggedUser", loggedUser);
				configLoggedUser = ret.getConfig();
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
		
//		if(configLoggedUser == null){
//			return "/common/formConfig.xhtml?faces-redirect=true";
//		}

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

	private Collection<Role> loadRoleList() {
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/role");
			ClientResponse<Role> response = request.get(Role.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			roleList = (List<Role>) response.getEntity(new GenericType<List<Role>>() {
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return roleList;
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

	public String listAdm() {
		showEditNewButton = false;
		this.showFormUser = false;
		this.disableUsername = false;
		loadList();
		return "/common/listUser.xhtml?faces-redirect=true";
	}

	public String listSuperUser() {
		this.showEditNewButton = true;
		this.showFormUser = false;
		this.disableUsername = false;
		loadListByParameter();
		return "/common/listUser.xhtml?faces-redirect=true";
	}

	public String logout() {
		this.loggedUser = null;
		this.user = new User();
		this.showFormUser = false;
		session.removeAttribute("loggedUser");
		return "/index.xhtml?faces-redirect=true";
	}

	public void newUser() {
		loggedUser = null;
		this.user = new User();
		this.user.setAdmin(false);
		this.role = new Role();
		this.role.setId(SUPER_USER);
		this.user.setRole(role);
		this.showPassword = true;
		this.showFormUser = true;
		this.disableSecretPhrase = false;
	}

	public void newParentUser() {
		this.user = new User();
		this.user.setAdmin(false);
		this.role = new Role();
		this.role.setId(USER);
		this.user.setRole(role);
		this.user.setSuperUser(loggedUser);
		this.user.setDefaultPassword(true);
		user.setSecretPhrase(superUser().getSecretPhrase());
		this.showPassword = false;
		this.refreshList = true;
		this.showFormUser = true;
		this.disableSecretPhrase = true;
		this.disableUsername = false;
		loadRoles();
	}

	public void cancel() {
		this.user = new User();
		this.showFormUser = false;
	}

	public void cancelApp(){
		this.user = new User();
		this.showFormUser = false;
		this.showEditNewButton = true;
	}
	
	public void edit() {
		showPassword = false;
		this.showFormUser = true;
		this.disableUsername = true;
		loadRoles();
	}
	
	public String saveNewPassword(){
		MessageReturn ret = new MessageReturn();
		if(loggedUser.getPassword().equals(confirmPassword)){
			if(loggedUser.getSecretPhrase().equals(loggedUser.getSuperUser().getSecretPhrase())){
				try {
					ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/user");
					request.body(MediaType.APPLICATION_JSON, loggedUser);

					ClientResponse<User> response = request.post(User.class);
					
					ret = response.getEntity(MessageReturn.class);

					if (response.getStatus() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
					}

					if (ret.getUser() == null) {
						throw new Exception(ret.getMessage());
					} else {
						FacesUtil.showSuccessMessage(ret.getMessage());
						loggedUser.setDefaultPassword(false);
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
					FacesUtil.showAErrorMessage(e.getMessage());
				}
			} else {
				FacesUtil.showAErrorMessage(MessageFactory.getMessage("lb_secret_phrase_not_match", loggedUser.getLanguage(), null));
			}
		}else{
			FacesUtil.showAErrorMessage(MessageFactory.getMessage("lb_password_not_match", loggedUser.getLanguage(), null));
		}
		return "/common/index.xhtml?faces-redirect=true";
	}

	public void save() {
		MessageReturn ret = new MessageReturn();

		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/user");
			request.body(MediaType.APPLICATION_JSON, user);

			ClientResponse<User> response = request.post(User.class);
			
			ret = response.getEntity(MessageReturn.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			if (ret.getUser() == null) {
				throw new Exception(ret.getMessage());
			} else {
				this.showFormUser = false;
				FacesUtil.showSuccessMessage(ret.getMessage());
			}
			if (refreshList) {
				loadListByParameter();
			}
			this.disableUsername = false;
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
			for (User user : selectedUsers) {
				ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/user");
				request.body(MediaType.APPLICATION_JSON, user);

				ClientResponse<User> response = request.delete(User.class);

				ret = response.getEntity(MessageReturn.class);
			}

			if (selectedUsers.length > 1) {
				FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_user_deleted_successfully_mult", loggedUser.getLanguage(), null));
			} else {
				FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_deleted_successfully", loggedUser.getLanguage(), null));
			}
			if (showEditNewButton) {
				loadListByParameter();
			} else {
				loadList();
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(ret.getMessage());
		}
	}

	public void checkEmail() throws ValidatorException {
		String typed = this.user.getEmail();
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(typed);
		boolean matchFound = m.matches();

		if (!matchFound) {
			FacesMessage message = new FacesMessage(MessageFactory.getMessage("error_invalid_email", "en", null));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", message);
		}
	}

	private Boolean verifyEmailExists() {
		MessageReturn ret = new MessageReturn();
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/user/emailcheck");
			request.body(MediaType.APPLICATION_JSON, user);
			ClientResponse<User> response = request.put(User.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			ret = response.getEntity(MessageReturn.class);

			if (ret.getUser() != null) {
				return true;
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private Boolean verifyUsernameExists() {
		MessageReturn ret = new MessageReturn();
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/user/usernamecheck");
			request.body(MediaType.APPLICATION_JSON, user);
			ClientResponse<User> response = request.put(User.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			ret = response.getEntity(MessageReturn.class);

			if (ret.getUser() != null) {
				return true;
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void existsEmail() throws ValidatorException {
		this.user.setPassword("");
		emailInUse  = verifyEmailExists();
		if (this.user.getId() == null && emailInUse) {
			FacesMessage message = new FacesMessage(MessageFactory.getMessage("error_email_in_use", "en", null));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("", message);
		}
	}

	public void existsUsername() throws ValidatorException {
		usernameInUse = verifyUsernameExists();
		this.user.setPassword("");
		if (this.user.getId() == null && usernameInUse) {
			FacesMessage message = new FacesMessage(MessageFactory.getMessage("error_username_in_use", "en", null));
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

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public SelectItem[] getRoles() {
		return roles;
	}

	public void setRoles(SelectItem[] roles) {
		this.roles = roles;
	}

	public Boolean getShowFormUser() {
		return showFormUser;
	}

	public void setShowFormUser(Boolean showFormUser) {
		this.showFormUser = showFormUser;
	}

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

	public Config getConfigLoggedUser() {
		return configLoggedUser;
	}

	public void setConfigLoggedUser(Config configLoggedUser) {
		this.configLoggedUser = configLoggedUser;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Boolean getEmailInUse() {
		return emailInUse;
	}

	public void setEmailInUse(Boolean emailInUse) {
		this.emailInUse = emailInUse;
	}

	public Boolean getUsernameInUse() {
		return usernameInUse;
	}

	public void setUsernameInUse(Boolean usernameInUse) {
		this.usernameInUse = usernameInUse;
	}

	public Boolean getDisableSecretPhrase() {
		return disableSecretPhrase;
	}

	public void setDisableSecretPhrase(Boolean disableSecretPhrase) {
		this.disableSecretPhrase = disableSecretPhrase;
	}

	public Boolean getDisableUsername() {
		return disableUsername;
	}

	public void setDisableUsername(Boolean disableUsername) {
		this.disableUsername = disableUsername;
	}
}
