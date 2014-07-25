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

import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.util.FacesUtil;
import com.mconnti.moneymanager.util.MessageFactory;

@SessionScoped
@ManagedBean(name = "descriptionMBean")
public class DescriptionMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{userMBean}")  
	private UserMBean userMBean;

	private List<Description> descriptionList;

	private Description description;

	private Description[] selectedDescriptions;

	private String host = null;
	
	private Boolean showFormDescription = false;

	public DescriptionMBean() {
		this.description = new Description();
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			String[] str = ((HttpServletRequest) request).getRequestURL().toString().split("mmanager");
			host = str[0];
		}
	}
	
	private User superUser() {
		return userMBean.getLoggedUser();
	}

	private void loadList() {
		try {
			
			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/description");
			description.setTypeAccount(null);
			description.setUser(superUser());

			request.body(MediaType.APPLICATION_JSON, description);
			ClientResponse<Description> response = request.put(Description.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			descriptionList = (List<Description>) response.getEntity(new GenericType<List<Description>>() {
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
		showFormDescription = false;
		return "/common/listDescription.xhtml?faces-redirect=true";
	}

	public void newDescription() {
		showFormDescription = true;
		this.description = new Description();
	}

	public void cancel() {
		showFormDescription = false;
		this.description = new Description();
	}

	public void edit() {
		showFormDescription = true;
	}

	public void save() {
		MessageReturn ret = new MessageReturn();

		try {
			
			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/description");
			description.setUser(superUser());
			request.body(MediaType.APPLICATION_JSON, description);
			
			ClientResponse<Description> response = request.post(Description.class);

			ret = response.getEntity(MessageReturn.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			if (ret.getDescription() == null) {
				throw new Exception(ret.getMessage());
			} else {
				FacesUtil.showSuccessMessage(ret.getMessage());
			}
			showFormDescription = false;
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
			for (Description description : selectedDescriptions) {
				ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/description");
				request.body(MediaType.APPLICATION_JSON, description);

				ClientResponse<Description> response = request.delete(Description.class);

				ret = response.getEntity(MessageReturn.class);
			}

			if (selectedDescriptions.length > 1) {
				FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_description_deleted_successfully_mult", superUser().getLanguage(), null));
			} else {
				FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_deleted_successfully", superUser().getLanguage(), null));
			}
			loadList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(ret.getMessage());
		}
	}

	public List<Description> getDescriptionList() {
		return descriptionList;
	}

	public void setDescriptionList(List<Description> descriptionList) {
		this.descriptionList = descriptionList;
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
		this.description = description;
	}

	public Description[] getSelectedDescriptions() {
		return selectedDescriptions;
	}

	public void setSelectedDescriptions(Description[] selectedDescriptions) {
		this.selectedDescriptions = selectedDescriptions;
	}

	public UserMBean getUserMBean() {
		return userMBean;
	}

	public void setUserMBean(UserMBean userMBean) {
		this.userMBean = userMBean;
	}

	public Boolean getShowFormDescription() {
		return showFormDescription;
	}

	public void setShowFormDescription(Boolean showFormDescription) {
		this.showFormDescription = showFormDescription;
	}

}
