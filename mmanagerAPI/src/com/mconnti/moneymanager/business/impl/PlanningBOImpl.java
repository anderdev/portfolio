package com.mconnti.moneymanager.business.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.PlanningBO;
import com.mconnti.moneymanager.entity.Planning;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.utils.MessageFactory;

public class PlanningBOImpl extends GenericBOImpl<Planning> implements PlanningBO {

	@Override
	@Transactional
	public MessageReturn save(Planning planning) {
		MessageReturn libReturn = new MessageReturn();
		try {
			saveGeneric(planning);
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setPlanning(planning);
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null && planning.getId() == null) {
			libReturn.setMessage(MessageFactory.getMessage("lb_planning_saved", planning.getUser().getCity().getState().getCountry().getLocale()));
			libReturn.setPlanning(planning);
		} else if (libReturn.getMessage() == null && planning.getId() != null) {
			libReturn.setMessage(MessageFactory.getMessage("lb_planning_updated", planning.getUser().getCity().getState().getCountry().getLocale()));
			libReturn.setPlanning(planning);
		}

		return libReturn;
	}

	public List<Planning> list(User user) throws Exception {
		Map<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put(" where x.user.id = ", user.getId() + "");
		List<Planning> result = list(Planning.class, queryParams, null);
		return result;
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Planning planning = null;
		try {
			planning = findById(Planning.class, id);
			if (planning == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_planning_not_found", "en"));
			} else {
				String locale = planning.getUser().getCity().getState().getCountry().getLocale();
				remove(planning);
				libReturn.setMessage(MessageFactory.getMessage("lb_planning_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public Planning getById(Long id) {
		try {
			return findById(Planning.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
