package com.mconnti.moneymanager.web.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.primefaces.event.TabChangeEvent;

import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.Planning;
import com.mconnti.moneymanager.entity.PlanningGroup;
import com.mconnti.moneymanager.entity.PlanningItem;
import com.mconnti.moneymanager.entity.TypeAccount;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.util.FacesUtil;
import com.mconnti.moneymanager.util.MessageFactory;

@SessionScoped
@ManagedBean(name = "planningMBean")
public class PlanningMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{userMBean}")
	private UserMBean userMBean;

	private List<Planning> planningList;

	private List<PlanningGroup> planningGroupList;

	private Planning planning;

	private TypeAccount typeAccount;

	private Description description;

	private PlanningGroup planningGroup;

	private PlanningGroup selectedPlanningGroup;

	private PlanningItem planningItem;

	private PlanningItem selectedPlanningItem;

	private Planning selectedPlanning;

	private String host = null;

	private Boolean showForm = false;

	private Boolean showButtonItem = false;

	private List<TypeAccount> typeAccountList;

	private List<Description> groupList;

	private SelectItem[] typeAccounts;

	private SelectItem[] groups;

	private Integer activedIndex;

	private String selectedMonth;
	
	private List<String> months;

	public PlanningMBean() {
		createPlanning();
		this.selectedPlanningGroup = new PlanningGroup();
		this.selectedPlanningGroup.setDescription(new Description());
		this.selectedPlanningGroup.setTypeAccount(new TypeAccount());
		this.planningItem = new PlanningItem();
		this.selectedPlanningItem = new PlanningItem();
		this.typeAccount = new TypeAccount();
		this.description = new Description();
		String[] monthArray = new String[] {"JAN","FEV","MAR","APR","MAY","JUN","JUL","AUG","SET","OUT","NOV","DEC"};
		this.months = Arrays.asList(monthArray);

		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			String[] str = ((HttpServletRequest) request).getRequestURL().toString().split("mmanager");
			host = str[0];
		}
	}

	private void createPlanning() {
		this.planning = new Planning();
		this.planningGroup = new PlanningGroup();
		this.planningGroup.setDescription(new Description());
		this.planningGroup.setTypeAccount(new TypeAccount());
	}

	private User superUser() {
		return userMBean.getLoggedUser();
	}

	private List<Planning> loadList() {
		try {
			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/planning/list");

			planning.setUser(superUser());
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
		List<Planning> planList = loadList();
		for (int x = 0; x < planList.size(); x++) {
			Planning plan = planList.get(x);
			if (plan.getSelected()) {
				activedIndex = x;
				selectedPlanning = plan;
			}
		}
		showButtonItem = planList.isEmpty() ? false : true;
		return "/common/formPlanning.xhtml?faces-redirect=true";
	}

	public void newPlanning() {
		showForm = true;
		this.planning = new Planning();
	}

	public void newPlanningGroup() {
		planningGroup.setPlanning(selectedPlanning);
		planningGroup.setUser(superUser());
		loadTypeAccount();
	}

	public void newPlanningItem() {
		setCurrentMonth();
	}

	private void setCurrentMonth() {
		switch (selectedPlanningItem.getMonth()) {
		case 1:
			selectedMonth = MessageFactory.getMessage("lb_month_january", superUser().getLanguage(), null);
			break;
		case 2:
			selectedMonth = MessageFactory.getMessage("lb_month_february", superUser().getLanguage(), null);
			break;
		case 3:
			selectedMonth = MessageFactory.getMessage("lb_month_march", superUser().getLanguage(), null);
			break;
		case 4:
			selectedMonth = MessageFactory.getMessage("lb_month_april", superUser().getLanguage(), null);
			break;
		case 5:
			selectedMonth = MessageFactory.getMessage("lb_month_may", superUser().getLanguage(), null);
			break;
		case 6:
			selectedMonth = MessageFactory.getMessage("lb_month_june", superUser().getLanguage(), null);
			break;
		case 7:
			selectedMonth = MessageFactory.getMessage("lb_month_july", superUser().getLanguage(), null);
			break;
		case 8:
			selectedMonth = MessageFactory.getMessage("lb_month_august", superUser().getLanguage(), null);
			break;
		case 9:
			selectedMonth = MessageFactory.getMessage("lb_month_september", superUser().getLanguage(), null);
			break;
		case 10:
			selectedMonth = MessageFactory.getMessage("lb_month_october", superUser().getLanguage(), null);
			break;
		case 11:
			selectedMonth = MessageFactory.getMessage("lb_month_november", superUser().getLanguage(), null);
			break;
		case 12:
			selectedMonth = MessageFactory.getMessage("lb_month_december", superUser().getLanguage(), null);
			break;
		}
	}

	public void cancelTab() {
		showForm = false;
	}

	public void edit() {
	}

	private Boolean save(Object obj, String url, Boolean showMessage) {
		MessageReturn ret = new MessageReturn();

		try {
			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/" + url);

			request.body(MediaType.APPLICATION_JSON, obj);

			ClientResponse<Object> response = request.post(Object.class);

			ret = response.getEntity(MessageReturn.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			if (showMessage) {
				if (ret.getPlanning() == null && ret.getPlanningGroup() == null && ret.getPlanningItem() == null) {
					throw new Exception(ret.getMessage());
				} else {
					FacesUtil.showSuccessMessage(ret.getMessage());
				}
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
		return true;
	}

	public void saveTab() {
		planning.setDate(new GregorianCalendar());
		planning.setUser(superUser());
		planning.setSelected(true);

		if (save(planning, "planning", true)) {
			createPlanning();
			list();
			showForm = false;
		}
	}

	public void onChange(TabChangeEvent event) {
		selectedPlanning.setSelected(false);
		save(selectedPlanning, "planning", false);

		Planning planning = planningList.get(activedIndex);
		planning.setSelected(true);
		save(planning, "planning", false);
	}

	public void delete() {
		MessageReturn ret = new MessageReturn();
		try {
			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/planninggroup");

			selectedPlanning = planningList.get(activedIndex);

			// selectedPlanningGroup.setPlanning(selectedPlanning);

			request.body(MediaType.APPLICATION_JSON, selectedPlanningGroup);

			ClientResponse<PlanningGroup> response = request.delete(PlanningGroup.class);

			ret = response.getEntity(MessageReturn.class);

			FacesUtil.showSuccessMessage(MessageFactory.getMessage("lb_deleted_successfully", superUser().getLanguage(), null));
			loadList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(ret.getMessage());
		}

	}

	public void saveGroup() {
		save(planningGroup, "planninggroup", true);
		groups = null;
		createPlanning();
	}

	public void saveItem() {
		save(selectedPlanningItem, "planningitem", true);
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
			typeAccount.setLocale(superUser().getLanguage());
			typeAccount.setShowType(true);
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

	public void loadGroups() {
		this.groups = siGroups();
	}

	public SelectItem[] siGroups() {

		groupList = loadDescriptionList(MessageFactory.getMessage("lb_group_", superUser().getLanguage(), null));

		List<SelectItem> itens = new ArrayList<SelectItem>(groupList.size());

		this.groups = new SelectItem[itens.size()];

		for (Description d : groupList) {
			itens.add(new SelectItem(d.getId(), d.getDescription()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	private List<Description> loadDescriptionList(String typeAccountDescription) {
		try {
			ClientRequest request = new ClientRequest(host + "mmanagerAPI/rest/description");
			TypeAccount typeAccount = new TypeAccount();
			typeAccount.setDescription(typeAccountDescription);

			description.setUser(superUser());
			description.setTypeAccount(typeAccount);

			request.body(MediaType.APPLICATION_JSON, description);
			ClientResponse<Description> response = request.put(Description.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			groupList = response.getEntity(new GenericType<List<Description>>() {
			});
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return groupList;
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

	public Integer getActivedIndex() {
		return activedIndex;
	}

	public void setActivedIndex(Integer activedIndex) {
		this.activedIndex = activedIndex;
	}

	public PlanningGroup getSelectedPlanningGroup() {
		return selectedPlanningGroup;
	}

	public void setSelectedPlanningGroup(PlanningGroup selectedPlanningGroup) {
		this.selectedPlanningGroup = selectedPlanningGroup;
	}

	public List<PlanningGroup> getPlanningGroupList() {
		return planningGroupList;
	}

	public void setPlanningGroupList(List<PlanningGroup> planningGroupList) {
		this.planningGroupList = planningGroupList;
	}

	public PlanningItem getSelectedPlanningItem() {
		return selectedPlanningItem;
	}

	public void setSelectedPlanningItem(PlanningItem selectedPlanningItem) {
		this.selectedPlanningItem = selectedPlanningItem;
	}

	public String getSelectedMonth() {
		return selectedMonth;
	}

	public void setSelectedMonth(String selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public Planning getSelectedPlanning() {
		return selectedPlanning;
	}

	public void setSelectedPlanning(Planning selectedPlanning) {
		this.selectedPlanning = selectedPlanning;
	}

	public List<Description> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Description> groupList) {
		this.groupList = groupList;
	}

	public SelectItem[] getGroups() {
		return groups;
	}

	public void setGroups(SelectItem[] groups) {
		this.groups = groups;
	}

	public Boolean getShowButtonItem() {
		return showButtonItem;
	}

	public void setShowButtonItem(Boolean showButtonItem) {
		this.showButtonItem = showButtonItem;
	}

	public List<String> getMonths() {
		return months;
	}

	public void setMonths(List<String> months) {
		this.months = months;
	}
}