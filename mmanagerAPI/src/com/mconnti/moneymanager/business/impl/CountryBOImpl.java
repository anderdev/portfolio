package com.mconnti.moneymanager.business.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.CountryBO;
import com.mconnti.moneymanager.entity.Country;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.utils.MessageFactory;

public class CountryBOImpl extends GenericBOImpl<Country> implements CountryBO {

	@Override
	@Transactional
	public MessageReturn save(Country country) {
		MessageReturn libReturn = new MessageReturn();
		Country c = null;
		try {
			String[] nameSplit = country.getName().split(";");
			if (nameSplit.length > 1) {
				for (int x = 0; x < nameSplit.length; x++) {
					c = new Country();
					c.setName(nameSplit[x]);
					c.setLocale(country.getLocale());
					saveGeneric(c);
				}
			} else {
				saveGeneric(country);
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setCountry(country);
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null && country.getId() == null) {
			libReturn.setMessage( MessageFactory.getMessage("lb_country_saved", country.getLocale()));
			libReturn.setCountry(country);
		} else if (libReturn.getMessage() == null && country.getId() != null) {
			libReturn.setMessage( MessageFactory.getMessage("lb_country_updated", country.getLocale()));
			libReturn.setCountry(country);
		}
		return libReturn;
	}

	@Override
	@Transactional
	public List<Country> list() throws Exception {
		List<Country> list = list(Country.class, null, null);
		return list;
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Country country = null;
		try {
			country = findById(Country.class, id);
			if (country == null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_country_not_found", "en"));
			} else {
				String locale = country.getLocale();
				remove(country);
				libReturn.setMessage( MessageFactory.getMessage("lb_country_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public Country getById(Long id) {
		try {
			return findById(Country.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
