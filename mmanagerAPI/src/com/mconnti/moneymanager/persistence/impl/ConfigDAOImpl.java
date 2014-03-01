package com.mconnti.moneymanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.Config;
import com.mconnti.moneymanager.persistence.ConfigDAO;

@Repository("configDAO")
public class ConfigDAOImpl extends GenericDAOImpl<Config> implements ConfigDAO{
	

}
