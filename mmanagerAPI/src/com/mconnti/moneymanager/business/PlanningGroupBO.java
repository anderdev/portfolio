package com.mconnti.moneymanager.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.PlanningGroup;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("planningGroupBO")
public interface PlanningGroupBO extends GenericBO<PlanningGroup>{
	
	public List<PlanningGroup> list() throws Exception;
	
	public MessageReturn save(final PlanningGroup plannnigGroup) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public PlanningGroup getById(Long id);
}

