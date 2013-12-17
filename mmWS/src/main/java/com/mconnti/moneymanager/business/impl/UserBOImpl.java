package com.mconnti.moneymanager.business.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.UserBO;
import com.mconnti.moneymanager.entity.City;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.CityDAO;
import com.mconnti.moneymanager.persistence.UserDAO;
import com.mconnti.moneymanager.utils.Crypt;
import com.mconnti.moneymanager.utils.MessageFactory;
import com.mconnti.moneymanager.utils.Utils;

public class UserBOImpl extends GenericBOImpl<User> implements UserBO {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CityDAO cityDAO;

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
		if (city != null) {
			try {
				if (user.getId() == null) {
					if (user.getBirth() != null && !user.getBirth().isEmpty()) {
						user.setBirthDate(Utils.stringToDate(user.getBirth(), false));
					}
					user.setRegister(new Date());
					user.setExcluded(false);
					if (user.getPass() != null && !user.getPass().isEmpty()) {
						user.setPassword(user.getPass());
					}
				} else {
					user.setPassword(Crypt.decrypt(Crypt.decrypt(user.getPassword())));
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

	@Override
	public List<User> list() throws Exception {
		return list(User.class, null, null);
	}

	@Override
	public List<User> listByParameter(User user) throws Exception {
		return list(User.class, user.getQueryParams(), user.getOrderBy());
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

	@Override
	public MessageReturn login(User user) {
		MessageReturn messageReturn = null;
		try {
			messageReturn = userDAO.getByUsername(user.getUsername());
			if (messageReturn.getUser() == null) {
				messageReturn.setMessage(MessageFactory.getMessage("lb_user_not_found", "en"));
			} else if (!messageReturn.getUser().getPassword().equals(user.getPassword())) {
				messageReturn.setUser(null);
				messageReturn.setMessage(MessageFactory.getMessage("lb_user_incorrect_password", "en"));
			} else {
				messageReturn.setMessage(MessageFactory.getMessage("lb_login_success", messageReturn.getUser().getLanguage()));
			}
		} catch (Exception e) {
			messageReturn = new MessageReturn();
			messageReturn.setMessage(e.getMessage());
		}
		return messageReturn;
	}
}
