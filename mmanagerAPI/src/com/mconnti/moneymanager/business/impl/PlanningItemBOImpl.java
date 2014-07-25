package com.mconnti.moneymanager.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.PlanningItemBO;
import com.mconnti.moneymanager.business.UserBO;
import com.mconnti.moneymanager.entity.PlanningItem;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.PlanningItemDAO;
import com.mconnti.moneymanager.utils.MessageFactory;

public class PlanningItemBOImpl extends GenericBOImpl<PlanningItem> implements PlanningItemBO {
	
	@Autowired
	private PlanningItemDAO planningItemDAO;
	
	@Autowired
	private UserBO userBO;
	
	private User getSuperUser(PlanningItem planningItem) {
		return userBO.getSuperUser(planningItem.getUser());
	}

	@Override
	@Transactional
	public MessageReturn save(PlanningItem planningItem) {
		MessageReturn libReturn = new MessageReturn();
		try {
			planningItem.setUser(getSuperUser(planningItem));
			saveGeneric(planningItem);
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setPlanningItem(planningItem);
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null && planningItem.getId() == null) {
			libReturn.setMessage(MessageFactory.getMessage("lb_planning_saved", planningItem.getUser().getLanguage()));
			libReturn.setPlanningItem(planningItem);
		} else if (libReturn.getMessage() == null && planningItem.getId() != null) {
			libReturn.setMessage(MessageFactory.getMessage("lb_planning_updated", planningItem.getUser().getLanguage()));
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
				String locale = planningItem.getUser().getLanguage();
				planningItemDAO.delete(planningItem);
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
