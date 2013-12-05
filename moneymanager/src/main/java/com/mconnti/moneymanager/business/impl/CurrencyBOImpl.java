package com.mconnti.moneymanager.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.CurrencyBO;
import com.mconnti.moneymanager.entity.Currency;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.CurrencyDAO;
import com.mconnti.moneymanager.utils.MessageFactory;

public class CurrencyBOImpl extends GenericBOImpl<Currency> implements CurrencyBO {

	@Autowired
	private CurrencyDAO currencyDAO;

	@Override
	@Transactional
	public MessageReturn save(Currency currency) {
		MessageReturn libReturn = new MessageReturn();
		Currency c = null;
		try {
			String[] nameSplit = currency.getName().split(";");
			if (nameSplit.length > 1) {
				for (int x = 0; x < nameSplit.length; x++) {
					c = new Currency();
					c.setName(nameSplit[x]);
					c.setAcronym(currency.getAcronym());
					c.setLocale(currency.getLocale());
					saveGeneric(c);
				}
			} else {
				c = new Currency();
				c.setId(currency.getId());
				c.setName(currency.getName());
				c.setAcronym(currency.getAcronym());
				c.setLocale(currency.getLocale());
				saveGeneric(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setCurrency(c);
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null && currency.getId() == null) {
			libReturn.setMessage( MessageFactory.getMessage("lb_currency_saved", currency.getLocale()));
			libReturn.setCurrency(c);
		} else if (libReturn.getMessage() == null && currency.getId() != null) {
			libReturn.setMessage( MessageFactory.getMessage("lb_currency_updated", currency.getLocale()));
			libReturn.setCurrency(c);
		}
		return libReturn;
	}

	public List<Currency> list() throws Exception {
		return list(Currency.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Currency currency = null;
		try {
			currency = findById(Currency.class, id);
			if (currency == null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_currency_not_found", "en"));
			} else {
				String locale = currency.getLocale();
				remove(currency);
				libReturn.setMessage( MessageFactory.getMessage("lb_currency_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public Currency getById(Long id) {
		try {
			return findById(Currency.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
