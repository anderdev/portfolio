package com.mconnti.moneymanager.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.CreditCard;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("creditCardBO")
public interface CreditCardBO extends GenericBO<CreditCard>{
	
	public List<CreditCard> list() throws Exception;
	
	public MessageReturn save(final CreditCard creditCard) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public CreditCard getById(Long id);

	public List<CreditCard> listByParameter(CreditCard creditCard) throws Exception;
}

