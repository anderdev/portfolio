package com.mconnti.moneymanager.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.StateBO;
import com.mconnti.moneymanager.entity.Country;
import com.mconnti.moneymanager.entity.State;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.CountryDAO;
import com.mconnti.moneymanager.persistence.StateDAO;
import com.mconnti.moneymanager.utils.MessageFactory;

public class StateBOImpl extends GenericBOImpl<State> implements StateBO {

	@Autowired
	private StateDAO stateDAO;

	@Autowired
	private CountryDAO countryDAO;

	private Country getCountry(State state) {
		try {
			return countryDAO.findById(Country.class, state.getCountry().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public MessageReturn save(State state) {
		MessageReturn libReturn = new MessageReturn();
		Country country = getCountry(state);
		if (country != null) {
			State c = null;
			try {
				String[] nameSplit = state.getName().split(";");
				if (nameSplit.length > 1) {
					for (int x = 0; x < nameSplit.length; x++) {
						c = new State();
						c.setName(nameSplit[x]);
						c.setCountry(country);
						saveGeneric(c);
					}
				} else {
					c = new State();
					c.setId(state.getId());
					c.setName(state.getName());
					c.setCountry(country);
					saveGeneric(c);
				}
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setState(c);
				libReturn.setMessage(e.getMessage());
			}
			if (libReturn.getMessage() == null && state.getId() == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_state_saved", country.getLocale()));
				libReturn.setState(c);
			} else if (libReturn.getMessage() == null && state.getId() != null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_state_updated", country.getLocale()));
				libReturn.setState(c);
			}
		} else{
			libReturn.setMessage(MessageFactory.getMessage("lb_state_not_found", "en"));
		}
		return libReturn;
	}

	public List<State> list() throws Exception {
		return list(State.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		State state = null;
		try {
			state = findById(State.class, id);
			if (state == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_state_not_found", "en"));
			} else {
				remove(state);
				libReturn.setMessage(MessageFactory.getMessage("lb_state_deleted", state.getCountry().getLocale()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public State getById(Long id) {
		try {
			return findById(State.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
