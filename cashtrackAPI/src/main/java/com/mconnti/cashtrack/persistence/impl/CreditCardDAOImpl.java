package com.mconnti.cashtrack.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.cashtrack.entity.CreditCard;
import com.mconnti.cashtrack.persistence.CreditCardDAO;

@Repository("creditCardDAO")
public class CreditCardDAOImpl extends GenericDAOImpl<CreditCard> implements CreditCardDAO{
	

}
