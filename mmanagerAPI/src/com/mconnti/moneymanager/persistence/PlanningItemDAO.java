package com.mconnti.moneymanager.persistence;

import com.mconnti.moneymanager.entity.PlanningItem;
import com.mconnti.moneymanager.entity.Register;

public interface PlanningItemDAO  extends GenericDAO<PlanningItem>{
	
	public Register delete(PlanningItem planningItem) throws Exception;
	
}
