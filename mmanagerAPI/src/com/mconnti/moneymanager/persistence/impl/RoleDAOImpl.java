package com.mconnti.moneymanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.Role;
import com.mconnti.moneymanager.persistence.RoleDAO;

@Repository("roleDAO")
public class RoleDAOImpl extends GenericDAOImpl<Role> implements RoleDAO{
	

}
