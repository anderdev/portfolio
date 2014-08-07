package com.mconnti.cashtrack.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mconnti.cashtrack.business.ConfigBO;
import com.mconnti.cashtrack.context.SpringApplicationContext;
import com.mconnti.cashtrack.entity.Config;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Path("/rest")
public class ConfigService {

	private ConfigBO configBO;

	public ConfigService() {
		configBO = (ConfigBO) SpringApplicationContext.getBean("configBO");
	}

	@GET
	@Path("/config")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listConfig() {

		List<Config> list = new ArrayList<>();
		try {
			list = configBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@PUT
	@Path("/config/save")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response saveConfig(Config config) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = configBO.save(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/config")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteConfig(Config config) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = configBO.delete(config.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
}
