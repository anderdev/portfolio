package com.mconnti.moneymanager.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mconnti.moneymanager.business.CurrencyBO;
import com.mconnti.moneymanager.context.SpringApplicationContext;
import com.mconnti.moneymanager.entity.Currency;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Path("/rest")
public class CurrencyService {
	
	private CurrencyBO currencyBO;
	
	public CurrencyService() {
		currencyBO = (CurrencyBO) SpringApplicationContext.getBean("currencyBO");
	}
	
	@GET
	@Path("/currency")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response list() {

		List<Currency> list = new ArrayList<>();
		try {
			list = currencyBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}
	
	@PUT
	@Path("/currency")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listCurrency(Currency currency) {

		List<Currency> list = new ArrayList<>();
		try {
			list = currencyBO.list(currency);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/currency")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response saveCurrency(Currency currency) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = currencyBO.save(currency);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/currency")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteCurrency(Currency currency) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = currencyBO.delete(currency.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
}
