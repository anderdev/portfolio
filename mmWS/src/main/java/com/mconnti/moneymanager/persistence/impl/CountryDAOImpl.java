package com.mconnti.moneymanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.Country;
import com.mconnti.moneymanager.persistence.CountryDAO;

@Repository("countryDAO")
public class CountryDAOImpl extends GenericDAOImpl<Country> implements CountryDAO{
	

}
