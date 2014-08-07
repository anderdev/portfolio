package com.mconnti.cashtrack.business.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.cashtrack.business.CreditCardBO;
import com.mconnti.cashtrack.business.UserBO;
import com.mconnti.cashtrack.entity.CreditCard;
import com.mconnti.cashtrack.entity.User;
import com.mconnti.cashtrack.entity.xml.MessageReturn;
import com.mconnti.cashtrack.utils.MessageFactory;
import com.mconnti.cashtrack.utils.Utils;

public class CreditCardBOImpl extends GenericBOImpl<CreditCard> implements CreditCardBO {

	@Autowired
	private UserBO userBO;
	
	private User getUser(CreditCard creditCard) {
		return userBO.getSuperUser(creditCard.getUser());
	}
	
	@Override
	@Transactional
	public MessageReturn save(CreditCard creditCard) {
		MessageReturn libReturn = new MessageReturn();
		User user = getUser(creditCard);
		if(user != null){
			try {
				if(creditCard.getExpire() != null){
					creditCard.setExpireDate(Utils.getCreditCardExpiredDate(creditCard.getExpire()).getTime());
				}else{
					creditCard.setExpireDate(Utils.getCreditCardExpiredDate(creditCard.getExpire()).getTime());
				}
				
				creditCard.setUser(user);
				saveGeneric(creditCard);
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setCreditCard(creditCard);
				libReturn.setMessage(e.getMessage());
			}
			
			if (libReturn.getMessage() == null && creditCard.getId() == null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_creditCard_saved", user.getLanguage()));
				libReturn.setCreditCard(creditCard);
			} else if (libReturn.getMessage() == null && creditCard.getId() != null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_creditCard_updated", user.getLanguage()));
				libReturn.setCreditCard(creditCard);
			}
		}else{
			libReturn.setMessage(MessageFactory.getMessage("lb_user_not_found", "en"));
		}
		return libReturn;
	}

	public List<CreditCard> list() throws Exception {
		return list(CreditCard.class, null, null);
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		CreditCard creditCard = null;
		try {
			creditCard = findById(CreditCard.class, id);
			if (creditCard == null) {
				libReturn.setMessage( MessageFactory.getMessage("lb_creditCard_not_found", "en"));
			} else {
				String locale = creditCard.getUser().getLanguage();
				remove(creditCard);
				libReturn.setMessage( MessageFactory.getMessage("lb_creditCard_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public CreditCard getById(Long id) {
		try {
			return findById(CreditCard.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<CreditCard> listByParameter(CreditCard creditCard) throws Exception {
		User user = getUser(creditCard);
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put(" where x.user.id = ", user.getId()+"");

		return list(CreditCard.class, queryParams, "x.name");
	}

}
