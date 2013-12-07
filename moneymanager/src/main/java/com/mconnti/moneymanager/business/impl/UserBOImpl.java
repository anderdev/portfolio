package com.mconnti.moneymanager.business.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.UserBO;
import com.mconnti.moneymanager.entity.City;
import com.mconnti.moneymanager.entity.Config;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.CityDAO;
import com.mconnti.moneymanager.persistence.ConfigDAO;
import com.mconnti.moneymanager.persistence.UserDAO;
import com.mconnti.moneymanager.utils.MessageFactory;
import com.mconnti.moneymanager.utils.Utils;

public class UserBOImpl extends GenericBOImpl<User> implements UserBO {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CityDAO cityDAO;

	@Autowired
	private ConfigDAO configDAO;

	private City getCity(User user) {
		try {
			return cityDAO.findById(City.class, user.getCity().getId());
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
		Config config = getConfig(user);
		if (city != null) {
			try {
				user.setBirthDate(Utils.stringToDate(user.getBirth(), false));
				user.setRegister(new Date());
				user.setCity(city);
				user.setConfig(config);
				user.setExcluded(false);
				if (user.getPass() != null || !user.getPass().isEmpty()) {
					user.setPassword(user.getPass());
				}
				saveGeneric(user);
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setUser(user);
				libReturn.setMessage(e.getMessage());
			}
			if (libReturn.getMessage() == null && user.getId() == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_user_saved", city.getState().getCountry().getLocale()));
				libReturn.setUser(user);
			} else if (libReturn.getMessage() == null && user.getId() != null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_user_updated", city.getState().getCountry().getLocale()));
				libReturn.setUser(user);
			}
		} else {
			libReturn.setMessage(MessageFactory.getMessage("lb_city_not_found", "en"));
		}

		return libReturn;
	}

	private Config getConfig(User user) {
		try {
			return configDAO.findById(Config.class, user.getConfig().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
				libReturn.setMessage(MessageFactory.getMessage("lb_user_not_found", "en"));
			} else {
				String locale = user.getCity().getState().getCountry().getLocale();
				remove(user);
				libReturn.setMessage(MessageFactory.getMessage("lb_user_deleted", locale));
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
