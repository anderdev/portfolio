package com.mconnti.moneymanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.State;
import com.mconnti.moneymanager.persistence.StateDAO;

@Repository("stateDAO")
public class StateDAOImpl extends GenericDAOImpl<State> implements StateDAO{
	

}
