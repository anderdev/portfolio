package com.mconnti.cashtrack.persistence.impl;

import java.util.Collection;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mconnti.cashtrack.entity.Closure;
import com.mconnti.cashtrack.entity.Register;
import com.mconnti.cashtrack.entity.User;
import com.mconnti.cashtrack.persistence.ClosureDAO;

@Repository("closureDAO")
public class ClosureDAOImpl extends GenericDAOImpl<Closure> implements ClosureDAO{

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Register> getRegisters(User user, String startDate, String endDate, boolean closed, Long typeAccountId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select d from Register d ");
		sql.append(" where d.user = ").append(user.getId());
		sql.append(" and d.closed = ").append(closed);
		sql.append(" and d.typeAccount = ").append(typeAccountId);
		sql.append(" and d.date between ");
		sql.append(" str_to_date('").append(startDate).append("', '%d/%m/%Y') and str_to_date('").append(endDate).append("', '%d/%m/%Y') ");
		
		System.out.println("Query: "+sql.toString());
		
		Query query = em.createQuery(sql.toString());
		Collection<Register> res = query.getResultList();
		
		return res;
	}
	

}
