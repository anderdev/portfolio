package com.mconnti.moneymanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mconnti.moneymanager.business.RegisterBO;
import com.mconnti.moneymanager.entity.Register;
import com.mconnti.moneymanager.entity.TypeAccount;
import com.mconnti.moneymanager.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class RegisterTest {
	
	@Autowired
	private RegisterBO registerBO;
	
	private Register register;
	
	public RegisterTest() {
		register = new Register();
	}
	
	private void createTypeAccount(final Long type){
		TypeAccount typeAccount = new TypeAccount();
		typeAccount.setId(type);
		register.setTypeAccount(typeAccount);
	}
	
	private void createUser(final Long id){
		User user = new User();
		user.setId(id);
		register.setUser(user);
	}
	
	@Test
	public void listIncome(){
		
		List<Register> incomes = new ArrayList<>();
		
		createTypeAccount(1L);
		createUser(1L);
		
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put(" where "," 1=1 ");
		queryParams.put(" and x.user.id = ", register.getUser().getId()+"");
		queryParams.put(" and x.typeAccount.id = ", register.getTypeAccount().getId()+ "");
		
		try {
			incomes = registerBO.list(Register.class, queryParams, "x. date");
			for (Register register : incomes) {
				System.out.println("ID: "+register.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public RegisterBO getRegisterBO() {
		return registerBO;
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}
}
