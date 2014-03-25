package com.mconnti.moneymanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.PlanningItem;
import com.mconnti.moneymanager.persistence.PlanningItemDAO;

@Repository("planningItemDAO")
public class PlanningItemDAOImpl extends GenericDAOImpl<PlanningItem> implements PlanningItemDAO{
	

}
