package com.mconnti.moneymanager.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.Credit;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("creditBO")
public interface CreditBO extends GenericBO<Credit>{
	
	public List<Credit> list() throws Exception;
	
	public MessageReturn save(final Credit credit) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public Credit getById(Long id);
}

