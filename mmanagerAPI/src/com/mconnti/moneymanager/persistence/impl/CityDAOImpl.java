package com.mconnti.moneymanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.City;
import com.mconnti.moneymanager.persistence.CityDAO;

@Repository("cityDAO")
public class CityDAOImpl extends GenericDAOImpl<City> implements CityDAO{
	

}
