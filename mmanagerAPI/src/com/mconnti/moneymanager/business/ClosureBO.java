package com.mconnti.moneymanager.business;

import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.Closure;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("closureBO")
public interface ClosureBO extends GenericBO<Closure>{
	
	public List<Closure> list() throws Exception;
	
	public MessageReturn save(final Closure closure) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public Closure getById(Long id);

	public Closure getClosure(Closure closure) throws ParseException;
}

