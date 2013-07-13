package com.library.business.impl;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.business.UserBO;
import com.library.entity.User;
import com.library.entity.xml.MessageReturn;
import com.library.persistence.UserDAO;

@Path("/user")
@Service("userBO")
public class UserBOImpl extends GenericBOImpl<User> implements UserBO, Serializable {

	private static final long serialVersionUID = -7181845834876407823L;
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	@Transactional
	public MessageReturn save(User user) {
		MessageReturn libReturn = new MessageReturn();
		User u = null;
		try {
			u = new User();
			u.setId(user.getId());
			u.setAddress(user.getAddress());
			u.setName(user.getName());
			u.setAdmin(user.getAdmin());
			u.setBirthDate(user.getBirthDate());			
			u.setEmail(user.getEmail());
			u.setLanguage(user.getLanguage());
			if(user.getPassword() != null || !user.getPassword().isEmpty()){
				u.setPassword(user.getPassword());
			}
			saveGeneric(u);
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setUser(u);
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null && user.getId() == null) {
			libReturn.setMessage(user.getLanguage().equals("pt_BR") ? "Usuário Cadastrado com sucesso!" : "User registration successfully!");
			libReturn.setUser(u);
		} else if (libReturn.getMessage() == null && user.getId() != null){
			libReturn.setMessage(user.getLanguage().equals("pt_BR") ? "Usuário alterado com sucesso!" : "User updated successfully!");
			libReturn.setUser(u);
		}
		return libReturn;
	}

	@Override
	public List<User> list() throws Exception {
		return list(User.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		User user = null;
		try {
			user = findById(User.class, id);
			remove(user);
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public User getById(Long id) {
		try {
			return findById(User.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public MessageReturn login(User user) {
		MessageReturn messageReturn = null;
		try {
			messageReturn = userDAO.getByEmail(user.getEmail());
			if(messageReturn.getUser() == null){
				messageReturn.setUser(null);
				messageReturn.setMessage(messageReturn.getUser().getLanguage().equals("pt_BR") ? "Usuário não encontrado!" : "User not exists!");
			}else if (!messageReturn.getUser().getPassword().equals(user.getPassword())) {
				messageReturn.setUser(null);
				messageReturn.setMessage(messageReturn.getUser().getLanguage().equals("pt_BR") ? "Senha informada esta incorreta!" : "Informed password is no correct!");
			}else{
				messageReturn.setMessage(messageReturn.getUser().getLanguage().equals("pt_BR") ? "Login realizado com sucesso!" : "Successfully logged!");
			}
		} catch (Exception e) {
			messageReturn = new MessageReturn();
			messageReturn.setMessage(e.getMessage());
		}
		return messageReturn;
	}
}
