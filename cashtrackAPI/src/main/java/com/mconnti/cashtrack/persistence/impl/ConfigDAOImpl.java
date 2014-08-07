package com.mconnti.cashtrack.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.cashtrack.entity.Config;
import com.mconnti.cashtrack.persistence.ConfigDAO;

@Repository("configDAO")
public class ConfigDAOImpl extends GenericDAOImpl<Config> implements ConfigDAO{
	

}
