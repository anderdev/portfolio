package com.mconnti.moneymanager.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("descriptionBO")
public interface DescriptionBO extends GenericBO<Description>{
	
	public List<Description> list() throws Exception;
	
	public List<Description> listByParameter(Description description) throws Exception;
	
	public MessageReturn save(final Description description) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public Description getById(Long id);
	
}

