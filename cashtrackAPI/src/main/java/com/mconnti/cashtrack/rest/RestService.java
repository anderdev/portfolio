package com.mconnti.cashtrack.rest;

import javax.ws.rs.Path;

@Path("/rest")
public class RestService {
//	private UserBO userBO;
//	private CurrencyBO currencyBO;
//	private TypeAccountBO typeAccountBO;
//	private TypeClosureBO typeClosureBO;
//	private DescriptionBO descriptionBO;
//	private CreditCardBO creditCardBO;
//	private ConfigBO configBO;
//	private RegisterBO registerBO;
//	private ClosureBO closureBO;
//	private PlanningBO planningBO;
//	private PlanningGroupBO planningGroupBO;
//	private RoleBO roleBO;

	public RestService() {
//		userBO = (UserBO) SpringApplicationContext.getBean("userBO");
//		currencyBO = (CurrencyBO) SpringApplicationContext.getBean("currencyBO");
//		typeAccountBO = (TypeAccountBO) SpringApplicationContext.getBean("typeAccountBO");
//		typeClosureBO = (TypeClosureBO) SpringApplicationContext.getBean("typeClosureBO");
//		descriptionBO = (DescriptionBO) SpringApplicationContext.getBean("descriptionBO");
//		creditCardBO = (CreditCardBO) SpringApplicationContext.getBean("creditCardBO");
//		configBO = (ConfigBO) SpringApplicationContext.getBean("configBO");
//		registerBO = (RegisterBO) SpringApplicationContext.getBean("registerBO");
//		closureBO = (ClosureBO) SpringApplicationContext.getBean("closureBO");
//		planningBO = (PlanningBO) SpringApplicationContext.getBean("planningBO");
//		planningGroupBO = (PlanningGroupBO) SpringApplicationContext.getBean("planningGroupBO");
//		roleBO = (RoleBO) SpringApplicationContext.getBean("roleBO");
		
	}

//	// USER AREA
//	
//	@GET
//	@Path("/user")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response listUser() {
//
//		List<User> list = new ArrayList<>();
//		try {
//			list = userBO.list();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(list).build();
//	}
//	
//	@PUT
//	@Path("/user")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response listUserSuperUser(User user) {
//		Map<String, String> queryParams = new LinkedHashMap<String, String>();
//		queryParams.put(" where x.superUser", "= " + user.getId());
//		queryParams.put(" or x.id", "= " + user.getId());
//
//		List<User> list = new ArrayList<>();
//		try {
//			list = userBO.list(User.class, queryParams, "x.name asc");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(list).build();
//	}
//
//	@POST
//	@Path("/user")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response saveUser(User user) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = userBO.save(user);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//
//	@DELETE
//	@Path("/user")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response deleteUser(User user) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = userBO.delete(user.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//	
//	@PUT
//	@Path("/user/login")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response login(User user) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = userBO.login(user);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(ret.getMessage());
//		return Response.status(200).entity(ret).build();
//	}
//	
//	@PUT
//	@Path("/user/emailcheck")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response emailCheck(User user) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			Map<String, String> queryParams = new LinkedHashMap<String, String>();
//			queryParams.put(" where x.email", "= '" + user.getEmail()+"'");
//			
//			ret.setUser(userBO.findByParameter(User.class, queryParams));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//	
//	@PUT
//	@Path("/user/usernamecheck")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response usernameCheck(User user) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			Map<String, String> queryParams = new LinkedHashMap<String, String>();
//			queryParams.put(" where x.username", "= '" + user.getUsername()+"'");
//			
//			ret.setUser(userBO.findByParameter(User.class, queryParams));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}

	// CURRENCY AREA

//	@PUT
//	@Path("/currency")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response listCurrency(Currency currency) {
//
//		List<Currency> list = new ArrayList<>();
//		try {
//			list = currencyBO.list(currency);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(list).build();
//	}
//
//	@POST
//	@Path("/currency")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response saveCurrency(Currency currency) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = currencyBO.save(currency);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//
//	@DELETE
//	@Path("/currency")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response deleteCurrency(Currency currency) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = currencyBO.delete(currency.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}

	// TYPE ACCOUNT AREA

//	@PUT
//	@Path("/typeaccount")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response listAccount(TypeAccount typeAccount) {
//
//		List<TypeAccount> list = new ArrayList<>();
//		try {
//			list = typeAccountBO.list(typeAccount);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(list).build();
//	}
//	
//	@PUT
//	@Path("/typeaccount/getbydescription")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response getAccountByDescription(Description description) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = typeAccountBO.getByDescription(description);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//
//	@POST
//	@Path("/typeaccount")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response saveAccount(TypeAccount account) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = typeAccountBO.save(account);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//
//	@DELETE
//	@Path("/typeaccount")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response deleteAccount(TypeAccount account) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = typeAccountBO.delete(account.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
	
	// TYPE_CLOSURE AREA

//	@PUT
//	@Path("/typeclosure")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response listTypeClosure(TypeClosure typeClosure) {
//
//		List<TypeClosure> list = new ArrayList<>();
//		try {
//			list = typeClosureBO.list(typeClosure);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(list).build();
//	}
//
//	@POST
//	@Path("/typeclosure")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response saveTypeClosure(TypeClosure typeClosure) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = typeClosureBO.save(typeClosure);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//
//	@DELETE
//	@Path("/typeclosure")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response deleteTypeClosure(TypeClosure typeClosure) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = typeClosureBO.delete(typeClosure.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}

