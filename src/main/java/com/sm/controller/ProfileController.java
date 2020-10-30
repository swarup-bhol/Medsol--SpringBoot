package com.sm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.dao.GradeDao;
import com.sm.dao.ProfessionDao;
import com.sm.dao.SpecializationDao;
import com.sm.dao.SubSpecializationDao;
import com.sm.dao.UserDao;
import com.sm.exception.UserNotFound;
import com.sm.model.Grade;
import com.sm.model.Profession;
import com.sm.model.Specialization;
import com.sm.model.User;
import com.sm.util.MedsolResponse;
import com.sm.util.Constants;

@RestController
@RequestMapping("/api/medsol/profile")
public class ProfileController {

	@Autowired
	ProfessionDao professionDao;

	@Autowired
	GradeDao gradeDao;

	@Autowired
	SpecializationDao specDao;

	@Autowired
	SubSpecializationDao subSpecDao;
	
	@Autowired
	UserDao userDao;

	/**
	 * @author swarupb
	 * 
	 * @return
	 */
	@GetMapping("/profession/all") 
	public MedsolResponse<List<Profession>> getAllProfession() {
		return new MedsolResponse<>(true ,200, Constants.OK, professionDao.findAll());
	}

	/**
	 * @author swarupb
	 * 
	 * @param professionId
	 * @return
	 */
	@GetMapping("/grade/{professionId}")
	public MedsolResponse<List<Grade>> getAllGradeOnProfession(@PathVariable long professionId) {
		Profession profession = professionDao.findByProfessionId(professionId);
		return new MedsolResponse<>(true ,200, Constants.OK, gradeDao.findByProfession(profession));
	}
	
	
	/**
	 * @author swarupb
	 * 
	 * @return
	 */
	@GetMapping("/spec/all")
	public MedsolResponse<List<Specialization>> getAllSpecialization(){
		List<Specialization> specializations = specDao.findAll();
		return new MedsolResponse<>(true ,200, Constants.OK, specializations);
	}

	
	/**
	 * @author swarupb
	 * 
	 * @param specId
	 * @return
	 */
	@GetMapping("/subSpec/{specId}")
	   public MedsolResponse<List<Specialization>> getAllSubSpecialisation(@PathVariable long specId){
		Specialization specialization = specDao.findBySpecializationId(specId);
		return new MedsolResponse<>(true ,200, Constants.OK, subSpecDao.findBySpecialization(specialization));
	   }
	
	
	/**
	 * @author swarupb
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/spec/{userId}")
	public MedsolResponse<List<Specialization>> getSpecializationByUserId(@PathVariable long userId){
		User user = userDao.findByUserId(userId);
		if(user == null) throw new UserNotFound(Constants.USER_NOT_FOUND);
		Specialization spec = specDao.findBySpecializationId(user.getSpecializationId());
		List<Specialization> specializations = new ArrayList<>();
		specializations.add(spec);
		return new MedsolResponse<>(true ,200, Constants.OK, specializations);
		
	}
	
//	@PostMapping("/profession")
//	public ApiResponse<List<SubSpecialization>> createProfession(@RequestBody List<SubSpecialization> specs){
//		return new ApiResponse(200, HttpStatus.CREATED.name(),subSpecDao.saveAll(specs));// subSpecDao.saveAll(specs))
//	}

}
