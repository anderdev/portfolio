package com.mconnti.moneymanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.Account;
import com.mconnti.moneymanager.persistence.AccountDAO;

@Repository("accountDAO")
public class AccountDAOImpl extends GenericDAOImpl<Account> implements AccountDAO{
	

}
