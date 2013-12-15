package com.mconnti.moneymanager.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.Planning;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("planningBO")
public interface PlanningBO extends GenericBO<Planning>{
	
	public List<Planning> list() throws Exception;
	
	public MessageReturn save(final Planning plannnig) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public Planning getById(Long id);
}
