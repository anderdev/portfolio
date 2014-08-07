package com.mconnti.cashtrack.persistence;

import com.mconnti.cashtrack.entity.User;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

public interface UserDAO  extends GenericDAO<User>{

	public MessageReturn getByUsername(String username) throws Exception;
	
}
