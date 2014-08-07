package com.mconnti.cashtrack.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.cashtrack.entity.Currency;
import com.mconnti.cashtrack.persistence.CurrencyDAO;

@Repository("currencyDAO")
public class CurrencyDAOImpl extends GenericDAOImpl<Currency> implements CurrencyDAO{
	

}
