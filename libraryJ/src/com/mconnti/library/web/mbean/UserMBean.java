package com.mconnti.library.web.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.mconnti.library.entity.User;
import com.mconnti.library.entity.xml.MessageReturn;
import com.mconnti.library.util.FacesUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

@SessionScoped
@ManagedBean(name = "userMBean")
public class UserMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// captura a sessão do contexto criado pelo JavaServer Faces
	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	private List<User> userList;

	private User user;

	@ManagedProperty(value = "#{loggedUser}")
	private User loggedUser;

	private User[] selectedUsers;

	private Boolean isAdmin = false;

	private Boolean showPassword = true;

	Client client = null;

	String host = null;
	
	String logoutURL = null;
	
	String locale;

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
		client = Client.create();
		this.user = new User();
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			String[] str = ((HttpServletRequest) request).getRequestURL().toString().split("library");
			host = str[0];
			logoutURL = host+"libraryJ/index.jsf";
		}
		if(loggedUser == null){
			locale = fc.getExternalContext().getRequestLocale().toString();
			if(!locale.equals("pt_BR")){
				locale = "en";
			}
		}else{
			locale = loggedUser.getLanguage();
		}
	}
	
	public String login() {
		MessageReturn ret = new MessageReturn();

		try {

			WebResource webResource = client.resource(host + "libraryWS/user");
			user.setLanguage(locale);
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, user);

			if (response.getStatus() != 201 && response.getStatus() != 200 && response.getStatus() != 500) {
				throw new Exception("Failed : HTTP error code : " + response.getStatus());
			}

			ret = response.getEntity(MessageReturn.class);

			if (ret.getUser() == null) {
				throw new Exception(ret.getMessage());
			} else {
				setLoggedUser(ret.getUser());
				FacesUtil.showSuccessMessage(ret.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(e.getMessage());
			return "";
		}

		return "/common/index.xhtml?faces-redirect=true";
	}

	JasperPrint jasperPrint;

	public void init() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(userList);
		jasperPrint = JasperFillManager.fillReport(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/relatorios/userList.jasper"), null, beanCollectionDataSource);
	}

	public void print(ActionEvent actionEvent) {
		try {
			init();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "inline; filename=report.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadList() {
		WebResource webResource = client.resource(host + "libraryWS/user");

		userList = webResource.accept(MediaType.APPLICATION_JSON).get(new GenericType<List<User>>() {});
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
			WebResource webResource = client.resource(host + "libraryWS/user");

			if (!isAdmin) {
				user.setAdmin(false);
			}

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, user);

			if (response.getStatus() != 201 && response.getStatus() != 200) {
				ret.setMessage("Failed : HTTP error code : " + response.getStatus());
				throw new Exception(ret.getMessage());
			}

			ret = response.getEntity(MessageReturn.class);

			if (ret.getUser() == null) {
				throw new Exception(ret.getMessage());
			} else {
				FacesUtil.showSuccessMessage(ret.getMessage());
			}
			if (isAdmin) {
				loadList();
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(ret.getMessage());
		}
		if (loggedUser != null) {
			return "/common/listUser.xhtml?faces-redirect=true";
		}
		return "";
	}

	public void delete() {
		MessageReturn ret = new MessageReturn();
		try {
			for (User user : selectedUsers) {
				WebResource webResource = client.resource(host + "libraryWS/user/" + user.getId());

				ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);

				if (response.getStatus() != 201 && response.getStatus() != 200) {
					ret.setMessage("Failed : HTTP error code : " + response.getStatus());
					throw new Exception(ret.getMessage());
				}

				ret = response.getEntity(MessageReturn.class);
			}

			if (selectedUsers.length > 1) {
				FacesUtil.showSuccessMessage(loggedUser.getLanguage().equals("pt_BR") ? "Usuários excluidos com sucesso!" : "User successfully deleted!");
			} else {
				FacesUtil.showSuccessMessage(loggedUser.getLanguage().equals("pt_BR") ? "Removido com sucesso!" : "Successfully deleted!");
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

}
