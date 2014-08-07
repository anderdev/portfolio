package com.mconnti.cashtrack.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.cashtrack.entity.TypeAccount;
import com.mconnti.cashtrack.persistence.TypeAccountDAO;

@Repository("typeAccountDAO")
public class TypeAccountDAOImpl extends GenericDAOImpl<TypeAccount> implements TypeAccountDAO{
	

}
