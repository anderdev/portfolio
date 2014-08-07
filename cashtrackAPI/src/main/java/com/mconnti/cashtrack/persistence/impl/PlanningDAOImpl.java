package com.mconnti.cashtrack.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.cashtrack.entity.Planning;
import com.mconnti.cashtrack.persistence.PlanningDAO;

@Repository("planningDAO")
public class PlanningDAOImpl extends GenericDAOImpl<Planning> implements PlanningDAO{
	

}
