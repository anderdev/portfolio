package com.mconnti.moneymanager.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.TypeClosure;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("typeClosureBO")
public interface TypeClosureBO extends GenericBO<TypeClosure>{
	
	public List<TypeClosure> list(final TypeClosure typeClosure) throws Exception;
	
	public MessageReturn save(final TypeClosure typeClosure) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public TypeClosure getById(Long id);
}

