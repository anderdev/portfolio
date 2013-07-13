package com.library.persistence.impl;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.library.entity.User;
import com.library.entity.xml.MessageReturn;
import com.library.persistence.UserDAO;

@Repository("userDAO")
public class UserDAOImpl extends GenericDAOImpl<User> implements UserDAO{
	
	@Override
	public MessageReturn getByEmail(final String email) throws Exception{
		MessageReturn messageReturn = new MessageReturn();
		User user = null;
		try {
			Query queryView = em.createNamedQuery("user.findByEmail");
			queryView.setParameter("email", email);
			user = (User) queryView.getSingleResult();
			messageReturn.setUser(user);			
		} catch (NoResultException nre) {
			messageReturn.setUser(user);
		} catch (NonUniqueResultException nure) {
			throw new Exception("Multiplos usuários encontrados com o mesmo email!");
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		return messageReturn;
	}

}
