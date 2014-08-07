package com.mconnti.cashtrack.business;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mconnti.cashtrack.entity.Description;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Service("descriptionBO")
public interface DescriptionBO extends GenericBO<Description>{
	
	public List<Description> list() throws Exception;
	
	public List<Description> listByParameter(Description description) throws Exception;
	
	public MessageReturn save(final Description description) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public Description getById(Long id);
	
	public Description getByDescription(Map<String, String> request) throws Exception;
	
}

