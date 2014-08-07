package com.mconnti.cashtrack.persistence;

import com.mconnti.cashtrack.entity.PlanningItem;
import com.mconnti.cashtrack.entity.Register;

public interface PlanningItemDAO  extends GenericDAO<PlanningItem>{
	
	public Register delete(PlanningItem planningItem) throws Exception;
	
}
