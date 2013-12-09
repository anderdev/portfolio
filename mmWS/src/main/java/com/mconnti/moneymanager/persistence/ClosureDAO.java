package com.mconnti.moneymanager.persistence;

import java.util.Collection;

import com.mconnti.moneymanager.entity.Closure;
import com.mconnti.moneymanager.entity.Credit;
import com.mconnti.moneymanager.entity.Debit;
import com.mconnti.moneymanager.entity.User;

public interface ClosureDAO  extends GenericDAO<Closure>{

	public Collection<Credit> getCredits(User user, String startDate, String endDate, boolean closed);

	public Collection<Debit> getDebts(User user, String startDate, String endDate, boolean closed);
	
}
