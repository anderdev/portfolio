package com.mconnti.cashtrack.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.cashtrack.entity.Config;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Service("configBO")
public interface ConfigBO extends GenericBO<Config>{
	
	public List<Config> list() throws Exception;
	
	public MessageReturn save(final Config config) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public Config getById(Long id);
}

