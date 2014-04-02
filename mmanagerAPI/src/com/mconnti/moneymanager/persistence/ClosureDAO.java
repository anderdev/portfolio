package com.mconnti.moneymanager.persistence;

import java.util.Collection;

import com.mconnti.moneymanager.entity.Closure;
import com.mconnti.moneymanager.entity.Register;
import com.mconnti.moneymanager.entity.User;

public interface ClosureDAO  extends GenericDAO<Closure>{

	public Collection<Register> getRegisters(User user, String startDate, String endDate, boolean closed, Long typeAccountId);
	
}
