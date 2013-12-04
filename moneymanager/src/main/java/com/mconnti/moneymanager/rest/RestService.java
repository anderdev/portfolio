package com.mconnti.moneymanager.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.mconnti.moneymanager.business.CityBO;
import com.mconnti.moneymanager.business.CountryBO;
import com.mconnti.moneymanager.business.StateBO;
import com.mconnti.moneymanager.business.UserBO;
import com.mconnti.moneymanager.context.SpringApplicationContext;
import com.mconnti.moneymanager.entity.City;
import com.mconnti.moneymanager.entity.Country;
import com.mconnti.moneymanager.entity.State;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Path("/rest")
public class RestService {
	private CountryBO countryBO;
	private StateBO stateBO;
	private CityBO cityBO;
	private UserBO userBO;

	public RestService() {
		countryBO = (CountryBO) SpringApplicationContext.getBean("countryBO");
		stateBO = (StateBO) SpringApplicationContext.getBean("stateBO");
		cityBO = (CityBO) SpringApplicationContext.getBean("cityBO");
		userBO = (UserBO) SpringApplicationContext.getBean("userBO");
	}
	
	//COUNTRY AREA

	@GET
	@Path("/country")
	@Produces({ "application/json" })
	public Response listCountry() {

		List<Country> list = new ArrayList<>();
		try {
			list = countryBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/country")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response saveCountry(Country country) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = countryBO.save(country);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/country")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response deleteCountry(Country country) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = countryBO.delete(country.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
	
	//STATE AREA
	
	@GET
	@Path("/state")
	@Produces({ "application/json" })
	public Response listState() {

		List<State> state = new ArrayList<>();
		try {
			state = stateBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(state).build();
	}
	
	@POST
	@Path("/state")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response saveState(State state) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = stateBO.save(state);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/state")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response deleteState(State state) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = stateBO.delete(state.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
	
	//CITY AREA
	
	@GET
	@Path("/city")
	@Produces({ "application/json" })
	public Response listCity() {

		List<City> list = new ArrayList<>();
		try {
			list = cityBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/city")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response saveCity(City city) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = cityBO.save(city);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/city")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response deleteCity(City city) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = cityBO.delete(city.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
	
	//USER AREA
	@GET
	@Path("/user")
	@Produces({ "application/json" })
	public Response listUser() {

		List<User> list = new ArrayList<>();
		try {
			list = userBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/user")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response saveUser(User user) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = userBO.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/user")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response deleteUser(User user) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = userBO.delete(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
}
