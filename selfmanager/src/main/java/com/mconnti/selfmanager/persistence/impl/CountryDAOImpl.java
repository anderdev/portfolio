package com.mconnti.selfmanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.selfmanager.entity.Country;
import com.mconnti.selfmanager.persistence.CountryDAO;

@Repository("countryDAO")
public class CountryDAOImpl extends GenericDAOImpl<Country> implements CountryDAO{
	

}
