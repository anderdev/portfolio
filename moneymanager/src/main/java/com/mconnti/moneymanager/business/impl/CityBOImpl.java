package com.mconnti.moneymanager.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.CityBO;
import com.mconnti.moneymanager.entity.City;
import com.mconnti.moneymanager.entity.State;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.CityDAO;
import com.mconnti.moneymanager.persistence.StateDAO;
import com.mconnti.moneymanager.utils.MessageFactory;

public class CityBOImpl extends GenericBOImpl<City> implements CityBO {

	@Autowired
	private CityDAO cityDAO;

	@Autowired
	private StateDAO stateDAO;
	
	private State getState(City city) {
		try {
			return stateDAO.findById(State.class, city.getState().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	@Transactional
	public MessageReturn save(City city) {
		MessageReturn libReturn = new MessageReturn();
		State state = getState(city);
		if (state != null){
			City c = null;
			try {
				String[] nameSplit = city.getName().split(";");
				if (nameSplit.length > 1) {
					for (int x = 0; x < nameSplit.length; x++) {
						c = new City();
						c.setName(nameSplit[x]);
						c.setState(state);
						saveGeneric(c);
					}
				} else {
					c = new City();
					c.setId(city.getId());
					c.setName(city.getName());
					saveGeneric(c);
				}
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setCity(c);
				libReturn.setMessage(e.getMessage());
			}
			if (libReturn.getMessage() == null && city.getId() == null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_city_saved", state.getCountry().getLocale()));
				libReturn.setCity(c);
			} else if (libReturn.getMessage() == null && city.getId() != null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_city_updated", state.getCountry().getLocale()));
				libReturn.setCity(c);
			}
		}
		
		return libReturn;
	}

	public List<City> list() throws Exception {
		return list(City.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		City city = null;
		try {
			city = findById(City.class, id);
			if (city == null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_city_not_found", "en"));
			} else {
				remove(city);
				libReturn.setMessage( MessageFactory.getMessage("lb_city_deleted", city.getState().getCountry().getLocale()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public City getById(Long id) {
		try {
			return findById(City.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
