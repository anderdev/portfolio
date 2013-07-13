package com.mconnti.library.web.mbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import com.mconnti.library.entity.Author;
import com.mconnti.library.entity.Book;
import com.mconnti.library.entity.BookQueue;
import com.mconnti.library.entity.Category;
import com.mconnti.library.entity.User;
import com.mconnti.library.entity.xml.MessageReturn;
import com.mconnti.library.entity.xml.SearchObject;
import com.mconnti.library.util.FacesUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

@SessionScoped
@ManagedBean(name = "bookMBean")
public class BookMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// captura a sessão do contexto criado pelo JavaServer Faces
	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	
	@ManagedProperty(value = "#{userMBean}")  
	private UserMBean userMBean;

	private List<Book> bookList;

	private List<Author> authorList;

	public SelectItem[] authores;

	public SelectItem[] categories;

	private List<Category> categoryList;

	private Category category;

	private Author author;

	private Book book;

	private Book[] selectedBooks;

	private String queueTip;

	Client client = null;

	String host = null;

	public BookMBean() {
		client = Client.create();
		this.book = new Book();
		author = new Author();
		category = new Category();
		this.book.setAuthor(author);
		this.book.setCategory(category);

		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			String[] str = ((HttpServletRequest) request).getRequestURL().toString().split("library");
			host = str[0];
		}
		loadList();
	}

	private void loadList() {
		WebResource webResource = client.resource(host + "libraryWS/book");

		bookList = webResource.accept(MediaType.APPLICATION_JSON).get(new GenericType<List<Book>>() {
		});
	}

	public String list() {

		loadList();

		return "/common/index.xhtml?faces-redirect=true";
	}

	public void getQueueSize() {
		MessageReturn ret = new MessageReturn();
		try {

			WebResource webResource = client.resource(host + "libraryWS/bookQueue/listSize");
			SearchObject search = new SearchObject();
			search.getQueryParams().put("book", " = " + book.getId());
			search.setUser(userMBean.getLoggedUser());

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, search);

			if (response.getStatus() != 201 && response.getStatus() != 200) {
				ret.setMessage("Failed : HTTP error code : " + response.getStatus());
				throw new Exception(ret.getMessage());
			}

			ret = response.getEntity(MessageReturn.class);
			queueTip = ret.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(ret.getMessage());
		}
	}
	
	public void insertOnQueue(){
		MessageReturn ret = new MessageReturn();
		try {
			User user = userMBean.getLoggedUser();
			WebResource webResource = client.resource(host + "libraryWS/bookQueue");
			BookQueue bookQueue = new BookQueue();
			bookQueue.setBook(book);
			bookQueue.setUser(user);

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, bookQueue);

			if (response.getStatus() != 201 && response.getStatus() != 200) {
				ret.setMessage("Failed : HTTP error code : " + response.getStatus());
				throw new Exception(ret.getMessage());
			}

			ret = response.getEntity(MessageReturn.class);
			
			if (ret.getBookQueue() == null) {
				throw new Exception(ret.getMessage());
			} else {
				FacesUtil.showSuccessMessage(ret.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(ret.getMessage());
		}
	}

	public void rent(){
		MessageReturn ret = new MessageReturn();
		try {
			User user = userMBean.getLoggedUser();
			WebResource webResource = client.resource(host + "libraryWS/bookQueue");
			BookQueue bookQueue = new BookQueue();
			bookQueue.setBook(book);
			bookQueue.setUser(user);

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, bookQueue);

			if (response.getStatus() != 201 && response.getStatus() != 200) {
				ret.setMessage("Failed : HTTP error code : " + response.getStatus());
				throw new Exception(ret.getMessage());
			}

			ret = response.getEntity(MessageReturn.class);
			
			if (ret.getBookQueue() == null) {
				throw new Exception(ret.getMessage());
			} else {
				FacesUtil.showSuccessMessage(ret.getMessage());
			}
			loadList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(ret.getMessage());
		}
	}
	
	public void release(){
		MessageReturn ret = new MessageReturn();
		try {
			User user = userMBean.getLoggedUser();
			WebResource webResource = client.resource(host + "libraryWS/bookQueue/release");
			BookQueue bookQueue = new BookQueue();
			bookQueue.setBook(book);
			bookQueue.setUser(user);

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, bookQueue);

			if (response.getStatus() != 201 && response.getStatus() != 200) {
				ret.setMessage("Failed : HTTP error code : " + response.getStatus());
				throw new Exception(ret.getMessage());
			}

			ret = response.getEntity(MessageReturn.class);
			
			if (ret.getBook() == null) {
				throw new Exception(ret.getMessage());
			} else {
				FacesUtil.showSuccessMessage(ret.getMessage());
			}
			loadList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(ret.getMessage());
		}
	}
	
	public void newBook() {
		this.book = new Book();
		this.author = new Author();
		this.category = new Category();
	}

	public void edit() {
		this.author = book.getAuthor();
		this.category = book.getCategory();
	}
	
	public String cancel(){
		return "/common/index.xhtml?faces-redirect=true";
	}

	public void save() {
		MessageReturn ret = new MessageReturn();
		try {
			WebResource webResource = client.resource(host + "libraryWS/book");
			this.book.setAuthor(author);
			this.book.setCategory(category);
			this.book.setAvailable(true);
			this.book.setUser(userMBean.getLoggedUser());

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, book);

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
	}

	public void delete() {
		MessageReturn ret = new MessageReturn();
		try {
			for (Book book : selectedBooks) {
				WebResource webResource = client.resource(host + "libraryWS/book/" + book.getId());

				ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);

				if (response.getStatus() != 201 && response.getStatus() != 200) {
					ret.setMessage("Failed : HTTP error code : " + response.getStatus());
					throw new Exception(ret.getMessage());
				}

				ret = response.getEntity(MessageReturn.class);
			}

			if (selectedBooks.length > 1) {
				FacesUtil.showSuccessMessage(userMBean.getLoggedUser().getLanguage().equals("pt_BR") ? "Livros excluidos com sucesso!" : "Books successfully deleted!");
			} else {
				FacesUtil.showSuccessMessage(userMBean.getLoggedUser().getLanguage().equals("pt_BR") ? "Removido com sucesso!" : "Successfully deleted!");
			}
			loadList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.showAErrorMessage(ret.getMessage());
		}
	}

	public SelectItem[] getAuthores() {
		WebResource webResource = client.resource(host + "libraryWS/author");

		authorList = webResource.accept(MediaType.APPLICATION_JSON).get(new GenericType<List<Author>>() {
		});

		List<SelectItem> itens = new ArrayList<SelectItem>(authorList.size());

		this.authores = new SelectItem[itens.size()];

		for (Author a : authorList) {
			itens.add(new SelectItem(a.getId(), a.getName()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	public SelectItem[] getCategories() {

		WebResource webResource = client.resource(host + "libraryWS/category");

		categoryList = webResource.accept(MediaType.APPLICATION_JSON).get(new GenericType<List<Category>>() {
		});

		List<SelectItem> itens = new ArrayList<SelectItem>(categoryList.size());

		this.categories = new SelectItem[itens.size()];

		for (Category c : categoryList) {
			itens.add(new SelectItem(c.getId(), c.getType()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Book[] getSelectedBooks() {
		return selectedBooks;
	}

	public void setSelectedBooks(Book[] selectedBooks) {
		this.selectedBooks = selectedBooks;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public List<Author> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(List<Author> authorList) {
		this.authorList = authorList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public void setAuthores(SelectItem[] authores) {
		this.authores = authores;
	}

	public void setCategories(SelectItem[] categories) {
		this.categories = categories;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getQueueTip() {
		return queueTip;
	}

	public void setQueueTip(String queueTip) {
		this.queueTip = queueTip;
	}

	public UserMBean getUserMBean() {
		return userMBean;
	}

	public void setUserMBean(UserMBean userMBean) {
		this.userMBean = userMBean;
	}

}
