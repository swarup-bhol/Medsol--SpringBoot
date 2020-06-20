package com.sm.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sm.dao.FollowDao;
import com.sm.dao.GradeDao;
import com.sm.dao.PostDao;
import com.sm.dao.ProfessionDao;
import com.sm.dao.SpecializationDao;
import com.sm.dao.SubSpecializationDao;
import com.sm.dao.UserDao;
import com.sm.dto.LoginUser;
import com.sm.dto.PasswordDto;
import com.sm.dto.Profile;
import com.sm.dto.ProfileDetailsDto;
import com.sm.dto.SuggetionsDto;
import com.sm.dto.UpdateProfileDto;
import com.sm.dto.UserDto;
import com.sm.exception.ResourceNotFoundException;
import com.sm.exception.UserNotFound;
import com.sm.model.Grade;
import com.sm.model.Profession;
import com.sm.model.Specialization;
import com.sm.model.SubSpecialization;
import com.sm.model.User;
import com.sm.service.FollowService;
import com.sm.service.UserService;
import com.sm.util.Constants;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

	public static final String uploadingDir = System.getProperty("user.dir") + "/Uploads/ProfilePic";
	public static final String docDir = System.getProperty("user.dir") + "/Uploads/Document";


	@Autowired
	UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	ProfessionDao professionDao;

	@Autowired
	SpecializationDao specializationDao;

	@Autowired
	SubSpecializationDao subSpecializationDao;

	@Autowired
	FollowDao followDao;

	@Autowired
	GradeDao gradeDao;

	@Autowired
	PostDao postDao;

	@Autowired
	FollowService followService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUserEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid email or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserEmail(), user.getUserPassword(),
				getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
	}

	@Override
	public User save(UserDto user) {
		User newUser = new User();
		newUser.setUserEmail(user.getEmail());
		newUser.setUserMobile(user.getMobile());
		newUser.setFullName(user.getName());
		newUser.setUserPassword(bcryptEncoder.encode(user.getPassword()));
//		newUser.setRecordStatus(true);
		return userDao.save(newUser);
	}

	@Override
	public User findOne(String username) {
		User user = findByEmail(username);
		return user;
	}

	@Override
	public boolean checkUser(UserDto userDto) {
		User user = userDao.findByUserEmail(userDto.getEmail());
		if (user == null) {
			return false;
		}
		return true;
	}

	private User findByEmail(String email) {
		User user = userDao.findByUserEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid email" + email);

		}
		return user;
	}

	public User findByuserId(long userId) {
		User user = userDao.findByUserId(userId);
		if (user == null) {
			throw new UsernameNotFoundException("User Not Found" + userId);

		}
		return user;
	}

	@Override
	/**
	 * 
	 */
	public boolean loginUser(LoginUser loginUser) {
		boolean result = false;
		User user = userDao.findByUserEmail(loginUser.getEmail());
		if (user != null) {
			result = bcryptEncoder.matches(loginUser.getPassword(), user.getUserPassword());
		}
		return result;
	}

	@Override
	public User uploadProfilePic(MultipartFile file, User user) throws IOException {
		if (!new File(uploadingDir).exists()) {
			new File(uploadingDir).mkdirs();
		}
		String uniqueID = UUID.randomUUID().toString();
		Path uploadPath = Paths.get(uploadingDir, uniqueID+file.getOriginalFilename());
		Files.write(uploadPath, file.getBytes());

		if (user.getProfilePicPath() != null) {
			Path prevPath = Paths.get(user.getProfilePicPath());
			Files.deleteIfExists(prevPath);
		}
		user.setProfilePicId(uniqueID);
		user.setProfilePicPath(uploadPath.toString());
		return userDao.save(user);
	}

	@Override
	public ProfileDetailsDto getProfileDetails(User user) {

		Profession profession = professionDao.findByProfessionId(user.getProfessionId());
		if (profession == null)
			throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);

		Specialization specialization = specializationDao.findBySpecializationId(user.getSpecializationId());
		if (specialization == null)
			throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
 
		SubSpecialization subSpecialization = subSpecializationDao.findBySubSpecId(user.getDetailsSpecializationId());
		if (subSpecialization == null)
			throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);

		Grade grade = gradeDao.findByGradeId(user.getGrade());
		if (grade == null)
			throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
 
