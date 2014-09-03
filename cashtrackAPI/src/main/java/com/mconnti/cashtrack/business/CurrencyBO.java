package com.mconnti.cashtrack.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mconnti.cashtrack.entity.Currency;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Service("currencyBO")
public interface CurrencyBO extends GenericBO<Currency>{
	
	public List<Currency> list() throws Exception;
	
	public List<Currency> listByLocale(final String locale) throws Exception;
	
	public MessageReturn save(final Currency currency) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public Currency getById(Long id);
}

