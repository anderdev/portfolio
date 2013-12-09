package com.mconnti.moneymanager.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.City;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("cityBO")
public interface CityBO extends GenericBO<City>{
	
	public List<City> list() throws Exception;
	
	public MessageReturn save(final City city) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public City getById(Long id);
}

