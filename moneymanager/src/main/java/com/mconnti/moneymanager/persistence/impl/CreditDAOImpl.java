package com.mconnti.moneymanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.Credit;
import com.mconnti.moneymanager.persistence.CreditDAO;

@Repository("creditDAO")
public class CreditDAOImpl extends GenericDAOImpl<Credit> implements CreditDAO{
	

}
