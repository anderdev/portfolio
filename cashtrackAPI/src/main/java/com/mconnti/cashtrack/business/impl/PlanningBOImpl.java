package com.mconnti.cashtrack.business.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.cashtrack.business.PlanningBO;
import com.mconnti.cashtrack.business.PlanningGroupBO;
import com.mconnti.cashtrack.business.PlanningItemBO;
import com.mconnti.cashtrack.business.UserBO;
import com.mconnti.cashtrack.entity.Description;
import com.mconnti.cashtrack.entity.Planning;
import com.mconnti.cashtrack.entity.PlanningGroup;
import com.mconnti.cashtrack.entity.PlanningItem;
import com.mconnti.cashtrack.entity.TypeAccount;
import com.mconnti.cashtrack.entity.User;
import com.mconnti.cashtrack.entity.xml.MessageReturn;
import com.mconnti.cashtrack.utils.MessageFactory;

public class PlanningBOImpl extends GenericBOImpl<Planning> implements PlanningBO {

	@Autowired
	private PlanningGroupBO planningGroupBO;

	@Autowired
	private PlanningItemBO planningItemBO;
	
	@Autowired
	private UserBO userBO;
	
	private User getSuperUser(User user) {
		return userBO.getSuperUser(user);
	}
	
	@Override
	@Transactional
	public MessageReturn save(Planning planning) {
		MessageReturn libReturn = new MessageReturn();
		User user = getSuperUser(planning.getUser());
		try {
			Planning planTemp = getSelectedPlanning(user);
			if (planTemp != null) {
				planTemp.setSelected(false);
				planTemp.setUser(user);
				saveGeneric(planTemp);
			}
			saveGeneric(planning);
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setPlanning(planning);
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null && planning.getId() == null) {
			libReturn.setMessage(MessageFactory.getMessage("lb_planning_saved", planning.getUser().getLanguage()));
			libReturn.setPlanning(planning);
		} else if (libReturn.getMessage() == null && planning.getId() != null) {
			libReturn.setMessage(MessageFactory.getMessage("lb_planning_updated", planning.getUser().getLanguage()));
			libReturn.setPlanning(planning);
		}

		return libReturn;
	}

	@Transactional
	public List<Planning> list(Planning plannig) throws Exception {
		Map<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put(" where x.user.id = ", getSuperUser(plannig.getUser()).getId() + "");
		List<Planning> result = list(Planning.class, queryParams, null);
		return result;
	}

	@Override
	public MessageReturn delete(final PlanningGroup planningGroup) {
		MessageReturn libReturn = new MessageReturn();
		try {
			String locale = planningGroup.getUser().getLanguage();
			planningGroupBO.delete(planningGroup.getId());
			
			libReturn.setMessage(MessageFactory.getMessage("lb_planning_deleted", locale));
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

	@Override
	public Planning getSelectedPlanning(User user) throws Exception {

		Map<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put(" where x.user = ", user.getId() + "");
		queryParams.put(" and x.selected = ", " 1 ");
		findByParameter(Planning.class, queryParams);

		return findByParameter(Planning.class, queryParams);
	}

	@Override
	@Transactional
	public MessageReturn saveGroup(PlanningGroup planningGroup) throws Exception {
		MessageReturn libReturn = new MessageReturn();
		try {
			User user = getSuperUser(planningGroup.getUser());
			planningGroup.setPlanning(getSelectedPlanning(user));
			List<PlanningItem> pItemList = new ArrayList<>();
			for (int x = 1; x < 13; x++) {
				PlanningItem pItem = new PlanningItem();
				pItem.setAmount(BigDecimal.ZERO);
				pItem.setMonth(x);
				pItem.setPlanningGroup(planningGroup);
				pItem.setUser(user);
				pItemList.add(pItem);
			}
			
			planningGroup.getPlannigItemList().addAll(pItemList);
			
			Description description = findById(Description.class, planningGroup.getDescription().getId());
			TypeAccount typeAccount = findById(TypeAccount.class, planningGroup.getTypeAccount().getId());
			planningGroup.setUser(user);
			
			planningGroup.setDescription(description);
			planningGroup.setTypeAccount(typeAccount);
			
			planningGroupBO.save(planningGroup);
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

	@Override
	@Transactional
	public MessageReturn saveItem(PlanningItem planningItem) throws Exception {
		MessageReturn libReturn = new MessageReturn();
		try {
			PlanningItem itemTemp = planningItemBO.findById(PlanningItem.class, planningItem.getId());
			planningItem.setPlanningGroup(itemTemp.getPlanningGroup());
			planningItem.setUser(getSuperUser(planningItem.getUser()));
			planningItemBO.save(planningItem);
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

}
