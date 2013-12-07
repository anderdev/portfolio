package com.mconnti.moneymanager.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.Debit;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("debitBO")
public interface DebitBO extends GenericBO<Debit>{
	
	public List<Debit> list() throws Exception;
	
	public MessageReturn save(final Debit debit) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public Debit getById(Long id);
}

