package com.mconnti.moneymanager.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.Role;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("roleBO")
public interface RoleBO extends GenericBO<Role>{
	
	public List<Role> list() throws Exception;
	
	public MessageReturn save(final Role role) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public Role getById(Long id);
}

