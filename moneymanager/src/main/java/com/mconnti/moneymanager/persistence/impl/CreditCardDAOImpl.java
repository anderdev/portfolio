package com.mconnti.moneymanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.CreditCard;
import com.mconnti.moneymanager.persistence.CreditCardDAO;

@Repository("creditCardDAO")
public class CreditCardDAOImpl extends GenericDAOImpl<CreditCard> implements CreditCardDAO{
	

}
