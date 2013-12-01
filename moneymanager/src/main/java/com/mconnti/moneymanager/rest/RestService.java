package com.mconnti.moneymanager.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.mconnti.moneymanager.business.CountryBO;
import com.mconnti.moneymanager.context.SpringApplicationContext;
import com.mconnti.moneymanager.customer.CustomerBo;
import com.mconnti.moneymanager.entity.Country;

@Path("/rest")
public class RestService {
	
	CustomerBo customerBo;
	CountryBO countryBO;
	
	public RestService() {
		customerBo = (CustomerBo) SpringApplicationContext.getBean("customerBo");
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

}
