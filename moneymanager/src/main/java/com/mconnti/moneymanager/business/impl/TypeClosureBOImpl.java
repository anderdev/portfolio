package com.mconnti.moneymanager.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.TypeClosureBO;
import com.mconnti.moneymanager.entity.TypeClosure;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.TypeClosureDAO;
import com.mconnti.moneymanager.utils.MessageFactory;

public class TypeClosureBOImpl extends GenericBOImpl<TypeClosure> implements TypeClosureBO {

	@Autowired
	private TypeClosureDAO typeClosureDAO;

	@Override
	@Transactional
	public MessageReturn save(TypeClosure typeClosure) {
		MessageReturn libReturn = new MessageReturn();
		TypeClosure c = null;
		try {
			c = new TypeClosure();
			c.setId(typeClosure.getId());
			c.setPeriod(typeClosure.getPeriod());
			c.setType(typeClosure.getType());
			c.setLocale(typeClosure.getLocale());
			saveGeneric(c);
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setTypeClosure(c);
			libReturn.setMessage(e.getMessage());
		}
		if (libReturn.getMessage() == null && typeClosure.getId() == null) {
			libReturn.setMessage( MessageFactory.getMessage("lb_typeClosure_saved", typeClosure.getLocale()));
			libReturn.setTypeClosure(c);
		} else if (libReturn.getMessage() == null && typeClosure.getId() != null) {
			libReturn.setMessage( MessageFactory.getMessage("lb_typeClosure_updated", typeClosure.getLocale()));
			libReturn.setTypeClosure(c);
		}
		return libReturn;
	}

	public List<TypeClosure> list() throws Exception {
		return list(TypeClosure.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		TypeClosure typeClosure = null;
		try {
			typeClosure = findById(TypeClosure.class, id);
			if (typeClosure == null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_typeClosure_not_found", "en"));
			} else {
				String locale = typeClosure.getLocale();
				remove(typeClosure);
				libReturn.setMessage( MessageFactory.getMessage("lb_typeClosure_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public TypeClosure getById(Long id) {
		try {
			return findById(TypeClosure.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
