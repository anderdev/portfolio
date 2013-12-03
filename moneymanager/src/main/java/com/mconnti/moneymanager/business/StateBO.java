package com.mconnti.moneymanager.business;

import java.util.List;

import javax.ws.rs.PathParam;

import com.mconnti.moneymanager.entity.State;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

public interface StateBO extends GenericBO<State>{
	
	public List<State> list() throws Exception;
	
	public MessageReturn save(final State state) throws Exception;
	
	public MessageReturn delete (@PathParam("id") Long id);
	
	public State getById(@PathParam("id") Long id);
}

