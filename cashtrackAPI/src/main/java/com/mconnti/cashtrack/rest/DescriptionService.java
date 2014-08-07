package com.mconnti.cashtrack.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mconnti.cashtrack.business.DescriptionBO;
import com.mconnti.cashtrack.context.SpringApplicationContext;
import com.mconnti.cashtrack.entity.Description;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Path("/rest")
public class DescriptionService {

	private DescriptionBO descriptionBO;

	public DescriptionService() {
		descriptionBO = (DescriptionBO) SpringApplicationContext.getBean("descriptionBO");
	}

	@GET
	@Path("/description")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listDescription() {

		List<Description> list = new ArrayList<>();
		try {
			list = descriptionBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@PUT
	@Path("/description")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listDescriptionByParameter(Description description) {

		List<Description> list = new ArrayList<>();
		try {
			list = descriptionBO.listByParameter(description);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@PUT
	@Path("/description/getbydescription")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDescriptionByDescription(Map<String, String> request) {
		Description ret = new Description();
		try {
			ret = descriptionBO.getByDescription(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@PUT
	@Path("/description/getbyid")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDescriptionById(Long id) {
		Description ret = new Description();
		try {
			ret = descriptionBO.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@POST
	@Path("/description")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response saveDescription(Description description) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = descriptionBO.save(description);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/description")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteDescription(Description description) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = descriptionBO.delete(description.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
}
