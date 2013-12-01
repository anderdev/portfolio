package com.mconnti.selfmanager.business.impl;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.selfmanager.business.CountryBO;
import com.mconnti.selfmanager.entity.Country;
import com.mconnti.selfmanager.entity.xml.MessageReturn;
import com.mconnti.selfmanager.persistence.CountryDAO;

@Service("countryBO")
@Path("/country")
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "country")
@Component
public class CountryBOImpl extends GenericBOImpl<Country> implements CountryBO, Serializable {

	private static final long serialVersionUID = -7181845834876407823L;
	
	@Autowired
	private CountryDAO countryDAO;
	
	@Override
	@Transactional
	public MessageReturn save(Country country) {
		MessageReturn libReturn = new MessageReturn();
		Country c = null;
		try {
			c = new Country();
			c.setId(country.getId());
			c.setName(country.getName());
			c.setLocale(country.getLocale());
			saveGeneric(c);
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setCountry(c);
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null && country.getId() == null) {
			libReturn.setMessage(country.getLocale().equals("pt_BR") ? "País cadastrado com sucesso!" : "Country registration successfully!");
			libReturn.setCountry(c);
		} else if (libReturn.getMessage() == null && country.getId() != null){
			libReturn.setMessage(country.getLocale().equals("pt_BR") ? "Páis alterado com sucesso!" : "Country updated successfully!");
			libReturn.setCountry(c);
		}
		return libReturn;
	}

	@Override
	@GET
	@Produces({ "application/json" })
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
