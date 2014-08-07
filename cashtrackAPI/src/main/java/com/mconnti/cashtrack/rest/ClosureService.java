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

import com.mconnti.cashtrack.business.ClosureBO;
import com.mconnti.cashtrack.context.SpringApplicationContext;
import com.mconnti.cashtrack.entity.Closure;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Path("/rest")
public class ClosureService {

	private ClosureBO closureBO;

	public ClosureService() {
		closureBO = (ClosureBO) SpringApplicationContext.getBean("closureBO");
	}

	@PUT
	@Path("/closure/all")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listClosure(Closure closure) {

		List<Closure> list = new ArrayList<>();
		try {
			list = closureBO.list(closure.getUser());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@PUT
	@Path("/closure/list")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listClosureByParameter(Closure closure) {

		List<Closure> list = new ArrayList<>();
		try {
			list = closureBO.listByParameter(closure);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@PUT
	@Path("/closure")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response executeClosure(Closure closure) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = closureBO.getValuesToClose(closure);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@POST
	@Path("/closure")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response saveClosure(Closure closure) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = closureBO.save(closure);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/closure")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteClosure(Closure closure) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = closureBO.delete(closure.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
}
