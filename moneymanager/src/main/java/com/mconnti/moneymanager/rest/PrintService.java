package com.mconnti.moneymanager.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.mconnti.moneymanager.context.SpringApplicationContext;
import com.mconnti.moneymanager.customer.CustomerBo;

@Path("/customer")
public class PrintService {

	CustomerBo customerBo;

	@GET
	@Path("/print")
	public Response printMessage() {

		customerBo = (CustomerBo) SpringApplicationContext.getBean("customerBo");
		
		String result = customerBo.getMsg();

		return Response.status(200).entity(result).build();

	}

}