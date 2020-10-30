package com.sm.service.impl;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.sm.dto.JwtToken;
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
import com.sm.util.JwtTokenUtil;
import com.sm.util.MedsolResponse;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

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
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	FollowService followService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		org.springframework.security.core.userdetails.User loginUser = null;
		try {
			User user = userDao.findByUserEmail(username);
			if (user == null) {
				throw new UsernameNotFoundException("Invalid email or password.");
			}
			loginUser = new org.springframework.security.core.userdetails.User(user.getUserEmail(),
					user.getUserPassword(), getAuthority());

		} catch (Exception e) {
			logger.error("Error :loadUserByUsername ",e.getMessage());
		}

		return loginUser;
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
	}

	@Override
	public User save(UserDto user) {
		User user2 = null;

		try {
			User newUser = new User();
			newUser.setUserEmail(user.getEmail());
			newUser.setUserMobile(user.getMobile());
			newUser.setFullName(user.getName());
			newUser.setUserPassword(bcryptEncoder.encode(user.getPassword()));
			user2 = userDao.save(newUser);

		} catch (Exception e) {
			logger.error("Error :save ",e.getMessage());
		}
		return user2;
	}

	@Override
	public User findOne(String username) {
		User user = null;
		try {
			user = findByEmail(username);
		} catch (Exception e) {
			logger.error("Error :findOne ",e.getMessage());
		}
		return user;
	}

	@Override
	public boolean checkUser(UserDto userDto) {
		User user = null;
		try {
			user = userDao.findByUserEmail(userDto.getEmail());
			if (user == null) {
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return true;
	}

	private User findByEmail(String email) {
		User user = null;
		try {
			user = userDao.findByUserEmail(email);
			if (user == null) {
				throw new UsernameNotFoundException("Invalid email" + email);

			}
		} catch (Exception e) {
			logger.error("Error :findByEmail ",e.getMessage());
		}
		return user;
	}

	public User findByuserId(long userId) {
		User user = null;
		try {
			user = userDao.findByUserId(userId);
			if (user == null) {
				throw new UsernameNotFoundException("User Not Found" + userId);
			}
		} catch (Exception e) {
			logger.error("Error :findByuserId ",e.getMessage());
		}
		return user;
	}

	@Override
	/**
	 * 
	 */
	public boolean loginUser(LoginUser loginUser) {
		boolean result = false;
		User user = null;
		try {
			user = userDao.findByUserEmail(loginUser.getEmail());
			if (user != null) {
				result = bcryptEncoder.matches(loginUser.getPassword(), user.getUserPassword());
			}
		} catch (Exception e) {
			logger.error("Error :loginUser ",e.getMessage());
		}
		return result;
	}

	@Override
	public MedsolResponse<User> uploadProfilePic(MultipartFile file, User user) {
		MedsolResponse<User> response = new MedsolResponse<>(true, 200, Constants.FAILED, null);
		try {
			if (!new File(uploadingDir).exists()) {
				new File(uploadingDir).mkdirs();
			}
			String uniqueID = UUID.randomUUID().toString();
			Path uploadPath = Paths.get(uploadingDir, uniqueID + file.getOriginalFilename());
			Files.write(uploadPath, file.getBytes());

			if (user.getProfilePicPath() != null) {
				Path prevPath = Paths.get(user.getProfilePicPath());
				Files.deleteIfExists(prevPath);
			}
			user.setProfilePicId(uniqueID);
			user.setProfilePicPath(uploadPath.toString());
			response = new MedsolResponse<>(true, 200, Constants.CREATED, userDao.save(user));
		} catch (IOException e) {
			logger.error("IO Error :", e.getMessage());
		} catch (Exception e) {
			logger.error("Error :", e.getMessage());
		}
		return response;

	}

	@Override
	public MedsolResponse<ProfileDetailsDto> getProfileDetails(User user) {

		MedsolResponse<ProfileDetailsDto> response = new MedsolResponse<>(false, 200, Constants.OK, null);

		ProfileDetailsDto proDetails = new ProfileDetailsDto();

		try {
			Profession profession = professionDao.findByProfessionId(user.getProfessionId());
			if (profession == null)
				throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);

			Specialization specialization = specializationDao.findBySpecializationId(user.getSpecializationId());
			if (specialization == null)
				throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);

			SubSpecialization subSpecialization = subSpecializationDao
					.findBySubSpecId(user.getDetailsSpecializationId());
			if (subSpecialization == null)
				throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);

			Grade grade = gradeDao.findByGradeId(user.getGrade());
			if (grade == null)
				throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);

