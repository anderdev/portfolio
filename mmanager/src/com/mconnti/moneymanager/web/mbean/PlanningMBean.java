package com.mconnti.moneymanager.web.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
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

import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.Planning;
import com.mconnti.moneymanager.entity.PlanningGroup;
import com.mconnti.moneymanager.entity.PlanningItem;
import com.mconnti.moneymanager.entity.TypeAccount;
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

	private TypeAccount typeAccount;

	private Description description;

	private PlanningGroup planningGroup;

	private PlanningItem planningItem;

	private Planning[] selectedPlanning;

	private String host = null;

	private Boolean showForm = false;

	private List<TypeAccount> typeAccountList;

	private List<Description> descriptionList;

	private SelectItem[] typeAccounts;

	private SelectItem[] descriptions;

	public PlanningMBean() {
		this.planning = new Planning();
		this.planningGroup = new PlanningGroup();
		this.planningGroup.setTypeAccount(new TypeAccount());
		this.planningGroup.setDescription(new Description());
		this.planningItem = new PlanningItem();
		this.typeAccount = new TypeAccount();
		this.description = new Description();

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
		showForm = false;
		createPlanning();
		loadList();
		return "/common/formPlanning.xhtml?faces-redirect=true";
	}

	public void newTab() {
		showForm = true;
		this.planning = new Planning();
	}

	public void newItem() {
		this.planningItem = new PlanningItem();
		loadTypeAccount();
	}

	public void cancelTab() {
		showForm = false;
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

	public void saveItem() {

	}

	public SelectItem[] loadTypeAccounts() {
		typeAccountList = loadTypeAccountList();

		List<SelectItem> itens = new ArrayList<SelectItem>(typeAccountList.size());

		this.typeAccounts = new SelectItem[itens.size()];

		for (TypeAccount c : typeAccountList) {
			itens.add(new SelectItem(c.getId(), c.getDescription()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	private void loadTypeAccount() {
		this.typeAccounts = loadTypeAccounts();
	}

	private List<TypeAccount> loadTypeAccountList() {
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/typeaccount");
			typeAccount.setLocale(userMBean.getLoggedUser().getLanguage());
			request.body(MediaType.APPLICATION_JSON, typeAccount);
			ClientResponse<TypeAccount> response = request.put(TypeAccount.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			typeAccountList = response.getEntity(new GenericType<List<TypeAccount>>() {
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return typeAccountList;
	}

	public SelectItem[] loadDescriptions() {
		descriptionList = loadDescriptionList();

		List<SelectItem> itens = new ArrayList<SelectItem>(descriptionList.size());

		this.descriptions = new SelectItem[itens.size()];

		for (Description c : descriptionList) {
			itens.add(new SelectItem(c.getId(), c.getDescription()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	private List<Description> loadDescriptionList() {
		try {

			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/description");
			description.setUser(userMBean.getLoggedUser().getSuperUser() == null ? userMBean.getLoggedUser() : userMBean.getLoggedUser().getSuperUser());
			description.setTypeAccount(planningGroup.getTypeAccount());

			request.body(MediaType.APPLICATION_JSON, description);
			ClientResponse<Description> response = request.put(Description.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			descriptionList = response.getEntity(new GenericType<List<Description>>() {
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return descriptionList;
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

	public PlanningGroup getPlanningGroup() {
		return planningGroup;
	}

	public void setPlanningGroup(PlanningGroup planningGroup) {
		this.planningGroup = planningGroup;
	}

	public PlanningItem getPlanningItem() {
		return planningItem;
	}

	public void setPlanningItem(PlanningItem planningItem) {
		this.planningItem = planningItem;
	}

	public List<TypeAccount> getTypeAccountList() {
		return typeAccountList;
	}

	public void setTypeAccountList(List<TypeAccount> typeAccountList) {
		this.typeAccountList = typeAccountList;
	}

	public SelectItem[] getTypeAccounts() {
		return typeAccounts;
	}

	public void setTypeAccounts(SelectItem[] typeAccounts) {
		this.typeAccounts = typeAccounts;
	}

	public List<Description> getDescriptionList() {
		return descriptionList;
	}

	public void setDescriptionList(List<Description> descriptionList) {
		this.descriptionList = descriptionList;
	}

	public SelectItem[] getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(SelectItem[] descriptions) {
		this.descriptions = descriptions;
	}

	public TypeAccount getTypeAccount() {
		return typeAccount;
	}

	public void setTypeAccount(TypeAccount typeAccount) {
		this.typeAccount = typeAccount;
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
		this.description = description;
	}
}