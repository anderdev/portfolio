package com.mconnti.moneymanager.persistence.impl;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.UserDAO;

@Repository("userDAO")
public class UserDAOImpl extends GenericDAOImpl<User> implements UserDAO{
	
	@Override
	public MessageReturn getByUsername(final String username) throws Exception{
		MessageReturn messageReturn = new MessageReturn();
		User user = null;
		try {
			Query queryView = em.createNamedQuery("user.findByUsername");
			queryView.setParameter("username", username);
			user = (User) queryView.getSingleResult();
			messageReturn.setUser(user);			
		} catch (NoResultException nre) {
			messageReturn.setUser(user);
		} catch (NonUniqueResultException nure) {
			throw new Exception("Multiples users found using same username, get in contact with support!");
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		return messageReturn;
	}
}
