package com.library.persistence.impl;

import org.springframework.stereotype.Repository;

import com.library.entity.Book;
import com.library.persistence.BookDAO;

@Repository("bookDAO")
public class BookDAOImpl extends GenericDAOImpl<Book> implements BookDAO{

}
