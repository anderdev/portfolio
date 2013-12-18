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

import com.mconnti.moneymanager.entity.CreditCard;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.util.FacesUtil;
import com.mconnti.moneymanager.util.MessageFactory;

@SessionScoped
@ManagedBean(name = "creditCardMBean")
public class CreditCardMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

	private List<CreditCard> creditCardList;

	private User loggedUser;
	
	private CreditCard creditCard;
	
	private CreditCard[] selectedCreditCard;

	private Boolean refreshList = false;

	private String host = null;

	private String locale;

	public CreditCardMBean() {
		this.creditCard = new CreditCard();
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

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/creditcard");
			ClientResponse<CreditCard> response = request.get(CreditCard.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			creditCardList = (List<CreditCard>) response.getEntity(new GenericType<List<CreditCard>>() {
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
		return "/common/listCreditCard.xhtml?faces-redirect=true";
	}

	public void newDesc() {
		this.creditCard = new CreditCard();
	}

	public String cancel() {
		this.creditCard = new CreditCard();
		return "index.xhtml\faces-redirect=true";
	}

	public void edit() {
	}

	public void save() {
		MessageReturn ret = new MessageReturn();

		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/creditcard");
			request.body(MediaType.APPLICATION_JSON, creditCard);

			ClientResponse<CreditCard> response = request.post(CreditCard.class);

			ret = response.getEntity(MessageReturn.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			if (ret.getCreditCard() == null) {
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
			for (CreditCard creditCard : selectedCreditCard) {
				ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/creditcard");
				request.body(MediaType.APPLICATION_JSON, creditCard);

				ClientResponse<CreditCard> response = request.delete(CreditCard.class);

				ret = response.getEntity(MessageReturn.class);
			}

			if (selectedCreditCard.length > 1) {
				FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_creditcard_deleted_successfully_mult", loggedUser.getLanguage()));
			} else {
				FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_deleted_successfully", loggedUser.getLanguage()));
			}
			loadList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(ret.getMessage());
		}
	}

	public List<CreditCard> getCreditCardList() {
		return creditCardList;
	}

	public void setCreditCardList(List<CreditCard> creditCardList) {
		this.creditCardList = creditCardList;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public CreditCard[] getSelectedCreditCard() {
		return selectedCreditCard;
	}

	public void setSelectedCreditCard(CreditCard[] selectedCreditCard) {
		this.selectedCreditCard = selectedCreditCard;
	}

	public Boolean getRefreshList() {
		return refreshList;
	}

	public void setRefreshList(Boolean refreshList) {
		this.refreshList = refreshList;
	}

}
