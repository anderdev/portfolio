package com.mconnti.moneymanager.business;

import java.util.List;

import javax.ws.rs.PathParam;

import com.mconnti.moneymanager.entity.City;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

public interface CityBO extends GenericBO<City>{
	
	public List<City> list() throws Exception;
	
	public MessageReturn save(final City city) throws Exception;
	
	public MessageReturn delete (@PathParam("id") Long id);
	
	public City getById(@PathParam("id") Long id);
}

