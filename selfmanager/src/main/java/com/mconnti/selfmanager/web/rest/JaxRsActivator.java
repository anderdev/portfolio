package com.mconnti.selfmanager.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import com.mconnti.selfmanager.business.impl.CountryBOImpl;
import com.mconnti.selfmanager.entity.Country;

@Path("/rest")
public class JaxRsActivator extends Application {
	
//	public Set<Class<?>> getClasses() {
//        return new HashSet<Class<?>>(Arrays.asList(CountryBOImpl.class));
//    }
	
//	@Inject
	private CountryBOImpl countryBO;
	
	@GET
	@Path("/country")
	public Response printMessage() {

		
		List<Country> list = new ArrayList<>();
		try {
			list = countryBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();

	}
//
//	private Set<Object> singletons = new HashSet<Object>();
//	private Set<Class<?>> classes = new HashSet<Class<?>>();
//
//	public JaxRsActivator() {
//		singletons.add(countryBO);
//	}
//
//	@Override
//	public Set<Class<?>> getClasses() {
//		return classes;
//	}
//
//	@Override
//	public Set<Object> getSingletons() {
//		return singletons;
//	}
}
