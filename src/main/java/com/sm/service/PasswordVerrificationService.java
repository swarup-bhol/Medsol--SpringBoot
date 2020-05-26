package com.sm.service;

import com.sm.dto.PasswordDto;
import com.sm.model.User;

public interface PasswordVerrificationService {

	public User verifyCode(String email, String code);

	public User changePassword(User user, PasswordDto passwordDto);

	public boolean sendMail(User userDetails);
}
