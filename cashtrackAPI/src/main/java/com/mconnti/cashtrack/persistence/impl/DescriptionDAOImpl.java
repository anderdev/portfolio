package com.mconnti.cashtrack.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.cashtrack.entity.Description;
import com.mconnti.cashtrack.persistence.DescriptionDAO;

@Repository("descriptionDAO")
public class DescriptionDAOImpl extends GenericDAOImpl<Description> implements DescriptionDAO{

}
