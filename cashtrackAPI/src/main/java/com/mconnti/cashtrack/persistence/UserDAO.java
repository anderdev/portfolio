package com.mconnti.cashtrack.persistence;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.mconnti.cashtrack.entity.User;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

public interface UserDAO  extends GenericDAO<User>, UserDetailsService{

	public MessageReturn getByUsername(String username) throws Exception;
	
}