//		long postCount = postDao.countByUserAndRecordStatus(user,true);
		long followingCount = followDao.totalCountByFollowedByAndIsFollowing(user.getUserId(), true);
		long followerCount = followDao.totalCountByUserIdAndIsFollowing(user.getUserId(), true);

		ProfileDetailsDto proDetails = new ProfileDetailsDto();

		proDetails.setFullName(user.getFullName());
		proDetails.setUserId(user.getUserId());
		proDetails.setProfession(profession.getProfessionName());
		proDetails.setGrade(grade.getGradeName());
		proDetails.setSpecialization(specialization.getSpecializationName());
		proDetails.setSubSpecialization(subSpecialization.getSubSpecName());
		proDetails.setInstitute(user.getInstituteName());
		proDetails.setEmailVerrified(user.isEmailVerrified());
		proDetails.setMobileVerrified(user.isMobileVerrified());
		proDetails.setProfileId(user.getProfilePicId());
		proDetails.setDocId(user.getDocumentId());
		proDetails.setFollower(followerCount);
		proDetails.setFollowing(followingCount);
		proDetails.setDob(user.getDateOfBirth());
		proDetails.setEmail(user.getUserEmail());
		proDetails.setMobile(user.getUserMobile());

		return proDetails;
	}

	@Override
	public ProfileDetailsDto updateProfile(long userId, UpdateProfileDto profileDto) {
		User user = userDao.findByUserId(userId);
		if (user == null)
			throw new UserNotFound(Constants.USER_NOT_FOUND);
		user.setFullName(profileDto.getName());
		user.setDateOfBirth(profileDto.getDob());
		user.setUserMobile(profileDto.getMobileNo());
		user.setInstituteName(profileDto.getInstitue());
		User userData = userDao.save(user);
		return getProfileDetails(userData);
	}

	@Override
	public User updatePassWord(User user, PasswordDto passwordDto) {
		boolean matches = bcryptEncoder.matches(passwordDto.getOldPassword(), user.getUserPassword());
		if (matches) {
			user.setUserPassword(bcryptEncoder.encode(passwordDto.getPassword()));
			return userDao.save(user);
		}
		return null;
	}

	@Override
	public User createProfile(Profile profile, User user) {
		user.setDateOfBirth(profile.getDob());
		user.setProfessionId(profile.getProfession());
		user.setSpecializationId(profile.getSpecialization());
		user.setGrade(profile.getGrade());
		user.setDetailsSpecializationId(profile.getSubspecialization());
		user.setFullName(profile.getName());
		user.setInstituteName(profile.getInstitute());
		user.setRecordStatus(true);
		return userDao.save(user);
	}

	@Override
	public List<SuggetionsDto> searchUser(String name, long userId) {
		List<User> users = userDao.findUsersWithStartPartOfName(name);
		if (users.isEmpty()) {
			users = userDao.findUsersWithPartOfName(name);
		}
		List<SuggetionsDto> suggetionsDtos = new ArrayList<SuggetionsDto>();
		Iterator<User> iterator = users.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			SuggetionsDto suggetionsDto = new SuggetionsDto();
			Profession profession = professionDao.findByProfessionId(user.getProfessionId());
			if (profession == null)
				throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
			suggetionsDto.setFollowing(followService.isFollowing(user.getUserId(), userId));
			suggetionsDto.setProfession(profession.getProfessionName());
			suggetionsDto.setInstitute(user.getInstituteName());
			suggetionsDto.setUserId(user.getUserId());
			suggetionsDto.setUserName(user.getFullName());
			suggetionsDtos.add(suggetionsDto);
		}
		return suggetionsDtos;
	}

	@Override
	public User uploadDocument(MultipartFile file, User user) throws IOException {
		if (!new File(docDir).exists()) {
			new File(docDir).mkdirs();
		}
		String uniqueID = UUID.randomUUID().toString();
		Path uploadPath = Paths.get(docDir, uniqueID+file.getOriginalFilename());
		Files.write(uploadPath, file.getBytes());

		if (user.getUserDocumentPath() != null) {
			Path prevPath = Paths.get(user.getUserDocumentPath());
			Files.deleteIfExists(prevPath);
		}
		
		user.setUserDocumentPath(uploadPath.toString());
		user.setDocumentId(uniqueID);
		return userDao.save(user);
//		return user;
	}

}
