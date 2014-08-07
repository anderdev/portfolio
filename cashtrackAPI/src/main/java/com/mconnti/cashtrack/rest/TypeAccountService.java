package com.mconnti.cashtrack.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mconnti.cashtrack.business.TypeAccountBO;
import com.mconnti.cashtrack.context.SpringApplicationContext;
import com.mconnti.cashtrack.entity.Description;
import com.mconnti.cashtrack.entity.TypeAccount;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Path("/rest")
public class TypeAccountService {

	private TypeAccountBO typeAccountBO;

	public TypeAccountService() {
		typeAccountBO = (TypeAccountBO) SpringApplicationContext.getBean("typeAccountBO");
	}

	@PUT
	@Path("/typeaccount")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listAccount(TypeAccount typeAccount) {

		List<TypeAccount> list = new ArrayList<>();
		try {
			list = typeAccountBO.list(typeAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@PUT
	@Path("/typeaccount/getbydescription")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAccountByDescription(Description description) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = typeAccountBO.getByDescription(description);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@POST
	@Path("/typeaccount")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response saveAccount(TypeAccount account) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = typeAccountBO.save(account);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/typeaccount")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteAccount(TypeAccount account) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = typeAccountBO.delete(account.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
}
