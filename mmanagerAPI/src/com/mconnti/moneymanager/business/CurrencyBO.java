package com.mconnti.moneymanager.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.moneymanager.entity.Currency;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Service("currencyBO")
public interface CurrencyBO extends GenericBO<Currency>{
	
	public List<Currency> list(final Currency currency) throws Exception;
	
	public MessageReturn save(final Currency currency) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public Currency getById(Long id);
}

