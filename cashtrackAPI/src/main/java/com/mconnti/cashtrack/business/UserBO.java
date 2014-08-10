package com.mconnti.cashtrack.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.cashtrack.entity.User;
import com.mconnti.cashtrack.entity.xml.MessageReturn;
import com.mconnti.cashtrack.entity.xml.TokenTransfer;

@Service("userBO")
public interface UserBO extends GenericBO<User>{
	
	public List<User> list() throws Exception;
	
	public MessageReturn save(final User user) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public User getById(Long id);

	public TokenTransfer login(String username, String password);

	public List<User> listByParameter(User user) throws Exception;
	
	public User getSuperUser(User user);
}

