package com.mconnti.moneymanager.web.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;

import com.mconnti.moneymanager.entity.Credit;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.util.FacesUtil;
import com.mconnti.moneymanager.util.MessageFactory;

@SessionScoped
@ManagedBean(name = "creditMBean")
public class CreditMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	private List<Credit> creditList;

	private User loggedUser;
	
	private Credit credit;
	
	private Credit[] selectedCredit;

	private Boolean refreshList = false;

	private String host = null;

	private String locale;

	public CreditMBean() {
		this.credit = new Credit();
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			String[] str = ((HttpServletRequest) request).getRequestURL().toString().split("mmanager");
			host = str[0];
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

	private void loadList() {
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/credit");
			ClientResponse<Credit> response = request.get(Credit.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			creditList = (List<Credit>) response.getEntity(new GenericType<List<Credit>>() {
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
		return "/common/listCredit.xhtml?faces-redirect=true";
	}

	public void newDesc() {
		this.credit = new Credit();
	}

	public String cancel() {
		this.credit = new Credit();
		return "index.xhtml\faces-redirect=true";
	}

	public void edit() {
	}

	public void save() {
		MessageReturn ret = new MessageReturn();

		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/credit");
			request.body(MediaType.APPLICATION_JSON, credit);

			ClientResponse<Credit> response = request.post(Credit.class);

			ret = response.getEntity(MessageReturn.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			if (ret.getCredit() == null) {
				throw new Exception(ret.getMessage());
			} else {
				FacesUtil.showSuccessMessage(ret.getMessage());
			}
			if (refreshList) {
				loadList();
			}
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
			for (Credit credit : selectedCredit) {
				ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/credit");
				request.body(MediaType.APPLICATION_JSON, credit);

				ClientResponse<Credit> response = request.delete(Credit.class);

				ret = response.getEntity(MessageReturn.class);
			}

			if (selectedCredit.length > 1) {
				FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_credit_deleted_successfully_mult", loggedUser.getLanguage()));
			} else {
				FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_deleted_successfully", loggedUser.getLanguage()));
			}
			loadList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(ret.getMessage());
		}
	}

	public List<Credit> getCreditList() {
		return creditList;
	}

	public void setCreditList(List<Credit> creditList) {
		this.creditList = creditList;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	public Credit getCredit() {
		return credit;
	}

	public void setCredit(Credit credit) {
		this.credit = credit;
	}

	public Credit[] getSelectedCredit() {
		return selectedCredit;
	}

	public void setSelectedCredit(Credit[] selectedCredit) {
		this.selectedCredit = selectedCredit;
	}

	public Boolean getRefreshList() {
		return refreshList;
	}

	public void setRefreshList(Boolean refreshList) {
		this.refreshList = refreshList;
	}

}
