package com.mconnti.cashtrack.persistence.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mconnti.cashtrack.entity.Description;
import com.mconnti.cashtrack.entity.Register;
import com.mconnti.cashtrack.entity.TypeAccount;
import com.mconnti.cashtrack.entity.User;
import com.mconnti.cashtrack.persistence.RegisterDAO;

@Repository("registerDAO")
public class RegisterDAOImpl extends GenericDAOImpl<Register> implements RegisterDAO{
	
	@Override
	public Register getByDescription(Description description, User user, TypeAccount typeAccount ) throws Exception{
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select d from Register d ");
		sql.append(" where d.id = ");
		sql.append(" ( select max(id) from Register ");
		sql.append(" where typeAccount = ").append(typeAccount.getId());
		sql.append(" and user = ").append(user.getId());
		sql.append(" and description = ").append(description.getId()).append(" )");
		
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
