package com.mconnti.moneymanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.TypeAccount;
import com.mconnti.moneymanager.persistence.TypeAccountDAO;

@Repository("accountDAO")
public class TypeAccountDAOImpl extends GenericDAOImpl<TypeAccount> implements TypeAccountDAO{
	

}
