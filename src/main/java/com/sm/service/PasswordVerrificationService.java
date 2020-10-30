package com.sm.service;

import com.sm.dto.PasswordDto;
import com.sm.model.User;
import com.sm.util.MedsolResponse;

public interface PasswordVerrificationService {

	public User verifyCode(String email, String code);

	public User changePassword(User user, PasswordDto passwordDto);

	public boolean sendMail(User userDetails);

	public MedsolResponse<User> updatePass(PasswordDto passwordDto, long userId);

	public MedsolResponse<User> verify(String email, String code);

	public MedsolResponse<String> checkPass(String email);
}
