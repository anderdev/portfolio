package com.mconnti.cashtrack.business.impl;

import java.util.Calendar;
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

	private void validateCreditCard(CreditCard creditCard) throws Exception {
		if (creditCard.getName() == null || creditCard.getName().isEmpty()) {
			throw new Exception(MessageFactory.getMessage("lb_name_empty", creditCard.getUser().getLanguage()));
		}
		if (creditCard.getExpire() == null || creditCard.getExpire().isEmpty()) {
			throw new Exception(MessageFactory.getMessage("lb_expire_empty", creditCard.getUser().getLanguage()));
		} else {
			Calendar calendar = Calendar.getInstance();
			Integer currentYear = calendar.get(Calendar.YEAR);
			String strDate = "01/" + creditCard.getExpire();

			String[] tmpDate = strDate.split("/");

			if (tmpDate.length == 3) {
				if (Integer.valueOf(tmpDate[1]) > 12) {
					throw new Exception(MessageFactory.getMessage("lb_month_wrong_format", creditCard.getUser().getLanguage()));
				} else if (Integer.valueOf(tmpDate[2]) < currentYear) {
					throw new Exception(MessageFactory.getMessage("lb_year_wrong_format", creditCard.getUser().getLanguage()));
				}
			} else {
				throw new Exception(MessageFactory.getMessage("lb_expire_wrong_format", creditCard.getUser().getLanguage()));
			}
		}
		if(creditCard.getPayday() == null || creditCard.getPayday() < 1 || creditCard.getPayday() > 31){
			throw new Exception(MessageFactory.getMessage("lb_payday_wrong_format", creditCard.getUser().getLanguage()));
		}
		if(creditCard.getLastDayToBuy() == null || creditCard.getLastDayToBuy() < 1 || creditCard.getLastDayToBuy() > 31){
			throw new Exception(MessageFactory.getMessage("lb_lastdaytobuy_wrong_format", creditCard.getUser().getLanguage()));
		}
	}

	@Override
	@Transactional
	public MessageReturn save(CreditCard creditCard) {
		MessageReturn libReturn = new MessageReturn();
		User user = getUser(creditCard);
		if (user != null) {
			try {
				validateCreditCard(creditCard);
				if (creditCard.getExpire() != null) {
					creditCard.setExpireDate(Utils.getCreditCardExpiredDate(creditCard.getExpire()).getTime());
				} else {
					creditCard.setExpireDate(Utils.getCreditCardExpiredDate(Utils.dateToStringCreditCard(creditCard.getExpireDate())).getTime());
				}

				creditCard.setUser(user);
				saveGeneric(creditCard);
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setMessage(e.getMessage());
			}

			if (libReturn.getMessage() == null && creditCard.getId() == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_creditCard_saved", user.getLanguage()));
				libReturn.setCreditCard(creditCard);
			} else if (libReturn.getMessage() == null && creditCard.getId() != null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_creditCard_updated", user.getLanguage()));
				libReturn.setCreditCard(creditCard);
			}
		} else {
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
				libReturn.setMessage(MessageFactory.getMessage("lb_creditCard_not_found", "en"));
			} else {
				String locale = creditCard.getUser().getLanguage();
				remove(creditCard);
				libReturn.setMessage(MessageFactory.getMessage("lb_creditCard_deleted", locale));
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
	public List<CreditCard> listByParameter(String userId) throws Exception {
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put(" where x.user.id = ", userId);

		return list(CreditCard.class, queryParams, "x.name");
	}

}
