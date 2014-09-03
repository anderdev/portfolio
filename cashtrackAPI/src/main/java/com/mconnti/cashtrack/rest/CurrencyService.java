package com.mconnti.cashtrack.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mconnti.cashtrack.business.CurrencyBO;
import com.mconnti.cashtrack.context.SpringApplicationContext;
import com.mconnti.cashtrack.entity.Currency;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

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
	
	@GET
	@Path("/currency/{locale}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response listCurrency(@PathParam("locale") String locale) {

		List<Currency> list = new ArrayList<>();
		try {
			list = currencyBO.listByLocale(locale);
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
