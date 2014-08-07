package com.mconnti.cashtrack.business.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.mconnti.cashtrack.business.CurrencyBO;
import com.mconnti.cashtrack.entity.Currency;
import com.mconnti.cashtrack.entity.xml.MessageReturn;
import com.mconnti.cashtrack.utils.MessageFactory;

public class CurrencyBOImpl extends GenericBOImpl<Currency> implements CurrencyBO {

	@Override
	@Transactional
	public MessageReturn save(Currency currency) {
		MessageReturn libReturn = new MessageReturn();
		try {
			saveGeneric(currency);
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setCurrency(currency);
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null && currency.getId() == null) {
			libReturn.setMessage(MessageFactory.getMessage("lb_currency_saved", currency.getLocale()));
			libReturn.setCurrency(currency);
		} else if (libReturn.getMessage() == null && currency.getId() != null) {
			libReturn.setMessage(MessageFactory.getMessage("lb_currency_updated", currency.getLocale()));
			libReturn.setCurrency(currency);
		}
		return libReturn;
	}

	public List<Currency> list(final Currency currency) throws Exception {
		Map<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put(" where x.locale = ", "'"+currency.getLocale()+"'");
		return list(Currency.class, queryParams, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Currency currency = null;
		try {
			currency = findById(Currency.class, id);
			if (currency == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_currency_not_found", "en"));
			} else {
				String locale = currency.getLocale();
				remove(currency);
				libReturn.setMessage(MessageFactory.getMessage("lb_currency_deleted", locale));
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

	@Override
	public List<Currency> list() throws Exception {
		return list(Currency.class, null, null);
	}

}
