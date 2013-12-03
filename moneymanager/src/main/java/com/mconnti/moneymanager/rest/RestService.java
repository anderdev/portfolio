package com.mconnti.moneymanager.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.mconnti.moneymanager.business.CountryBO;
import com.mconnti.moneymanager.context.SpringApplicationContext;
import com.mconnti.moneymanager.entity.Country;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Path("/rest")
public class RestService {
	
	private CountryBO countryBO;
	
	public RestService() {
		countryBO = (CountryBO) SpringApplicationContext.getBean("countryBO");
	}
	
	@GET
	@Path("/country")
	@Produces({ "application/json" })
	public Response listCountry() {
		
		List<Country> list = new ArrayList<>();
		try {
			list = countryBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}
	
	@POST
	@Path("/country")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response save(Country country){
		MessageReturn ret = new MessageReturn();
		try {
			ret = countryBO.save(country);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

}
