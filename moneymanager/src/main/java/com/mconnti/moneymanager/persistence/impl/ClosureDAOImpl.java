package com.mconnti.moneymanager.persistence.impl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.Closure;
import com.mconnti.moneymanager.entity.Credit;
import com.mconnti.moneymanager.entity.Debit;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.persistence.ClosureDAO;

@Repository("closureDAO")
public class ClosureDAOImpl extends GenericDAOImpl<Closure> implements ClosureDAO{

	@Override
	public Collection<String> getClosedCredits(User user, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getClosedDebits(User user, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Credit> getCredits(User user, String startDate, String endDate, boolean closed) {
		Object[] obj = {user,closed,startDate,endDate};
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from Credit cred ");
		sql.append(" where cred.usuario = ? ");
		sql.append(" and cred.contabilizado = ? ");
		sql.append(" and cred.data between ");
		sql.append(" str_to_date(?, '%d/%m/%Y') and str_to_date(?, '%d/%m/%Y') ");
		
		Collection<Credit> res = em.createQuery(sql.toString(),obj);
		
		return null;
	}

	@Override
	public Collection<Debit> getDebts(User user, String startDate, String endDate, boolean closed) {
		Object[] obj = {user,closed,startDate,endDate};
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from Debito deb ");
		sql.append(" where deb.usuario = ? ");
		sql.append(" and deb.contabilizado = ? ");
		sql.append(" and deb.data between ");
		sql.append(" str_to_date(?, '%d/%m/%Y') and str_to_date(?, '%d/%m/%Y') ");
		
//		Collection<Debito> res = getHibernateTemplate().find(sql.toString(),obj);
		
		return null;
	}
	

}
