package com.library.business;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.library.entity.Book;
import com.library.entity.xml.MessageReturn;
import com.library.entity.xml.SearchObject;

public interface BookBO extends GenericBO<Book> {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> list();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MessageReturn save(final Book book);
	
	@DELETE @Path("{id}")
	public MessageReturn delete (@PathParam("id") Long id);
	
	@GET @Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Book getById(@PathParam("id") Long id);
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> searchedList(SearchObject serachObject);

}
