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
import com.mconnti.moneymanager.business.ConfigBO;
import com.mconnti.moneymanager.business.CountryBO;
import com.mconnti.moneymanager.business.CreditBO;
import com.mconnti.moneymanager.business.CreditCardBO;
import com.mconnti.moneymanager.business.CurrencyBO;
import com.mconnti.moneymanager.business.DebitBO;
import com.mconnti.moneymanager.business.DescriptionBO;
import com.mconnti.moneymanager.business.StateBO;
import com.mconnti.moneymanager.business.TypeAccountBO;
import com.mconnti.moneymanager.business.TypeClosureBO;
import com.mconnti.moneymanager.business.UserBO;
import com.mconnti.moneymanager.context.SpringApplicationContext;
import com.mconnti.moneymanager.entity.City;
import com.mconnti.moneymanager.entity.Config;
import com.mconnti.moneymanager.entity.Country;
import com.mconnti.moneymanager.entity.Credit;
import com.mconnti.moneymanager.entity.CreditCard;
import com.mconnti.moneymanager.entity.Currency;
import com.mconnti.moneymanager.entity.Debit;
import com.mconnti.moneymanager.entity.Description;
import com.mconnti.moneymanager.entity.State;
import com.mconnti.moneymanager.entity.TypeAccount;
import com.mconnti.moneymanager.entity.TypeClosure;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;

@Path("/rest")
public class RestService {
	private CountryBO countryBO;
	private StateBO stateBO;
	private CityBO cityBO;
	private UserBO userBO;
	private CurrencyBO currencyBO;
	private TypeAccountBO typeAccountBO;
	private TypeClosureBO typeClosureBO;
	private DescriptionBO descriptionBO;
	private CreditCardBO creditCardBO;
	private CreditBO creditBO;
	private ConfigBO configBO;
	private DebitBO debitBO;

	public RestService() {
		countryBO = (CountryBO) SpringApplicationContext.getBean("countryBO");
		stateBO = (StateBO) SpringApplicationContext.getBean("stateBO");
		cityBO = (CityBO) SpringApplicationContext.getBean("cityBO");
		userBO = (UserBO) SpringApplicationContext.getBean("userBO");
		currencyBO = (CurrencyBO) SpringApplicationContext.getBean("currencyBO");
		typeAccountBO = (TypeAccountBO) SpringApplicationContext.getBean("typeAccountBO");
		typeClosureBO = (TypeClosureBO) SpringApplicationContext.getBean("typeClosureBO");
		descriptionBO = (DescriptionBO) SpringApplicationContext.getBean("descriptionBO");
		creditCardBO = (CreditCardBO) SpringApplicationContext.getBean("creditCardBO");
		configBO = (ConfigBO) SpringApplicationContext.getBean("configBO");
		creditBO = (CreditBO) SpringApplicationContext.getBean("creditBO");
		debitBO = (DebitBO) SpringApplicationContext.getBean("debitBO");
	}

	// COUNTRY AREA

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

	// STATE AREA

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

	// CITY AREA

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

	// USER AREA
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

	// CURRENCY AREA

	@GET
	@Path("/currency")
	@Produces({ "application/json" })
	public Response listCurrency() {

		List<Currency> list = new ArrayList<>();
		try {
			list = currencyBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/currency")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
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
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response deleteCurrency(Currency currency) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = currencyBO.delete(currency.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	// TYPE ACCOUNT AREA

	@GET
	@Path("/typeaccount")
	@Produces({ "application/json" })
	public Response listAccount() {

		List<TypeAccount> list = new ArrayList<>();
		try {
			list = typeAccountBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/typeaccount")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response saveAccount(TypeAccount account) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = typeAccountBO.save(account);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/typeaccount")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response deleteAccount(TypeAccount account) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = typeAccountBO.delete(account.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	// TYPE_CLOSURE AREA

	@GET
	@Path("/typeclosure")
	@Produces({ "application/json" })
	public Response listTypeClosure() {

		List<TypeClosure> list = new ArrayList<>();
		try {
			list = typeClosureBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/typeclosure")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response saveTypeClosure(TypeClosure typeClosure) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = typeClosureBO.save(typeClosure);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/typeclosure")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response deleteTypeClosure(TypeClosure typeClosure) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = typeClosureBO.delete(typeClosure.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	// DESCRIPTION AREA

	@GET
	@Path("/description")
	@Produces({ "application/json" })
	public Response listDescription() {

		List<Description> list = new ArrayList<>();
		try {
			list = descriptionBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/description")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response saveDescription(Description description) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = descriptionBO.save(description);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/description")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response deleteDescription(Description description) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = descriptionBO.delete(description.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	// CREDIT_CARD AREA

	@GET
	@Path("/creditcard")
	@Produces({ "application/json" })
	public Response listCreditCard() {

		List<CreditCard> list = new ArrayList<>();
		try {
			list = creditCardBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/creditcard")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response saveCreditCard(CreditCard creditCard) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = creditCardBO.save(creditCard);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/creditcard")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response deleteCreditCard(CreditCard creditCard) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = creditCardBO.delete(creditCard.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	// CONFIG AREA

	@GET
	@Path("/config")
	@Produces({ "application/json" })
	public Response listConfig() {

		List<Config> list = new ArrayList<>();
		try {
			list = configBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/config")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
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
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response deleteConfig(Config config) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = configBO.delete(config.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	// CREDIT AREA

	@GET
	@Path("/credit")
	@Produces({ "application/json" })
	public Response listCredit() {

		List<Credit> list = new ArrayList<>();
		try {
			list = creditBO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("/credit")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response saveCredit(Credit credit) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = creditBO.save(credit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}

	@DELETE
	@Path("/credit")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response deleteCredit(Credit credit) {
		MessageReturn ret = new MessageReturn();
		try {
			ret = creditBO.delete(credit.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(ret).build();
	}
	
	// DEBIT AREA

		@GET
		@Path("/debit")
		@Produces({ "application/json" })
		public Response listDebit() {

			List<Debit> list = new ArrayList<>();
			try {
				list = debitBO.list();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return Response.status(200).entity(list).build();
		}

		@POST
		@Path("/debit")
		@Consumes({ "application/json" })
		@Produces({ "application/json" })
		public Response saveDebit(Debit debit) {
			MessageReturn ret = new MessageReturn();
			try {
				ret = debitBO.save(debit);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return Response.status(200).entity(ret).build();
		}

		@DELETE
		@Path("/debit")
		@Consumes({ "application/json" })
		@Produces({ "application/json" })
		public Response deleteDebit(Debit debit) {
			MessageReturn ret = new MessageReturn();
			try {
				ret = debitBO.delete(debit.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return Response.status(200).entity(ret).build();
		}
}
