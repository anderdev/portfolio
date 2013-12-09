package com.mconnti.moneymanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.Debit;
import com.mconnti.moneymanager.persistence.DebitDAO;

@Repository("debitDAO")
public class DebitDAOImpl extends GenericDAOImpl<Debit> implements DebitDAO{
	

}
