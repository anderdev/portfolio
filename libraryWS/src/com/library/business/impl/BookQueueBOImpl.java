package com.library.business.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.business.BookQueueBO;
import com.library.entity.Book;
import com.library.entity.BookQueue;
import com.library.entity.User;
import com.library.entity.xml.MessageReturn;
import com.library.entity.xml.SearchObject;
import com.library.persistence.BookDAO;
import com.library.persistence.UserDAO;

@Service("bookQueueBO")
@Path("/bookQueue")
public class BookQueueBOImpl extends GenericBOImpl<BookQueue> implements BookQueueBO {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private BookDAO bookDAO;

	@Override
	public List<BookQueue> list() throws Exception {
		return list(BookQueue.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn addOnQueue(BookQueue bookQueue) throws Exception {
		MessageReturn libReturn = new MessageReturn();
		try {
			Boolean addOnQueue = true;
			BookQueue queue = new BookQueue();
			Book book = bookDAO.findById(Book.class, bookQueue.getBook().getId());
			User user = userDAO.findById(User.class, bookQueue.getUser().getId());
			
			Map<String,String> queryParams = new HashMap<>();
			queryParams.put("book", " = "+book.getId());
			List<BookQueue> list = list(BookQueue.class, queryParams, null);
			int x = 0;
			for (BookQueue bk : list) {
				if(bk.getUser().getId().equals(user.getId()) && list.size() == 1){
					libReturn.setMessage(user.getLanguage().equals("pt_BR") ? "Você já esta locando este livro." : "You are already renting this book.");
					addOnQueue = false;
					break;
				}else if(bk.getUser().getId().equals(user.getId())){
					libReturn.setMessage(user.getLanguage().equals("pt_BR") ? "Você é o "+x+"º da fila de espera deste livro." : "You are the "+x+"º on the queue of this book.");
					addOnQueue = false;
					break;
				}
				x++;
			}
			
			if(addOnQueue){
				queue.setBook(book);
				queue.setUser(user);
				queue.setDateIn(new Date());
				queue.setRenting(false);
				saveGeneric(queue);
				libReturn.setBookQueue(queue);
				libReturn.setMessage(user.getLanguage().equals("pt_BR") ? "Adicionado na fila com sucesso!" : "Inserted on the queue successfully!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		BookQueue bookQueue = null;
		try {
			bookQueue = findById(BookQueue.class, id);
			remove(bookQueue);
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null) {
			libReturn.setMessage(bookQueue.getUser().getLanguage().equals("pt_BR") ? "Removido com sucesso!" : "Successfully deleted!");
		}
		return libReturn;
	}

	@Override
	public BookQueue getById(Long id) {
		try {
			return findById(BookQueue.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public MessageReturn renting(BookQueue bookQueue) {
		MessageReturn messageReturn = new MessageReturn();
		User user = null;
		try {
			Book book = bookDAO.findById(Book.class, bookQueue.getBook().getId());
			user = userDAO.findById(User.class, bookQueue.getUser().getId());

			Map<String, String> queryParams = new HashMap<>();
			queryParams.put("user", "= " + user.getId());
			queryParams.put("renting", "= true");

			List<BookQueue> list = list(BookQueue.class, queryParams, null);

			if (list.size() > 0) {
				messageReturn.setMessage(user.getLanguage().equals("pt_BR") ? "É permitida a locação de somente um livro por vez." : "It is allowed to rent just one book at a time!");
				throw new Exception(messageReturn.getMessage());
			}

			if (book.getAvailable()) {
				book.setAvailable(false);
				bookDAO.save(book);

				user.getBookList().add(book);
				userDAO.save(user);

				BookQueue queue = new BookQueue();
				queue.setBook(book);
				queue.setUser(user);
				queue.setDateIn(new Date());
				queue.setRenting(true);
				saveGeneric(queue);
				messageReturn.setBookQueue(bookQueue);
			} else {
				messageReturn.setMessage(user.getLanguage().equals("pt_BR") ? "O livro não está disponível." : "This book is not available!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			messageReturn.setMessage(e.getMessage());
		}

		if (messageReturn.getMessage() == null || messageReturn.getMessage().isEmpty()) {
			messageReturn.setMessage(user.getLanguage().equals("pt_BR") ? "Aluguel realizado com sucesso." : "Renting successfully!");
		}

		return messageReturn;
	}

	@Override
	@Transactional
	public MessageReturn waitList(SearchObject searchObject) {
		MessageReturn messageReturn = new MessageReturn();
		try {
			searchObject.getQueryParams().put("renting", " = false");
			List<BookQueue> list = list(BookQueue.class, searchObject.getQueryParams(), null);
			if (list.size() == 0) {
				messageReturn.setMessage(searchObject.getUser().getLanguage().equals("pt_BR") ? "Você é o próximo da fila, deseja aguardar?" : "You are the next on the queue, wants to wait?");
			} else {
				messageReturn.setMessage(searchObject.getUser().getLanguage().equals("pt_BR") ? "A fila de espera deste livro é de: " + list.size() + " usuário(s), deseja aguardar?" : "The queue for this book contains: "+list.size()+" users, wants to wait?");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageReturn;
	}

	@Override
	@Transactional
	public MessageReturn releaseBook(BookQueue bookQueue) {
		MessageReturn messageReturn = new MessageReturn();
		Book book = null;
		try {
			book = bookDAO.findById(Book.class, bookQueue.getBook().getId());

			Map<String, String> queryParams = new HashMap<>();
			queryParams.put("book", "= " + book.getId());
			queryParams.put("renting", "= true");
			bookQueue = findByParameter(BookQueue.class, queryParams);
			
			remove(bookQueue);

			queryParams.clear();
			queryParams.put("book", "= " + book.getId());
			queryParams.put("renting", "= false");

			List<BookQueue> queueList = list(BookQueue.class, queryParams, "id");

			if (queueList.isEmpty()) {
				book.setAvailable(true);
				bookDAO.save(book);
				messageReturn.setBook(book);
				messageReturn.setMessage(bookQueue.getUser().getLanguage().equals("pt_BR") ? book.getTitle() + ", devolução efetuada com sucesso!" :  book.getTitle() + ", returned successfully!");
			} else {
				for (BookQueue bQueue : queueList) {
					//testa para ver se usuário ja esta locando
					queryParams.clear();
					queryParams.put("user", "= " + bQueue.getUser().getId());
					queryParams.put("renting", "= true");
					
					List<BookQueue> list = list(BookQueue.class, queryParams, null);
					if(list.isEmpty()){
						bQueue.setRenting(true);
						saveGeneric(bQueue);
						
						bQueue.getUser().getBookList().add(book);
						userDAO.save(bQueue.getUser());
						messageReturn.setBook(book);
						messageReturn.setMessage(bQueue.getUser().getLanguage().equals("pt_BR") ? "Livro liberado para o próximo da fila que é o(a) "+bQueue.getUser().getName() : "Book available for the next on the queue, that is "+bQueue.getUser().getName());
						break;
					}
				}				
			}

		} catch (Exception e) {
			e.printStackTrace();
			messageReturn.setMessage(e.getMessage());
		}

		return messageReturn;
	}

}
