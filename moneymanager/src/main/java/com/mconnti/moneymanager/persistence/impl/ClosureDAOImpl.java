package com.mconnti.moneymanager.persistence.impl;

import java.util.Collection;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.Closure;
import com.mconnti.moneymanager.entity.Credit;
import com.mconnti.moneymanager.entity.Debit;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.persistence.ClosureDAO;

@Repository("closureDAO")
public class ClosureDAOImpl extends GenericDAOImpl<Closure> implements ClosureDAO{

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Credit> getCredits(User user, String startDate, String endDate, boolean closed) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select from Credit cred ");
		sql.append(" where cred.user = ").append(user);
		sql.append(" and cred.closed = ").append(closed);
		sql.append(" and cred.date between ");
		sql.append(" str_to_date(").append(startDate).append(", '%d/%m/%Y') and str_to_date(").append(endDate).append(", '%d/%m/%Y') ");
		
		Query query = em.createQuery(sql.toString());
		query.setHint("toplink.refresh", "true");
		Collection<Credit> res = query.getResultList();
		
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Debit> getDebts(User user, String startDate, String endDate, boolean closed) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select from Debit d ");
		sql.append(" where d.user = ").append(user);
		sql.append(" and d.closed = ").append(closed);
		sql.append(" and d.date between ");
		sql.append(" str_to_date(").append(startDate).append(", '%d/%m/%Y') and str_to_date(").append(endDate).append(", '%d/%m/%Y') ");
		
		Query query = em.createQuery(sql.toString());
		query.setHint("toplink.refresh", "true");
		Collection<Debit> res = query.getResultList();
		
		return res;
	}
	

}
