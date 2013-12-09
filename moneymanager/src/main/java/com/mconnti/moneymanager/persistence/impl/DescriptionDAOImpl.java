package com.mconnti.moneymanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.persistence.DescriptionDAO;

@Repository("descriptionDAO")
public class DescriptionDAOImpl extends GenericDAOImpl<Description> implements DescriptionDAO{

}
