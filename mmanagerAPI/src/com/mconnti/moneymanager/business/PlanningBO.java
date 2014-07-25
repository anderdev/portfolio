package com.mconnti.moneymanager.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.Planning;
import com.mconnti.moneymanager.entity.PlanningGroup;
import com.mconnti.moneymanager.entity.PlanningItem;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("planningBO")
public interface PlanningBO extends GenericBO<Planning>{
	
	public List<Planning> list(final Planning plannig) throws Exception;
	
	public MessageReturn save(final Planning plannig) throws Exception;
	
	public MessageReturn saveGroup(final PlanningGroup planningGroup) throws Exception;
	
	public MessageReturn saveItem(final PlanningItem planningItem) throws Exception;
	
	public MessageReturn delete (final PlanningGroup planningGroup);
	
	public Planning getById(Long id);
	
	public Planning getSelected(final Planning plannig) throws Exception;
}

