package com.mconnti.cashtrack.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mconnti.cashtrack.business.RoleBO;
import com.mconnti.cashtrack.context.SpringApplicationContext;
import com.mconnti.cashtrack.entity.Role;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Path("/rest")
public class RoleService {

	private RoleBO roleBO;

	public RoleService() {
		roleBO = (RoleBO) SpringApplicationContext.getBean("roleBO");
	}

	@GET
	@Path("/role")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listRole() {

		List<Role> list = new ArrayList<>();
		try {
			list = roleBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/role")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response saveRole(Role role) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = roleBO.save(role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/role")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteRole(Role role) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = roleBO.delete(role.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
}
