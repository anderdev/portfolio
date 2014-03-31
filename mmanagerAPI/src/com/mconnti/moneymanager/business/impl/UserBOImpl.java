package com.mconnti.moneymanager.business.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.moneymanager.business.UserBO;
import com.mconnti.moneymanager.entity.City;
import com.mconnti.moneymanager.entity.Config;
import com.mconnti.moneymanager.entity.User;
import com.mconnti.moneymanager.entity.xml.MessageReturn;
import com.mconnti.moneymanager.persistence.CityDAO;
import com.mconnti.moneymanager.persistence.ConfigDAO;
import com.mconnti.moneymanager.persistence.UserDAO;
import com.mconnti.moneymanager.utils.Constants;
import com.mconnti.moneymanager.utils.Crypt;
import com.mconnti.moneymanager.utils.MessageFactory;
import com.mconnti.moneymanager.utils.Utils;

public class UserBOImpl extends GenericBOImpl<User> implements UserBO {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CityDAO cityDAO;
	
	@Autowired
	private ConfigDAO configDAO;

	private City getCity(User user) {
		try {
			return cityDAO.findById(City.class, user.getCity().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public MessageReturn save(User user) {
		MessageReturn libReturn = new MessageReturn();
		City city = getCity(user);
		if (city != null) {
			try {
				if (user.getId() == null) {
					if (user.getBirth() != null && !user.getBirth().isEmpty()) {
						user.setBirthDate(Utils.stringToDate(user.getBirth(), false));
					}
					user.setRegister(new Date());
					user.setExcluded(false);
					if (user.getPass() != null && !user.getPass().isEmpty()) {
						user.setPassword(user.getPass());
					}
					if(user.getDefaultPassword() != null && user.getDefaultPassword()){
						user.setPassword(Constants.DEFAULT_PASSWORD);
					}
				} else {
					if(user.getPassword() != null && !user.getPassword().isEmpty()){
						user.setPassword(Crypt.decrypt(Crypt.decrypt(user.getPassword())));
					}
				}
				saveGeneric(user);
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setUser(user);
				libReturn.setMessage(e.getMessage());
			}
			if (libReturn.getMessage() == null && user.getId() == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_user_saved", city.getState().getCountry().getLocale()));
				libReturn.setUser(user);
				try {
					sendEmail(user);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (libReturn.getMessage() == null && user.getId() != null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_user_updated", city.getState().getCountry().getLocale()));
				libReturn.setUser(user);
			}
		} else {
			libReturn.setMessage(MessageFactory.getMessage("lb_city_not_found", "en"));
		}

		return libReturn;
	}
	
	private void sendEmail(User user) throws Exception{
		if(user.getDefaultPassword() != null && user.getDefaultPassword()){
			String emailTo = user.getEmail();
			String emailFrom = user.getSuperUser().getEmail();
			String nameFrom = user.getSuperUser().getName();
			StringBuilder body = new StringBuilder();
			body.append("Olá ").append(user.getName()).append("\n");
			body.append("<b>").append(user.getSuperUser().getName()).append("</b>, lhe cadastrou no Momey Manager como coparticipante nos cadastros de créditos e despesas de seu controle financeiro.\n");
			body.append("No seu primeiro acesso será solicitado a frase secreta registrada pelo <b>").append(user.getSuperUser().getName()).append("</b>\n");
			body.append("Frase: <b>").append(user.getSuperUser().getSecretPhrase()).append("</b>\n");
			body.append("Para fazer o primeiro acesso você terá que se logar no sistema com:").append("\n");
			body.append("Usuário: <b>").append(user.getUsername()).append("</b>\n");
			body.append("Senha: <b>").append(Crypt.decrypt(user.getPassword())).append("</b>\n");
			body.append("Link: <b>").append("www.andersantos.com/mmanager").append("</b>\n");
			body.append("Na próxima tela lhe será solitado para cadastrar nova senha e informar a frase secreta cadastrada pelo usuário <b>").append(user.getSuperUser().getName()).append("</b>\n\n");
			body.append("Obrigado pela atenção,").append("\n");
			body.append("<b>Equipe Money Manager</b>");
			
			Utils.sendEmail(emailTo, emailFrom, nameFrom, MessageFactory.getMessage("lb_email_subject", user.getLanguage()), body.toString());
		}
	}

	@Override
	public List<User> list() throws Exception {
		return list(User.class, null, null);
	}

	@Override
	public List<User> listByParameter(User user) throws Exception {
		return list(User.class, user.getQueryParams(), user.getOrderBy());
	}

	@Override
	@Transactional
	public MessageReturn delete(Long id) {
		MessageReturn libReturn = new MessageReturn();
		User user = null;
		try {
			user = findById(User.class, id);
			if (user == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_user_not_found", "en"));
			} else {
				String locale = user.getCity().getState().getCountry().getLocale();
				remove(user);
				libReturn.setMessage(MessageFactory.getMessage("lb_user_deleted", locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
			libReturn.setMessage(e.getMessage());
		}
		return libReturn;
	}

	@Override
	public User getById(Long id) {
		try {
			return findById(User.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public MessageReturn login(User user) {
		MessageReturn messageReturn = null;
		try {
			messageReturn = userDAO.getByUsername(user.getUsername());
			if (messageReturn.getUser() == null) {
				messageReturn.setMessage(MessageFactory.getMessage("lb_user_not_found", "en"));
			} else if (!messageReturn.getUser().getPassword().equals(user.getPassword())) {
				messageReturn.setUser(null);
				messageReturn.setMessage(MessageFactory.getMessage("lb_user_incorrect_password", "en"));
			} else {
				if("ADMIN".equals(messageReturn.getUser().getRole().getRole())){
					messageReturn.getUser().setAdmin(true);
				}
				
				Map<String, String> queryParams = new LinkedHashMap<>();
				queryParams.put(" where x.user = ", messageReturn.getUser().getId()+"");
				Config config =  configDAO.findByParameter(Config.class, queryParams);
				messageReturn.setConfig(config);
				
				if(messageReturn.getUser().getPassword().equals(Crypt.encrypt(Constants.DEFAULT_PASSWORD))){
					messageReturn.getUser().setDefaultPassword(true);
					messageReturn.getUser().setSecretPhrase(null);
				}else{
					messageReturn.getUser().setDefaultPassword(false);
				}
				
				messageReturn.setMessage(MessageFactory.getMessage("lb_login_success", messageReturn.getUser().getLanguage()));
			}
		} catch (Exception e) {
			messageReturn = new MessageReturn();
			messageReturn.setMessage(e.getMessage());
		}
		return messageReturn;
	}
}
