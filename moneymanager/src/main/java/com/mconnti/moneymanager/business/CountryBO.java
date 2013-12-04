package com.mconnti.moneymanager.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.Country;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("countryBO")
public interface CountryBO extends GenericBO<Country>{
	
	public List<Country> list() throws Exception;
	
	public MessageReturn save(final Country country) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public Country getById(Long id);
}

