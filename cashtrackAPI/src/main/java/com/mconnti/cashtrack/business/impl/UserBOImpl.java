package com.mconnti.cashtrack.business.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.persistence.RollbackException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.mconnti.cashtrack.business.UserBO;
import com.mconnti.cashtrack.business.impl.security.TokenUtils;
import com.mconnti.cashtrack.entity.Config;
import com.mconnti.cashtrack.entity.Role;
import com.mconnti.cashtrack.entity.User;
import com.mconnti.cashtrack.entity.xml.MessageReturn;
import com.mconnti.cashtrack.entity.xml.TokenTransfer;
import com.mconnti.cashtrack.persistence.ConfigDAO;
import com.mconnti.cashtrack.persistence.UserDAO;
import com.mconnti.cashtrack.utils.Constants;
import com.mconnti.cashtrack.utils.Crypt;
import com.mconnti.cashtrack.utils.MessageFactory;
import com.mconnti.cashtrack.utils.Utils;

public class UserBOImpl extends GenericBOImpl<User> implements UserBO {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private ConfigDAO configDAO;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public MessageReturn save(User user) {
		MessageReturn libReturn = new MessageReturn();
		if (user.getLanguage() != null) {
			try {
				if (user.getId() == null) {
					if (user.getBirth() != null && !user.getBirth().isEmpty()) {
						user.setBirthDate(Utils.stringToDate(user.getBirth(), false).getTime());
					}
					user.setRegister(new Date());
					user.setExcluded(false);

					if (user.getDefaultPassword() != null && user.getDefaultPassword()) {
						user.setPassword(passwordEncoder.encode(Constants.DEFAULT_PASSWORD));
						user.setDefaultPassword(true);
					} else {
						user.setPassword(passwordEncoder.encode(user.getPassword()));
						user.setDefaultPassword(false);
					}
				} else {
					if (user.getPassword() != null && !user.getPassword().isEmpty()) {
						if (user.getDefaultPassword() == null) {
							user.setDefaultPassword(false);
						}
						if (!user.getDefaultPassword()) {
							user.setPassword(passwordEncoder.encode(user.getPassword()));
						}
					}
				}
				Role role = null;
				if(user.getRole() == null && user.getToken() == null){
					Map<String, String> queryParams = new LinkedHashMap<>();
					queryParams.put(" where x.role = '", Constants.ROLE_SUPER_USER+ "'");
					role = findByParameter(Role.class, queryParams);
				} else {
					role = findById(Role.class, user.getRole().getId());
				}
				if(role != null){
					user.setRole(role);
					saveGeneric(user);
				}
			} catch (Exception e) {
				e.printStackTrace();
				libReturn.setUser(user);
				libReturn.setMessage(e.getMessage());
			}
			if (libReturn.getMessage() == null && user.getId() == null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_user_saved", user.getLanguage()));
				libReturn.setUser(user);
				if(user.getSuperUser() != null){
					try {
						sendEmail(user);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (MessagingException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (libReturn.getMessage() == null && user.getId() != null) {
				libReturn.setMessage(MessageFactory.getMessage("lb_user_updated", user.getLanguage()));
				libReturn.setUser(user);
			}
		} else {
			libReturn.setMessage(MessageFactory.getMessage("lb_city_not_found", user.getLanguage()));
		}

		return libReturn;
	}

	private void sendEmail(User user) throws Exception {
		if (user.getDefaultPassword() != null && user.getDefaultPassword()) {
			String emailTo = user.getEmail();
			String emailFrom = user.getSuperUser().getEmail();
			String nameFrom = user.getSuperUser().getName();
			StringBuilder body = new StringBuilder();
			body.append(MessageFactory.getMessage("lb_email_hello", user.getLanguage())).append(" <b>").append(user.getName()).append("</b><br/>");
			body.append("<b>").append(user.getSuperUser().getName()).append("</b>").append(MessageFactory.getMessage("lb_email_first_line", user.getLanguage())).append("<br/>");
			body.append(MessageFactory.getMessage("lb_email_second_line", user.getLanguage())).append(" <b>").append(user.getSuperUser().getName()).append("</b><br/>");

			body.append(MessageFactory.getMessage("lb_email_phrase", user.getLanguage())).append(" <b>").append(user.getSuperUser().getSecretPhrase()).append("</b><br/>");
			body.append(MessageFactory.getMessage("lb_email_first_login", user.getLanguage())).append("<br/>");
			body.append(MessageFactory.getMessage("lb_email_user", user.getLanguage())).append(" <b>").append(user.getUsername()).append("</b><br/>");
			body.append(MessageFactory.getMessage("lb_email_password", user.getLanguage())).append(" <b>").append(Crypt.decrypt(user.getPassword())).append("</b><br/>");
			body.append(MessageFactory.getMessage("lb_email_link", user.getLanguage())).append(" <b>").append(MessageFactory.getMessage("lb_email_url", user.getLanguage())).append("</b><br/>");
			body.append(MessageFactory.getMessage("lb_email_next_page", user.getLanguage())).append(" <b>").append(user.getSuperUser().getName()).append("</b><br/><br/>");
			body.append(MessageFactory.getMessage("lb_email_thanks", user.getLanguage())).append("<br/>");
			body.append("<b>").append(MessageFactory.getMessage("lb_email_signature", user.getLanguage())).append("</b>");

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
				String locale = user.getLanguage();
				remove(user);
				libReturn.setMessage(MessageFactory.getMessage("lb_user_deleted", locale));
			}
		} catch (RollbackException r){ 
			r.printStackTrace();
			libReturn.setMessage(r.getMessage());
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
	public MessageReturn login(String username, String password) {

		MessageReturn messageReturn = null;
		try {
			messageReturn = getByUsername(username);
			if (messageReturn.getUser() == null) {
				messageReturn.setMessage(MessageFactory.getMessage("lb_user_not_found", "en"));
			} else {

				messageReturn.setTokenTransfer(getTokenTransfer(username, password));

				if ("ADMIN".equals(messageReturn.getUser().getRole().getRole())) {
					messageReturn.getUser().setAdmin(true);
				}

				Map<String, String> queryParams = new LinkedHashMap<>();
				queryParams.put(" where x.user = ", messageReturn.getUser().getId() + "");
				Config config = configDAO.findByParameter(Config.class, queryParams);
				messageReturn.setConfig(config);

				if (messageReturn.getUser().getDefaultPassword() != null && messageReturn.getUser().getDefaultPassword()) {
					messageReturn.getUser().setSecretPhrase(null);
				} else {
					messageReturn.getUser().setDefaultPassword(false);
				}

				messageReturn.setMessage(MessageFactory.getMessage("lb_login_success", messageReturn.getUser().getLanguage()));
			}
		} catch (Exception e) {
			messageReturn = new MessageReturn();
			if (e.getMessage().equals("Bad credentials")) {
				messageReturn.setMessage(MessageFactory.getMessage("lb_user_incorrect_password", "en"));
			} else {
				messageReturn.setMessage(e.getMessage());
			}
		}
		return messageReturn;
	}

	private TokenTransfer getTokenTransfer(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = this.authManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		/*
		 * Reload user as password of authentication principal will be null after authorization and password is needed for token generation
		 */
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
		return new TokenTransfer(TokenUtils.createToken(userDetails));
	}

	@Override
	public User getSuperUser(User user) {
		User userRet = getById(user.getId());
		return userRet.getSuperUser() == null ? userRet : userRet.getSuperUser();
	}

	@Override
	public MessageReturn getByUsername(String username) throws Exception {
		return userDAO.getByUsername(username);
	}

}
