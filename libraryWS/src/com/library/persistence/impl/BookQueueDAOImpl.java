package com.library.persistence.impl;

import org.springframework.stereotype.Repository;

import com.library.entity.BookQueue;
import com.library.persistence.BookQueueDAO;

@Repository("bookQueueDAO")
public class BookQueueDAOImpl extends GenericDAOImpl<BookQueue> implements BookQueueDAO{

}
