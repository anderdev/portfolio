package com.mconnti.moneymanager.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.TypeAccount;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("accountBO")
public interface TypeAccountBO extends GenericBO<TypeAccount>{
	
	public List<TypeAccount> list() throws Exception;
	
	public MessageReturn save(final TypeAccount account) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public TypeAccount getById(Long id);
}

