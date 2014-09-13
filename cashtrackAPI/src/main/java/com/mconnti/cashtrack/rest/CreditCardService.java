package com.mconnti.cashtrack.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mconnti.cashtrack.business.CreditCardBO;
import com.mconnti.cashtrack.context.SpringApplicationContext;
import com.mconnti.cashtrack.entity.CreditCard;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Path("/rest")
public class CreditCardService {

	private CreditCardBO creditCardBO;

	public CreditCardService() {
		creditCardBO = (CreditCardBO) SpringApplicationContext.getBean("creditCardBO");
	}

	@GET
	@Path("/creditcard/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listByParameter(@PathParam("userId") String userId) {
		List<CreditCard> list = new ArrayList<>();
		try {
			list = creditCardBO.listByParameter(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/creditcard")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response save(CreditCard creditCard) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = creditCardBO.save(creditCard);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/creditcard/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response delete(@PathParam("id") String id) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = creditCardBO.delete(Long.valueOf(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
}
