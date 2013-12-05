package com.mconnti.moneymanager.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.moneymanager.entity.Parcel;
import com.mconnti.moneymanager.persistence.ParcelDAO;

@Repository("parcelDAO")
public class ParcelDAOImpl extends GenericDAOImpl<Parcel> implements ParcelDAO{
	

}
