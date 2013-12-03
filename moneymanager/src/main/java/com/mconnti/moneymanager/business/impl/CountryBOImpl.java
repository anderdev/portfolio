package com.mconnti.moneymanager.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.CountryBO;
import com.mconnti.moneymanager.entity.Country;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.CountryDAO;

public class CountryBOImpl extends GenericBOImpl<Country> implements CountryBO {

	@Autowired
	private CountryDAO countryDAO;

	@Override
	@Transactional
	public MessageReturn save(Country country) {
		MessageReturn libReturn = new MessageReturn();
		Country c = null;
		try {
			String[] nameSplit = country.getName().split(";");
			if (nameSplit.length > 0) {
				for (int x = 0; x < nameSplit.length; x++) {
					c = new Country();
					c.setName(nameSplit[x]);
					c.setLocale(country.getLocale());
					saveGeneric(c);
				}
			} else {
				c = new Country();
				c.setId(country.getId());
				c.setName(country.getName());
				c.setLocale(country.getLocale());
				saveGeneric(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setCountry(c);
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null && country.getId() == null) {
			libReturn
					.setMessage(country.getLocale().equals("pt_BR") ? "País cadastrado com sucesso!"
							: "Country registration successfully!");
			libReturn.setCountry(c);
		} else if (libReturn.getMessage() == null && country.getId() != null) {
			libReturn
					.setMessage(country.getLocale().equals("pt_BR") ? "Páis alterado com sucesso!"
							: "Country updated successfully!");
			libReturn.setCountry(c);
		}
		return libReturn;
	}

	public List<Country> list() throws Exception {
		return list(Country.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Country country = null;
		try {
			country = findById(Country.class, id);
			remove(country);
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
