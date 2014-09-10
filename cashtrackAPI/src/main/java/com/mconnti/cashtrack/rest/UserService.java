package com.mconnti.cashtrack.rest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mconnti.cashtrack.business.UserBO;
import com.mconnti.cashtrack.context.SpringApplicationContext;
import com.mconnti.cashtrack.entity.User;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Path("/rest")
public class UserService {

	private UserBO userBO;

	public UserService() {
		userBO = (UserBO) SpringApplicationContext.getBean("userBO");
	}

	@GET
	@Path("/user")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response list() {

		List<User> list = new ArrayList<>();
		try {
			list = userBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@PUT
	@Path("/user")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listSuperUser(User user) {
		Map<String, String> queryParams = new LinkedHashMap<String, String>();
		queryParams.put(" where x.superUser", "= " + user.getId());
		queryParams.put(" or x.id", "= " + user.getId());

		List<User> list = new ArrayList<>();
		try {
			list = userBO.list(User.class, queryParams, "x.name asc");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/user")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response save(User user) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = userBO.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/user/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response delete(@PathParam("id") Long id) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = userBO.delete(id);
		} catch (Exception e) {
			ret.setMessage(e.getMessage());
		}
		return Response.status(200).entity(ret).build();
	}

	@POST
	@Path("/user/login")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = userBO.login(username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(ret.getMessage());
		return Response.status(200).entity(ret).build();
	}

	@PUT
	@Path("/user/emailcheck")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response emailCheck(User user) {
		MessageReturn ret = new MessageReturn();
		try {
			Map<String, String> queryParams = new LinkedHashMap<String, String>();
			queryParams.put(" where x.email", "= '" + user.getEmail() + "'");

			ret.setUser(userBO.findByParameter(User.class, queryParams));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@PUT
	@Path("/user/usernamecheck")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response usernameCheck(User user) {
		MessageReturn ret = new MessageReturn();
		try {
			Map<String, String> queryParams = new LinkedHashMap<String, String>();
			queryParams.put(" where x.username", "= '" + user.getUsername() + "'");

			ret.setUser(userBO.findByParameter(User.class, queryParams));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
	
	@PUT
	@Path("/user/getbyusername")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getByUsername(String username) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = userBO.getByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
}
