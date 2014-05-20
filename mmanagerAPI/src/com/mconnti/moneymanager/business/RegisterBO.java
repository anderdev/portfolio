package com.mconnti.moneymanager.business;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.Register;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("registerBO")
public interface RegisterBO extends GenericBO<Register>{
	
	public List<Register> list(Register register) throws Exception;
	
	public List<Register> listByParameter(Register register) throws Exception;
	
	public MessageReturn save(final Register debit) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public Register getByDescription(Map<String, String> request);
	
}

