package com.mconnti.moneymanager.web.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.GregorianCalendar;
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

import com.mconnti.moneymanager.entity.Planning;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.util.FacesUtil;

@SessionScoped
@ManagedBean(name = "planningMBean")
public class PlanningMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{userMBean}")
	private UserMBean userMBean;

	private List<Planning> planningList;

	private Planning planning;

	private Planning[] selectedPlanning;

	private String host = null;

	private Boolean showForm = true;

	public PlanningMBean() {
		this.planning = new Planning();

		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			String[] str = ((HttpServletRequest) request).getRequestURL().toString().split("mmanager");
			host = str[0];
		}
	}

	private void createPlanning() {
		this.planning = new Planning();
	}

	private List<Planning> loadList() {
		try {
			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/planning/list");
			
			planning.setUser(userMBean.getLoggedUser().getSuperUser() == null ? userMBean.getLoggedUser() : userMBean.getLoggedUser().getSuperUser());
			request.body(MediaType.APPLICATION_JSON, planning);
			
			ClientResponse<Planning> response = request.put(Planning.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			planningList = response.getEntity(new GenericType<List<Planning>>() {
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return planningList;
	}

	public String list() {
		showForm = true;
		createPlanning();
		loadList();
		return "/common/formPlanning.xhtml?faces-redirect=true";
	}


	public String cancel() {
		showForm = false;
		this.planning = new Planning();
		return "/common/index.xhtml/faces-redirect=true";
	}
	
	public void cancelForm(){
		showForm = false;
	}
	
	public void calculate(){
		MessageReturn ret = new MessageReturn();
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/planning");

			planning.setUser(userMBean.getLoggedUser().getSuperUser() == null ? userMBean.getLoggedUser() : userMBean.getLoggedUser().getSuperUser());
			request.body(MediaType.APPLICATION_JSON, planning);
			
			ClientResponse<Planning> response = request.put(Planning.class);

			ret = response.getEntity(MessageReturn.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			if (ret.getPlanning() == null) {
				throw new Exception(ret.getMessage());
			} else {
				planning = ret.getPlanning();
			}
			showForm = true;
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
			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/planning");

			planning.setDate(new GregorianCalendar());
			planning.setUser(userMBean.getLoggedUser().getSuperUser() == null ? userMBean.getLoggedUser() : userMBean.getLoggedUser().getSuperUser());
			request.body(MediaType.APPLICATION_JSON, planning);

			ClientResponse<Planning> response = request.post(Planning.class);

			ret = response.getEntity(MessageReturn.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			if (ret.getPlanning() == null) {
				throw new Exception(ret.getMessage());
			} else {
				FacesUtil.showSuccessMessage(ret.getMessage());
			}
			createPlanning();
			list();
			showForm = false;
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
			for (Planning planning : selectedPlanning) {
				ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/planning");
				request.body(MediaType.APPLICATION_JSON, planning);

				ClientResponse<Planning> response = request.delete(Planning.class);

				ret = response.getEntity(MessageReturn.class);
			}

			if (selectedPlanning.length > 1) {
				// FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_debit_deleted_successfully_mult", userMBean.getLoggedUser().getLanguage()));
			} else {
				// FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_deleted_successfully", userMBean.getLoggedUser().getLanguage()));
			}
			// loadList();
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

	public List<Planning> getPlanningList() {
		return planningList;
	}

	public void setPlanningList(List<Planning> planningList) {
		this.planningList = planningList;
	}

	public Planning getPlanning() {
		return planning;
	}

	public void setPlanning(Planning planning) {
		this.planning = planning;
	}

	public Planning[] getSelectedPlanning() {
		return selectedPlanning;
	}

	public void setSelectedPlanning(Planning[] selectedPlanning) {
		this.selectedPlanning = selectedPlanning;
	}


	public Boolean getShowForm() {
		return showForm;
	}

	public void setShowForm(Boolean showForm) {
		this.showForm = showForm;
	}
}