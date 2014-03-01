package com.mconnti.moneymanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.Currency;
import com.mconnti.moneymanager.persistence.CurrencyDAO;

@Repository("currencyDAO")
public class CurrencyDAOImpl extends GenericDAOImpl<Currency> implements CurrencyDAO{
	

}
