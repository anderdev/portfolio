package com.mconnti.moneymanager.business.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.PlanningItemBO;
import com.mconnti.moneymanager.entity.PlanningItem;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.utils.MessageFactory;

public class PlanningItemBOImpl extends GenericBOImpl<PlanningItem> implements PlanningItemBO {

	@Override
	@Transactional
	public MessageReturn save(PlanningItem planningItem) {
		MessageReturn libReturn = new MessageReturn();
		try {
			saveGeneric(planningItem);
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setPlanningItem(planningItem);
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null && planningItem.getId() == null) {
			libReturn.setMessage(MessageFactory.getMessage("lb_planning_saved", planningItem.getUser().getCity().getState().getCountry().getLocale()));
			libReturn.setPlanningItem(planningItem);
		} else if (libReturn.getMessage() == null && planningItem.getId() != null) {
			libReturn.setMessage(MessageFactory.getMessage("lb_planning_updated", planningItem.getUser().getCity().getState().getCountry().getLocale()));
			libReturn.setPlanningItem(planningItem);
		}

		return libReturn;
	}

	public List<PlanningItem> list() throws Exception {
		return list(PlanningItem.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		PlanningItem planningItem = null;
		try {
			planningItem = findById(PlanningItem.class, id);
			if (planningItem == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_planning_not_found", "en"));
			} else {
				String locale = planningItem.getUser().getCity().getState().getCountry().getLocale();
				remove(planningItem);
				libReturn.setMessage(MessageFactory.getMessage("lb_planning_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public PlanningItem getById(Long id) {
		try {
			return findById(PlanningItem.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
