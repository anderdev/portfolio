package com.mconnti.moneymanager.persistence;

import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

public interface UserDAO  extends GenericDAO<User>{

	public MessageReturn getByUsername(String username) throws Exception;
	
}
