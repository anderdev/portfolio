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

import com.mconnti.cashtrack.business.TypeClosureBO;
import com.mconnti.cashtrack.context.SpringApplicationContext;
import com.mconnti.cashtrack.entity.TypeClosure;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Path("/rest")
public class TypeClosureService {

	private TypeClosureBO typeClosureBO;

	public TypeClosureService() {
		typeClosureBO = (TypeClosureBO) SpringApplicationContext.getBean("typeClosureBO");
	}

	@PUT
	@Path("/typeclosure")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listTypeClosure(TypeClosure typeClosure) {

		List<TypeClosure> list = new ArrayList<>();
		try {
			list = typeClosureBO.list(typeClosure);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/typeclosure")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response saveTypeClosure(TypeClosure typeClosure) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = typeClosureBO.save(typeClosure);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/typeclosure")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteTypeClosure(TypeClosure typeClosure) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = typeClosureBO.delete(typeClosure.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
}