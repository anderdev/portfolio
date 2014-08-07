package com.mconnti.cashtrack.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mconnti.cashtrack.business.RegisterBO;
import com.mconnti.cashtrack.context.SpringApplicationContext;
import com.mconnti.cashtrack.entity.Register;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Path("/rest")
public class RegisterService {

	private RegisterBO registerBO;

	public RegisterService() {
		registerBO = (RegisterBO) SpringApplicationContext.getBean("registerBO");
	}

	@PUT
	@Path("/register/all")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listRegister(Register register) {

		List<Register> list = new ArrayList<>();
		try {
			list = registerBO.list(register);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@PUT
	@Path("/register")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listRegisterByParameter(Register register) {

		List<Register> list = new ArrayList<>();
		try {
			list = registerBO.listByParameter(register);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@PUT
	@Path("/register/getbydescription")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRegisterByDescription(Map<String, String> request) {

		Register register = new Register();
		try {
			register = registerBO.getByDescription(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(register).build();
	}

	@POST
	@Path("/register")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response saveRegister(Register register) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = registerBO.save(register);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/register")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteRegister(Register register) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = registerBO.delete(register.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
}
