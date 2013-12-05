package com.mconnti.moneymanager.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.Account;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("accountBO")
public interface AccountBO extends GenericBO<Account>{
	
	public List<Account> list() throws Exception;
	
	public MessageReturn save(final Account account) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public Account getById(Long id);
}

