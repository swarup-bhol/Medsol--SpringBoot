package com.sm.service.impl;

import java.util.Random;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sm.dao.UserDao;
import com.sm.dto.PasswordDto;
import com.sm.exception.UserNotFound;
import com.sm.model.User;
import com.sm.service.PasswordVerrificationService;
import com.sm.util.Constants;
import com.sm.util.EmailService;
import com.sm.util.MedsolResponse;

@Service
@Transactional
public class PasswordVerrificationServiceImpl implements PasswordVerrificationService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	EmailService emailService;

	
	/**
	 * 
	 * @author swarupb
	 * 
	 * @purpose verify code
	 * 
	 * @param email
	 * @param code
	 * 
	 * @return User
	 */
	@Override
	public User verifyCode(String email, String code) {
		User user = null;
		try {
			user = userDao.findByUserEmail(email);
			if (user == null)
				throw new UserNotFound(Constants.USER_NOT_FOUND);
			String emailVerifficationCode = user.getEmailVerifficationCode();
			if (emailVerifficationCode.equals(code)) {
				return user;
			}
		} catch (HibernateException e) {
			log.error("DB Error: verifyCode", e.getMessage());
		} catch (NullPointerException e) {
			log.error("Null : verifyCode", e.getMessage());
		} catch (Exception e) {
			log.error("Error: verifyCode", e.getMessage());
		}
		return null;

	}

	
	
	/**
	 * @author swarupb
	 * 
	 * @purpose change password
	 * 
	 * @param user
	 * @param passwordDto
	 * 
	 * @return User
	 * 
	 */
	@Override
	public User changePassword(User user, PasswordDto passwordDto) {
		User save = null;
		try {
			user.setUserPassword(bcryptEncoder.encode(passwordDto.getPassword()));
			save = userDao.save(user);
		} catch (HibernateException e) {
			log.error("DB Error: changePassword", e.getMessage());
		} catch (NullPointerException e) {
			log.error("Null : changePassword", e.getMessage());
		} catch (Exception e) {
			log.error("Error: changePassword", e.getMessage());
		}
		return save;
	}

	
	/**
	 * @author swarupb
	 * 
	 * @purpose Send mail
	 * 
	 * @param userDetails
	 * 
	 * @return boolean
	 * 
	 */
	@Override
	public boolean sendMail(User userDetails) {
		boolean status = false;
		try {
			Random rnd = new Random();
			int number = rnd.nextInt(999999);
			boolean sentMail = emailService.sentMail(userDetails.getUserEmail(), String.format("%06d", number));
			if (sentMail) {
				userDetails.setEmailVerifficationCode(String.format("%06d", number));
				userDao.save(userDetails);
				status = true;
			}
		} catch (NullPointerException e) {
			log.error("Null : sendMail", e.getMessage());
		} catch (Exception e) {
			log.error("Error: sendMail", e.getMessage());
		}
		return status;
	}

	
	/**
	 * @author swarupb
	 * 
	 * @purpose update Password
	 * 
	 * @param passwordDto
	 * @param userId
	 *
	 * 
	 * @return MedsolResponse
	 * 
	 */
	@Override
	public MedsolResponse<User> updatePass(PasswordDto passwordDto, long userId) {
		MedsolResponse<User> response = new MedsolResponse<>(false, 200, Constants.OK, null);
		try {
			User user = userDao.findByUserId(userId);
			if (user == null)
				throw new UserNotFound(Constants.USER_NOT_FOUND);
			if (!passwordDto.getPassword().equals(passwordDto.getCnfPassword()))
				response = new MedsolResponse<>(true, 402, Constants.BAD_REQUEST, passwordDto);
			response = new MedsolResponse<>(true, 200, Constants.OK, changePassword(user, passwordDto));
		} catch (NullPointerException e) {
			log.error("Null : updatePass", e.getMessage());
		} catch (Exception e) {
			log.error("Error: updatePass", e.getMessage());
		}
		return response;
	}

	
	/**
	 * @author swarupb
	 * 
	 * @purpose verify emails
	 * 
	 * @param email
	 * @param code
	 * 
	 * 
	 * @return MedsolResponse
	 * 
	 */
	@Override
	public MedsolResponse<User> verify(String email, String code) {
		User user = verifyCode(email, code);
		if (user == null)
			return new MedsolResponse<>(true, 200, Constants.BAD_REQUEST, code);
		return new MedsolResponse<>(true, 200, Constants.OK, user);
	}

	
	/**
	 * @author swarupb
	 * 
	 * @purpose check password
	 * 
	 * @param email
	 * 
	 * @return MedsolResponse
	 * 
	 */
	@Override
	public MedsolResponse<String> checkPass(String email) {
		MedsolResponse<String> response = new MedsolResponse<>(false, 200, Constants.OK, null);
		try {
			User userDetails = userDao.findByUserEmail(email);
			if (userDetails == null)
				response = new MedsolResponse<>(true, 200, Constants.OK, Constants.USER_NOT_FOUND);
			boolean messageStatus = sendMail(userDetails);
			response = new MedsolResponse<>(true, 200, Constants.OK, messageStatus);
		} catch (NullPointerException e) {
			log.error("Null : checkPass", e.getMessage());
		} catch (Exception e) {
			log.error("Error: checkPass", e.getMessage());
		}
		return response;
	}
}
