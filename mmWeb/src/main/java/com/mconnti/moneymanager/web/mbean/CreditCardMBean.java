package com.mconnti.moneymanager.web.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;

import com.mconnti.moneymanager.entity.CreditCard;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.util.FacesUtil;
import com.mconnti.moneymanager.util.MessageFactory;

@SessionScoped
@ManagedBean(name = "creditCardMBean")
public class CreditCardMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{userMBean}")
	private UserMBean userMBean;

	private List<CreditCard> creditCardList;

	private CreditCard creditCard;

	private CreditCard[] selectedCreditCard;

	private String host = null;

	public CreditCardMBean() {
		this.creditCard = new CreditCard();
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			String[] str = ((HttpServletRequest) request).getRequestURL().toString().split("mmanager");
			host = str[0];
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

	public void newCreditCard() {
		this.creditCard = new CreditCard();
	}

	public String cancel() {
		return "index.xhtml\faces-redirect=true";
	}

	public void edit() {
	}

	public void save() {
		MessageReturn ret = new MessageReturn();

		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/creditcard");
			creditCard.setUser(userMBean.getLoggedUser());
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
			loadList();
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
				FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_creditcard_deleted_successfully_mult", userMBean.getLoggedUser().getLanguage()));
			} else {
				FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_deleted_successfully", userMBean.getLoggedUser().getLanguage()));
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

	public UserMBean getUserMBean() {
		return userMBean;
	}

	public void setUserMBean(UserMBean userMBean) {
		this.userMBean = userMBean;
	}
}