//		long postCount = postDao.countByUserAndRecordStatus(user,true);
			long followingCount = followDao.totalCountByFollowedByAndIsFollowing(user.getUserId(), true);
			long followerCount = followDao.totalCountByUserIdAndIsFollowing(user.getUserId(), true);

			proDetails = ProfileDetailsDto.builder().fullName(user.getFullName()).userId(user.getUserId())
					.profession(profession.getProfessionName()).grade(grade.getGradeName())
					.specialization(specialization.getSpecializationName())
					.subSpecialization(subSpecialization.getSubSpecName()).institute(user.getInstituteName())
					.isEmailVerrified(user.isEmailVerrified()).isMobileVerrified(user.isMobileVerrified())
					.profileId(user.getProfilePicId()).docId(user.getDocumentId()).follower(followerCount)
					.following(followingCount).dob(user.getDateOfBirth()).email(user.getUserEmail())
					.mobile(user.getUserMobile()).build();

			response = new MedsolResponse<>(true, 200, Constants.OK, proDetails);
		} catch (HibernateException e) {
			logger.error(e.getMessage());
		}

		catch (Exception e) {
			logger.error(e.getMessage());
		}

		return response;
	}

	@Override
	public MedsolResponse<ProfileDetailsDto> updateProfile(long userId, UpdateProfileDto profileDto) {
		User user = null;
		MedsolResponse<ProfileDetailsDto> profileDetails = null;
		try {
			user = userDao.findByUserId(userId);
			if (user == null)
				throw new UserNotFound(Constants.USER_NOT_FOUND);
			user.setFullName(profileDto.getName());
			user.setDateOfBirth(profileDto.getDob());
			user.setUserMobile(profileDto.getMobileNo());
			user.setInstituteName(profileDto.getInstitue());
			User userData = userDao.save(user);
			profileDetails = getProfileDetails(userData);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return profileDetails;
	}

	@Override
	public MedsolResponse<User> updatePassWord(User user, PasswordDto passwordDto) {
		MedsolResponse<User> response = new MedsolResponse<>(true, 200, Constants.PASSWORD_NOT_MATCH, null);
		try {
			boolean matches = bcryptEncoder.matches(passwordDto.getOldPassword(), user.getUserPassword());
			if (matches) {
				user.setUserPassword(bcryptEncoder.encode(passwordDto.getPassword()));
				response = new MedsolResponse<>(true, 200, Constants.OK, userDao.save(user));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return response;
	}

	@Override
	public User createProfile(Profile profile, User user) {
		User save = null;
		try {
			user.setDateOfBirth(profile.getDob());
			user.setProfessionId(profile.getProfession());
			user.setSpecializationId(profile.getSpecialization());
			user.setGrade(profile.getGrade());
			user.setDetailsSpecializationId(profile.getSubspecialization());
			user.setFullName(profile.getName());
			user.setInstituteName(profile.getInstitute());
			user.setRecordStatus(true);
			save = userDao.save(user);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return save;
	}

	@Override
	public MedsolResponse<List<SuggetionsDto>> searchUser(String name, long userId) {
		List<User> users = new ArrayList<User>();
//		List<SuggetionsDto> suggetionsDtos = new ArrayList<SuggetionsDto>();
		MedsolResponse<List<SuggetionsDto>> respone = new MedsolResponse<>(false, 200, Constants.OK, null);
		try {

			users = userDao.findUsersWithStartPartOfName(name);
			if (users.isEmpty()) {
				users = userDao.findUsersWithPartOfName(name);
			}
			List<SuggetionsDto> suggetionsDtos = users.stream().map(user -> {

				Profession profession = professionDao.findByProfessionId(user.getProfessionId());
				return SuggetionsDto.builder().isFollowing(followService.isFollowing(user.getUserId(), userId))
						.Profession(profession.getProfessionName()).institute(user.getInstituteName())
						.userId(user.getUserId()).userName(user.getFullName()).build();
			}).collect(Collectors.toList());
			respone = new MedsolResponse<>(true, 200, Constants.OK, suggetionsDtos);
		} catch (HibernateException e) {
			logger.error("DB Error : searchUser", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : searchUser", e.getMessage());
		}
		return respone;
	}

	@Override
	public MedsolResponse<User> uploadDocument(MultipartFile file, User user) throws IOException {

		MedsolResponse<User> user1 = new MedsolResponse<>(false, 200, Constants.CREATED, null);

		try {
			if (!new File(docDir).exists()) {
				new File(docDir).mkdirs();
			}
			String uniqueID = UUID.randomUUID().toString();
			Path uploadPath = Paths.get(docDir, uniqueID + file.getOriginalFilename());
			Files.write(uploadPath, file.getBytes());

			if (user.getUserDocumentPath() != null) {
				Path prevPath = Paths.get(user.getUserDocumentPath());
				Files.deleteIfExists(prevPath);
			}
			user.setUserDocumentPath(uploadPath.toString());
			user.setDocumentId(uniqueID);
			user1 = new MedsolResponse<>(false, 200, Constants.CREATED, userDao.save(user));
		} catch (HibernateException e) {
			logger.error("DB Error : searchUser", e.getMessage());
		} catch (IOException e) {
			logger.error("IO Error : searchUser", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return user1;
	}

	@Override
	public MedsolResponse<JwtToken> createProfile(Profile profile, String email) {
		MedsolResponse<JwtToken> medsolResponse = new MedsolResponse<>(false, 200, Constants.OK, null);
		try {
			User user = userDao.findByUserEmail(email);
			if (user == null)
				throw new UserNotFound(Constants.USER_NOT_FOUND);
			if (user.isRecordStatus())
				return new MedsolResponse<>(false, 409, Constants.USER_EXIST, profile);
			User updatedUser = createProfile(profile, user);
			final String token = jwtTokenUtil.generateToken(user);
			medsolResponse = new MedsolResponse<>(true, 200, Constants.OK,
					new JwtToken(token, updatedUser.getUserEmail(), updatedUser.getUserId()));
		} catch (HibernateException e) {
			logger.error("DB Error : createProfile", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("IO Error : createProfile", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : createProfile", e.getMessage());
		}
		return medsolResponse;
	}

	@Override
	public MedsolResponse<JwtToken> login(LoginUser loginUser) {

		MedsolResponse<JwtToken> response = new MedsolResponse<>(false, 401, Constants.INVALID_CREDENTIALS, true);
		try {
			if (loginUser != null) {
				boolean result = loginUser(loginUser);
				if (result) {
					final User user = findOne(loginUser.getEmail());
					final String token = jwtTokenUtil.generateToken(user);
					response = new MedsolResponse<>(true, 200, Constants.OK,
							new JwtToken(token, user.getUserEmail(), user.getUserId()));
				}
				response = new MedsolResponse<>(false, 400, Constants.INVALID_CREDENTIALS, loginUser);
			}
		} catch (HibernateException e) {
			logger.error("DB Error : login", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : login", e.getMessage());
		}
		return response;
	}

	@Override
	public MedsolResponse<User> createNewUser(UserDto user) {
		MedsolResponse<User> response = new MedsolResponse<>(false, 409, Constants.USER_EXIST, user);
		try {
			boolean userExist = checkUser(user);
			if (!userExist) {
				response = new MedsolResponse<>(true, HttpStatus.OK.value(), Constants.CREATED, save(user));
			}
		} catch (Exception e) {
			logger.error("createNewUser : login", e.getMessage());
		}
		return response;
	}

}
