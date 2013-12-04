package com.mconnti.moneymanager.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.State;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("stateBO")
public interface StateBO extends GenericBO<State>{
	
	public List<State> list() throws Exception;
	
	public MessageReturn save(final State state) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public State getById(Long id);
}

