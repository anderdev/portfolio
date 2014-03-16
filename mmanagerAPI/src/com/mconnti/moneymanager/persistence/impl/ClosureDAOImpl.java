package com.mconnti.moneymanager.persistence.impl;

import java.util.Collection;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.Closure;
import com.mconnti.moneymanager.entity.Register;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.persistence.ClosureDAO;

@Repository("closureDAO")
public class ClosureDAOImpl extends GenericDAOImpl<Closure> implements ClosureDAO{

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Register> getRegisters(User user, String startDate, String endDate, boolean closed, int typeAccountId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select d from Register d ");
		sql.append(" where d.user = ").append(user.getId());
		sql.append(" and d.closed = ").append(closed);
		sql.append(" and d.typeAccount = ").append(typeAccountId);
		sql.append(" and d.date between ");
		sql.append(" str_to_date('").append(startDate).append("', '%d/%m/%Y') and str_to_date('").append(endDate).append("', '%d/%m/%Y') ");
		
		Query query = em.createQuery(sql.toString());
		Collection<Register> res = query.getResultList();
		
		return res;
	}
	

}
