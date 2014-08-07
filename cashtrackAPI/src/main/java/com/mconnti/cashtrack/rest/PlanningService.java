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

import com.mconnti.cashtrack.business.PlanningBO;
import com.mconnti.cashtrack.business.PlanningGroupBO;
import com.mconnti.cashtrack.context.SpringApplicationContext;
import com.mconnti.cashtrack.entity.Planning;
import com.mconnti.cashtrack.entity.PlanningGroup;
import com.mconnti.cashtrack.entity.PlanningItem;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Path("/rest")
public class PlanningService {

	private PlanningBO planningBO;
	
	private PlanningGroupBO planningGroupBO;

	public PlanningService() {
		planningBO = (PlanningBO) SpringApplicationContext.getBean("planningBO");
		planningGroupBO = (PlanningGroupBO) SpringApplicationContext.getBean("planningGroupBO");
	}

	@PUT
	@Path("/planning/list")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listPlanning(Planning planning) {
		List<Planning> list = new ArrayList<>();
		try {
			list = planningBO.list(planning);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@PUT
	@Path("/planning/selected")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSelected(Planning planning) {
		try {
			planning = planningBO.getSelectedPlanning(planning.getUser());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(planning).build();
	}

	@POST
	@Path("/planning")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response savePlanning(Planning planning) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = planningBO.save(planning);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@POST
	@Path("/planninggroup")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response savePlanningGroup(PlanningGroup planningGroup) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = planningBO.saveGroup(planningGroup);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@POST
	@Path("/planningitem")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response savePlanningItem(PlanningItem planningItem) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = planningBO.saveItem(planningItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/planninggroup")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deletePlanning(PlanningGroup planningGroup) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = planningGroupBO.delete(planningGroup.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
}
