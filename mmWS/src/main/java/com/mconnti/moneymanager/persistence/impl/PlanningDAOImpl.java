package com.mconnti.moneymanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.Planning;
import com.mconnti.moneymanager.persistence.PlanningDAO;

@Repository("planningDAO")
public class PlanningDAOImpl extends GenericDAOImpl<Planning> implements PlanningDAO{
	

}