	// DESCRIPTION AREA

//	@GET
//	@Path("/description")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response listDescription() {
//
//		List<Description> list = new ArrayList<>();
//		try {
//			list = descriptionBO.list();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(list).build();
//	}
//	
//	@PUT
//	@Path("/description")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response listDescriptionByParameter(Description description) {
//
//		List<Description> list = new ArrayList<>();
//		try {
//			list = descriptionBO.listByParameter(description);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(list).build();
//	}
//	
//	@PUT
//	@Path("/description/getbydescription")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response getDescriptionByDescription(Map<String, String> request) {
//		Description ret = new Description();
//		try {
//			ret = descriptionBO.getByDescription(request);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//	
//	@PUT
//	@Path("/description/getbyid")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response getDescriptionById(Long id) {
//		Description ret = new Description();
//		try {
//			ret = descriptionBO.getById(id);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//
//	@POST
//	@Path("/description")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response saveDescription(Description description) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = descriptionBO.save(description);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//
//	@DELETE
//	@Path("/description")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response deleteDescription(Description description) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = descriptionBO.delete(description.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}

	// CREDIT_CARD AREA

//	@GET
//	@Path("/creditcard")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response listCreditCard() {
//
//		List<CreditCard> list = new ArrayList<>();
//		try {
//			list = creditCardBO.list();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(list).build();
//	}
//	
//	@PUT
//	@Path("/creditcard")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response listCreditCardByParameter(CreditCard creditCard) {
//
//		List<CreditCard> list = new ArrayList<>();
//		try {
//			list = creditCardBO.listByParameter(creditCard);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(list).build();
//	}
//
//	@POST
//	@Path("/creditcard")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response saveCreditCard(CreditCard creditCard) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = creditCardBO.save(creditCard);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//
//	@DELETE
//	@Path("/creditcard")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response deleteCreditCard(CreditCard creditCard) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = creditCardBO.delete(creditCard.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}

	// CONFIG AREA

//	@GET
//	@Path("/config")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response listConfig() {
//
//		List<Config> list = new ArrayList<>();
//		try {
//			list = configBO.list();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(list).build();
//	}
//
//	@PUT
//	@Path("/config/save")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response saveConfig(Config config) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = configBO.save(config);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//
//	@DELETE
//	@Path("/config")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response deleteConfig(Config config) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = configBO.delete(config.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}

	// REGISTER AREA

//	@PUT
//	@Path("/register/all")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response listRegister(Register register) {
//
//		List<Register> list = new ArrayList<>();
//		try {
//			list = registerBO.list(register);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(list).build();
//	}
//	
//	@PUT
//	@Path("/register")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response listRegisterByParameter(Register register) {
//
//		List<Register> list = new ArrayList<>();
//		try {
//			list = registerBO.listByParameter(register);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(list).build();
//	}
//	
//	@PUT
//	@Path("/register/getbydescription")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response getRegisterByDescription(Map<String, String> request) {
//
//		Register register = new Register();
//		try {
//			register = registerBO.getByDescription(request);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(register).build();
//	}
//
//	@POST
//	@Path("/register")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response saveRegister(Register register) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = registerBO.save(register);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//	
//	@DELETE
//	@Path("/register")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response deleteRegister(Register register) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = registerBO.delete(register.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}

	// CLOSURE AREA
//	@PUT
//	@Path("/closure/all")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response listClosure(Closure closure) {
//
//		List<Closure> list = new ArrayList<>();
//		try {
//			list = closureBO.list(closure.getUser());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(list).build();
//	}
//	
//	@PUT
//	@Path("/closure/list")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response listClosureByParameter(Closure closure) {
//
//		List<Closure> list = new ArrayList<>();
//		try {
//			list = closureBO.listByParameter(closure);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(list).build();
//	}
//
//	@PUT
//	@Path("/closure")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response executeClosure(Closure closure) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = closureBO.getValuesToClose(closure);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//
//	@POST
//	@Path("/closure")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response saveClosure(Closure closure) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = closureBO.save(closure);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//
//	@DELETE
//	@Path("/closure")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response deleteClosure(Closure closure) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = closureBO.delete(closure.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}

	// PLANNING AREA

//	@PUT
//	@Path("/planning/list")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response listPlanning(Planning planning) {
//		List<Planning> list = new ArrayList<>();
//		try {
//			list = planningBO.list(planning);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(list).build();
//	}
//	
//	@PUT
//	@Path("/planning/selected")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response getSelected(Planning planning) {
//		try {
//			planning = planningBO.getSelectedPlanning(planning.getUser());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(planning).build();
//	}
//
//	@POST
//	@Path("/planning")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response savePlanning(Planning planning) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = planningBO.save(planning);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//	
//	@POST
//	@Path("/planninggroup")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response savePlanningGroup(PlanningGroup planningGroup) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = planningBO.saveGroup(planningGroup);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//	
//	@POST
//	@Path("/planningitem")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response savePlanningItem(PlanningItem planningItem) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = planningBO.saveItem(planningItem);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//
//	@DELETE
//	@Path("/planninggroup")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response deletePlanning(PlanningGroup planningGroup) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = planningGroupBO.delete(planningGroup.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}

	// ROLE AREA

//	@GET
//	@Path("/role")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response listRole() {
//
//		List<Role> list = new ArrayList<>();
//		try {
//			list = roleBO.list();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return Response.status(200).entity(list).build();
//	}
//
//	@POST
//	@Path("/role")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response saveRole(Role role) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = roleBO.save(role);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
//
//	@DELETE
//	@Path("/role")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response deleteRole(Role role) {
//		MessageReturn ret = new MessageReturn();
//		try {
//			ret = roleBO.delete(role.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return Response.status(200).entity(ret).build();
//	}
}
