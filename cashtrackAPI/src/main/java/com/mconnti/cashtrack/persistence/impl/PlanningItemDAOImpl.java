package com.mconnti.cashtrack.persistence.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mconnti.cashtrack.entity.PlanningItem;
import com.mconnti.cashtrack.entity.Register;
import com.mconnti.cashtrack.persistence.PlanningItemDAO;

@Repository("planningItemDAO")
public class PlanningItemDAOImpl extends GenericDAOImpl<PlanningItem> implements PlanningItemDAO{

	@Override
	public Register delete(PlanningItem planningItem) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" delete from PlanningItem d ");
		sql.append(" where d.id = "+planningItem.getId());
		
		System.out.println("Query: "+sql.toString());
		
		Query query = em.createQuery(sql.toString());
		Register res;
		try {
			res = (Register) query.getSingleResult();
		}  catch (NoResultException nre) {
			res = null;
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
		return res;
	}
	

}
