package com.mconnti.moneymanager.persistence.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.Register;
import com.mconnti.moneymanager.entity.TypeAccount;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.persistence.RegisterDAO;

@Repository("registerDAO")
public class RegisterDAOImpl extends GenericDAOImpl<Register> implements RegisterDAO{
	
	@Override
	public Register getByDescription(Description description, User user, TypeAccount typeAccount ){
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select d from Register d ");
		sql.append(" where d.id = ");
		sql.append(" ( select max(id) from Register ");
		sql.append(" where typeAccount = ").append(typeAccount.getId());
		sql.append(" and user = ").append(user.getId());
		sql.append(" and description = ").append(description.getId()).append(" )");
		
		System.out.println("Query: "+sql.toString());
		
		Query query = em.createQuery(sql.toString());
		Register res = (Register) query.getSingleResult();
		return res;
	}
	

}
