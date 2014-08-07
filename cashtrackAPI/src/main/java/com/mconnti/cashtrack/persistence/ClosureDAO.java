package com.mconnti.cashtrack.persistence;

import java.util.Collection;

import com.mconnti.cashtrack.entity.Closure;
import com.mconnti.cashtrack.entity.Register;
import com.mconnti.cashtrack.entity.User;

public interface ClosureDAO  extends GenericDAO<Closure>{

	public Collection<Register> getRegisters(User user, String startDate, String endDate, boolean closed, Long typeAccountId);
	
}
