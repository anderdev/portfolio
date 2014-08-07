package com.mconnti.cashtrack.business.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mconnti.cashtrack.business.RoleBO;
import com.mconnti.cashtrack.entity.Role;
import com.mconnti.cashtrack.entity.xml.MessageReturn;
import com.mconnti.cashtrack.utils.MessageFactory;

public class RoleBOImpl extends GenericBOImpl<Role> implements RoleBO {

	
	@Override
	@Transactional
	public MessageReturn save(Role role) {
		MessageReturn libReturn = new MessageReturn();
			try {
				saveGeneric(role);
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setRole(role);
				libReturn.setMessage(e.getMessage());
			}
			if (libReturn.getMessage() == null && role.getId() == null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_role_saved", "en"));
				libReturn.setRole(role);
			} else if (libReturn.getMessage() == null && role.getId() != null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_role_updated", "en"));
				libReturn.setRole(role);
			}
		
		return libReturn;
	}

	public List<Role> list() throws Exception {
		return list(Role.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		Role role = null;
		try {
			role = findById(Role.class, id);
			if (role == null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_role_not_found", "en"));
			} else {
				remove(role);
				libReturn.setMessage( MessageFactory.getMessage("lb_role_deleted", "en"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public Role getById(Long id) {
		try {
			return findById(Role.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
