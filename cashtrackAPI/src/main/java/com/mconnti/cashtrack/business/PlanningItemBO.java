package com.mconnti.cashtrack.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.cashtrack.entity.PlanningItem;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Service("planningItemBO")
public interface PlanningItemBO extends GenericBO<PlanningItem>{
	
	public List<PlanningItem> list() throws Exception;
	
	public MessageReturn save(final PlanningItem plannnigItem) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public PlanningItem getById(Long id);
}

