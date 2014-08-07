package com.mconnti.cashtrack.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.cashtrack.business.PlanningGroupBO;
import com.mconnti.cashtrack.business.UserBO;
import com.mconnti.cashtrack.entity.PlanningGroup;
import com.mconnti.cashtrack.entity.User;
import com.mconnti.cashtrack.entity.xml.MessageReturn;
import com.mconnti.cashtrack.utils.MessageFactory;

public class PlanningGroupBOImpl extends GenericBOImpl<PlanningGroup> implements PlanningGroupBO {
	
	@Autowired
	private UserBO userBO;
	
	private User getSuperUser(PlanningGroup planningGroup) {
		return userBO.getSuperUser(planningGroup.getUser());
	}

	@Override
	@Transactional
	public MessageReturn save(PlanningGroup planningGroup) {
		MessageReturn libReturn = new MessageReturn();
		try {
			planningGroup.setUser(getSuperUser(planningGroup));
			saveGeneric(planningGroup);
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setPlanningGroup(planningGroup);
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null && planningGroup.getId() == null) {
			libReturn.setMessage(MessageFactory.getMessage("lb_planning_saved", planningGroup.getUser().getLanguage()));
			libReturn.setPlanningGroup(planningGroup);
		} else if (libReturn.getMessage() == null && planningGroup.getId() != null) {
			libReturn.setMessage(MessageFactory.getMessage("lb_planning_updated", planningGroup.getUser().getLanguage()));
			libReturn.setPlanningGroup(planningGroup);
		}

		return libReturn;
	}

	public List<PlanningGroup> list() throws Exception {
		return list(PlanningGroup.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		PlanningGroup planningGroup = null;
		try {
			planningGroup = findById(PlanningGroup.class, id);
			if (planningGroup == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_planning_not_found", "en"));
			} else {
				String locale = planningGroup.getUser().getLanguage();
				remove(planningGroup);
				libReturn.setMessage(MessageFactory.getMessage("lb_planning_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public PlanningGroup getById(Long id) {
		try {
			return findById(PlanningGroup.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
