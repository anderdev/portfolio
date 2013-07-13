package com.library.business.impl;

import java.util.List;

import javax.ws.rs.Path;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.business.CategoryBO;
import com.library.entity.Category;
import com.library.entity.xml.MessageReturn;

@Service("categoryBO")
@Path("/category")
public class CategoryBOImpl extends GenericBOImpl<Category> implements CategoryBO{

	@Override
	public List<Category> list() throws Exception {
		return list(Category.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn save(Category category) throws Exception {
		MessageReturn libReturn = new MessageReturn();
		try {
			Category cat = new Category();
			cat.setId(category.getId());
			cat.setType(category.getType());
			cat.setUser(category.getUser());
			saveGeneric(cat);
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null && category.getId() == null) {
			libReturn.setMessage(category.getUser().getLanguage().equals("pt_BR") ? "Categoria cadastrada com sucesso!" : "Category registration successfully!");
		} else if (libReturn.getMessage() == null && category.getId() != null){
			libReturn.setMessage(category.getUser().getLanguage().equals("pt_BR") ? "Categoria alterada com sucesso!" : "Category successfully updated!");
		}
		return libReturn;
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Category category = null;
		try {
			category = findById(Category.class, id);
			remove(category);
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public Category getById(Long id) {
		try {
			return findById(Category.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
