package com.mconnti.cashtrack.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.cashtrack.entity.Role;
import com.mconnti.cashtrack.persistence.RoleDAO;

@Repository("roleDAO")
public class RoleDAOImpl extends GenericDAOImpl<Role> implements RoleDAO{
	

}
