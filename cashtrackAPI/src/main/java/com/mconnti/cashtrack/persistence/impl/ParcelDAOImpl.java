package com.mconnti.cashtrack.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.cashtrack.entity.Parcel;
import com.mconnti.cashtrack.persistence.ParcelDAO;

@Repository("parcelDAO")
public class ParcelDAOImpl extends GenericDAOImpl<Parcel> implements ParcelDAO{
	

}
