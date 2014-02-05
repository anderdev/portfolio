package com.mconnti.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mconnti.entity.PageView;
import com.mconnti.persistence.PageViewDAO;

@Repository("pageViewDAO")
public class PageViewDAOImpl extends GenericDAOImpl<PageView> implements PageViewDAO{
	

}
