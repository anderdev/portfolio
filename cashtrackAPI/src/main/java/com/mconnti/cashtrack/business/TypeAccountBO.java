package com.mconnti.cashtrack.business;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mconnti.cashtrack.entity.Description;
import com.mconnti.cashtrack.entity.TypeAccount;
import com.mconnti.cashtrack.entity.User;
import com.mconnti.cashtrack.entity.xml.MessageReturn;

@Service("typeAccountBO")
public interface TypeAccountBO extends GenericBO<TypeAccount>{
	
	public List<TypeAccount> list(final TypeAccount typeAccount) throws Exception;
	
	public MessageReturn save(final TypeAccount typeAccount) throws Exception;
	
	public MessageReturn delete (Long id);
	
	public TypeAccount getById(Long id);
	
	public MessageReturn getByDescription(Description description);
	
	public Map<String, Object> getTypeAccountDescriptionByUserAndDescription(User user, String description, Boolean withTypeAccount);
	
}

