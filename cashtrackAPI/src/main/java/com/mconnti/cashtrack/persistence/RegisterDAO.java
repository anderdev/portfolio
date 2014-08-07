package com.mconnti.cashtrack.persistence;

import com.mconnti.cashtrack.entity.Description;
import com.mconnti.cashtrack.entity.Register;
import com.mconnti.cashtrack.entity.TypeAccount;
import com.mconnti.cashtrack.entity.User;

public interface RegisterDAO  extends GenericDAO<Register>{
	
	public Register getByDescription(Description description, User user, TypeAccount typeAccount ) throws Exception;
	
}
