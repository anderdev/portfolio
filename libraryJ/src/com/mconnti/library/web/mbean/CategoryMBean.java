package com.mconnti.library.web.mbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import com.mconnti.library.entity.Category;
import com.mconnti.library.entity.xml.MessageReturn;
import com.mconnti.library.util.FacesUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

@SessionScoped
@ManagedBean(name = "categoryMBean")
public class CategoryMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// captura a sessão do contexto criado pelo JavaServer Faces
	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	
	@ManagedProperty(value = "#{userMBean}")  
	private UserMBean userMBean;
	
	public UserMBean getUserMBean() {
		return userMBean;
	}

	public void setUserMBean(UserMBean userMBean) {
		this.userMBean = userMBean;
	}
	
	private List<Category> categoryList;

	private Category category;

	private Category[] selectedCategorys;

	Client client = null;

	String host = null;

	public CategoryMBean() {
		client = Client.create();
		this.category = new Category();
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			String[] str = ((HttpServletRequest) request).getRequestURL().toString().split("library");
			host = str[0];
		}
	}

	private void loadList(){
		WebResource webResource = client.resource(host + "libraryWS/category");
		
		categoryList = webResource.accept(MediaType.APPLICATION_JSON).get(new GenericType<List<Category>>(){});
	}

	public String list() {
		
		loadList();
		
		return "/common/listCategory.xhtml?faces-redirect=true";
	}
	
	public String cancel(){
		return "/common/listCategory.xhtml?faces-redirect=true";
	}

	public void newCategory() {
		this.category = new Category();
	}

	public void edit() {
	}

	public String save() {
		MessageReturn ret = new MessageReturn();
		try {
			WebResource webResource = client.resource(host + "libraryWS/category");
			category.setUser(userMBean.getLoggedUser());
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, category);

			if (response.getStatus() != 201 && response.getStatus() != 200) {
				ret.setMessage("Failed : HTTP error code : " + response.getStatus());
				throw new Exception(ret.getMessage());
			}

			ret = response.getEntity(MessageReturn.class);

			FacesUtil.showSuccessMessage(ret.getMessage());
			loadList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(ret.getMessage());
		}
		return "/common/listCategory.xhtml?faces-redirect=true";
	}

	public void delete() {
		MessageReturn ret = new MessageReturn();
		try {
			for (Category category : selectedCategorys) {
				WebResource webResource = client.resource(host + "libraryWS/category/" + category.getId());

				ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);

				if (response.getStatus() != 201 && response.getStatus() != 200) {
					ret.setMessage("Failed : HTTP error code : " + response.getStatus());
					throw new Exception(ret.getMessage());
				}

				ret = response.getEntity(MessageReturn.class);
			}

			if (selectedCategorys.length > 1) {
				FacesUtil.showSuccessMessage(userMBean.getLoggedUser().getLanguage().equals("pt_BR") ? "Categorias excluidos com sucesso!" : "Categories successfully deleted!");
			} else {
				FacesUtil.showSuccessMessage(userMBean.getLoggedUser().getLanguage().equals("pt_BR") ? "Removido com sucesso!" : "Successfully deleted!");
			}
			loadList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(ret.getMessage());
		}
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category[] getSelectedCategorys() {
		return selectedCategorys;
	}

	public void setSelectedCategorys(Category[] selectedCategorys) {
		this.selectedCategorys = selectedCategorys;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

}
