package com.mconnti.cashtrack.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.cashtrack.entity.CreditCard;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Service("creditCardBO")
public interface CreditCardBO extends GenericBO<CreditCard>{
	
	public List<CreditCard> list() throws Exception;
	
	public MessageReturn save(final CreditCard creditCard) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public CreditCard getById(Long id);

	public List<CreditCard> listByParameter(String userId) throws Exception;
}

