package com.mconnti.moneymanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.Register;
import com.mconnti.moneymanager.persistence.RegisterDAO;

@Repository("registerDAO")
public class RegisterDAOImpl extends GenericDAOImpl<Register> implements RegisterDAO{
	

}
