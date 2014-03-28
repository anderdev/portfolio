package com.mconnti.moneymanager.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.ConfigBO;
import com.mconnti.moneymanager.entity.Config;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.UserDAO;
import com.mconnti.moneymanager.utils.MessageFactory;

public class ConfigBOImpl extends GenericBOImpl<Config> implements ConfigBO {

	@Autowired
	private UserDAO userDAO;

	private User getUser(Config config) {
		try {
			return userDAO.findById(User.class, config.getUser().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public MessageReturn save(Config config) {
		MessageReturn libReturn = new MessageReturn();
		User user = getUser(config);
		if (user != null || config.getTypeClosure() != null || config.getCurrency() != null) {
			try {
				saveGeneric(config);
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setConfig(config);
				libReturn.setMessage(e.getMessage());
			}
			if (libReturn.getMessage() == null && config.getId() == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_config_saved", user.getCity().getState().getCountry().getLocale()));
				libReturn.setConfig(config);
			} else if (libReturn.getMessage() == null && config.getId() != null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_config_updated", user.getCity().getState().getCountry().getLocale()));
				libReturn.setConfig(config);
			}
		} else {
			libReturn.setMessage(MessageFactory.getMessage("lb_config_not_found", "en"));
		}

		return libReturn;
	}

	public List<Config> list() throws Exception {
		return list(Config.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Config config = null;
		try {
			config = findById(Config.class, id);
			if (config == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_config_not_found", "en"));
			} else {
				String locale = config.getUser().getCity().getState().getCountry().getLocale();
				remove(config);
				libReturn.setMessage(MessageFactory.getMessage("lb_config_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public Config getById(Long id) {
		try {
			return findById(Config.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
