package com.library.persistence.impl;

import org.springframework.stereotype.Repository;

import com.library.entity.Category;
import com.library.persistence.CategoryDAO;

@Repository("categoryDAO")
public class CategoryDAOImpl extends GenericDAOImpl<Category> implements CategoryDAO{

}
