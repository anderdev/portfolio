package com.mconnti.cashtrack.persistence.impl;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.mconnti.cashtrack.entity.User;
import com.mconnti.cashtrack.entity.xml.MessageReturn;
import com.mconnti.cashtrack.persistence.UserDAO;

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
			messageReturn.setMessage("Multiples users found using same username, get in contact with support!");
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		return messageReturn;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		try {
			user = this.getByUsername(username).getUser();
			if (null == user) {
				throw new UsernameNotFoundException("The user with name " + username + " was not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (UserDetails) user;
	}
}
