package com.mconnti.cashtrack.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.cashtrack.entity.Role;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Service("roleBO")
public interface RoleBO extends GenericBO<Role>{
	
	public List<Role> list() throws Exception;
	
	public MessageReturn save(final Role role) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public Role getById(Long id);
}

