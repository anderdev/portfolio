package com.library.persistence.impl;

import org.springframework.stereotype.Repository;

import com.library.entity.Author;
import com.library.persistence.AuthorDAO;

@Repository("authorDAO")
public class AuthorDAOImpl extends GenericDAOImpl<Author> implements AuthorDAO {

}
