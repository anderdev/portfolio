package com.mconnti.moneymanager.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.UserBO;
import com.mconnti.moneymanager.entity.City;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.CityDAO;
import com.mconnti.moneymanager.persistence.UserDAO;
import com.mconnti.moneymanager.utils.MessageFactory;

public class UserBOImpl extends GenericBOImpl<User> implements UserBO {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CityDAO cityDAO;
	
	private City getCity(User user) {
		try {
			return cityDAO.findById(City.class, user.getCity().getState().getCountry().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	@Transactional
	public MessageReturn save(User user) {
		MessageReturn libReturn = new MessageReturn();
		City city = getCity(user);
		if (city != null){
			User c = null;
			try {
				String[] nameSplit = user.getName().split(";");
				if (nameSplit.length > 1) {
					for (int x = 0; x < nameSplit.length; x++) {
						c = new User();
						c.setName(nameSplit[x]);
						c.setCity(city);
						saveGeneric(c);
					}
				} else {
					c = new User();
					c.setId(user.getId());
					c.setName(user.getName());
					saveGeneric(c);
				}
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setUser(c);
				libReturn.setMessage(e.getMessage());
			}
			if (libReturn.getMessage() == null && user.getId() == null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_user_saved", user.getCity().getState().getCountry().getLocale()));
				libReturn.setUser(c);
			} else if (libReturn.getMessage() == null && user.getId() != null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_user_updated", user.getCity().getState().getCountry().getLocale()));
				libReturn.setUser(c);
			}
		} else {
			libReturn.setMessage(MessageFactory.getMessage("lb_city_not_found", "en"));
		}
		
		return libReturn;
	}

	public List<User> list() throws Exception {
		return list(User.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		User user = null;
		try {
			user = findById(User.class, id);
			if (user == null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_user_not_found", "en"));
			} else {
				remove(user);
				libReturn.setMessage( MessageFactory.getMessage("lb_user_deleted", user.getCity().getState().getCountry().getLocale()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public User getById(Long id) {
		try {
			return findById(User.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
