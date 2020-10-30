package com.sm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.dao.UserDao;
import com.sm.dto.PasswordDto;
import com.sm.model.User;
import com.sm.service.PasswordVerrificationService;
import com.sm.util.MedsolResponse;
import com.sm.util.EmailService;

@RestController
@RequestMapping("api/medsol/password")
public class PasswordVerrificationController {

	@Autowired
	UserDao userDao;

	@Autowired
	EmailService emailService;

	@Autowired
	PasswordVerrificationService passwordService;

	
	/**
	 * @author swarupb
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("/{email}")
	public MedsolResponse<String> checkEmailOrPassword(@PathVariable String email) {

		return passwordService.checkPass(email);

	}

	/**
	 * @author swarupb
	 * 
	 * @param email
	 * @param code
	 * @return
	 */
	@GetMapping("/verify/{email}/{code}")
	public MedsolResponse<User> verifyPassword(@PathVariable String email, @PathVariable String code) {
		return passwordService.verify(email, code);
	}


	/**
	 * @author swarupb
	 * 
	 * @param passwordDto
	 * @param userId
	 * @return
	 */
	@PutMapping("/{userId}")
	public MedsolResponse<User> updatePassword(@RequestBody PasswordDto passwordDto, @PathVariable long userId) {
		return passwordService.updatePass(passwordDto, userId);
	}


}
