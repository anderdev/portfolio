package com.library.business.impl;

import java.util.List;

import javax.ws.rs.Path;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.business.AuthorBO;
import com.library.entity.Author;
import com.library.entity.xml.MessageReturn;

@Path("/author")
@Service("authorBO")
public class AuthorBOImpl extends GenericBOImpl<Author> implements AuthorBO{

	@Override
	public List<Author> list() throws Exception {
		return list(Author.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn save(Author author) throws Exception {
		MessageReturn libReturn = new MessageReturn();
		try {
			Author auth = new Author();
			auth.setId(author.getId());
			auth.setName(author.getName());
			auth.setUser(author.getUser());
			saveGeneric(auth);
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null) {
			libReturn.setMessage(author.getUser().getLanguage().equals("pt_BR") ? "Autor Cadastrado com sucesso!" : "Author registration successfully!");
		}
		return libReturn;
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Author author = null;
		try {
			author = findById(Author.class, id);
			remove(author);
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public Author getById(Long id) {
		try {
			return findById(Author.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
