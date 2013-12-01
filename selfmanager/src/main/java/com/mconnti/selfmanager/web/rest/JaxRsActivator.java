package com.mconnti.selfmanager.web.rest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.mconnti.selfmanager.business.impl.CountryBOImpl;

@ApplicationPath("/rest")
public class JaxRsActivator extends Application {
	
	public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(CountryBOImpl.class));
    }
	
//	@Inject
//	private CountryBOImpl countryBO;
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
