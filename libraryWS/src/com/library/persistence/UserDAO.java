package com.library.persistence;

import com.library.entity.User;
import com.library.entity.xml.MessageReturn;

public interface UserDAO  extends GenericDAO<User>{
	
	public MessageReturn getByEmail(final String email) throws Exception;

}
