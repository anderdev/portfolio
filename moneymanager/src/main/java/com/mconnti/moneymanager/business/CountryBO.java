package com.mconnti.moneymanager.business;

import java.util.List;

import javax.ws.rs.PathParam;

import com.mconnti.moneymanager.entity.Country;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

public interface CountryBO extends GenericBO<Country>{
	
//	@GET
//	@Produces({ "application/json" })
	public List<Country> list() throws Exception;
	
//	@POST
//	@Consumes({ "application/json" })
//	@Produces({ "application/json" })
	public MessageReturn save(final Country country) throws Exception;
	
//	@DELETE @Path("{id}")
//	@Consumes({ "application/json" })
//	@Produces({ "application/json" })
	public MessageReturn delete (@PathParam("id") Long id);
	
//	@GET @Path("{id}")
//	@Consumes({ "application/json" })
//	@Produces({ "application/json" })
	public Country getById(@PathParam("id") Long id);
}

