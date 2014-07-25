package com.mconnti.moneymanager.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("userBO")
public interface UserBO extends GenericBO<User>{
	
	public List<User> list() throws Exception;
	
	public MessageReturn save(final User user) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public User getById(Long id);

	public MessageReturn login(User user);

	public List<User> listByParameter(User user) throws Exception;
	
	public User getSuperUser(User user);
}

