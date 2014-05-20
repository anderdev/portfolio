package com.mconnti.moneymanager.persistence;

import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.Register;
import com.mconnti.moneymanager.entity.TypeAccount;
import com.mconnti.moneymanager.entity.User;

public interface RegisterDAO  extends GenericDAO<Register>{
	
	public Register getByDescription(Description description, User user, TypeAccount typeAccount );
	
}
