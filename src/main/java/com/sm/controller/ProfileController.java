package com.sm.controller;

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
import com.sm.model.Grade;
import com.sm.model.Profession;
import com.sm.model.Specialization;
import com.sm.util.ApiResponse;
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

	@GetMapping("/profession/all") 
	public ApiResponse<List<Profession>> getAllProfession() {
		return new ApiResponse<>(200, Constants.OK, professionDao.findAll());
	}

	@GetMapping("/grade/{professionId}")
	public ApiResponse<List<Grade>> getAllGradeOnProfession(@PathVariable long professionId) {
		Profession profession = professionDao.findByProfessionId(professionId);
		return new ApiResponse<>(200, Constants.OK, gradeDao.findByProfession(profession));
	}
	
	@GetMapping("/spec/all")
	public ApiResponse<List<Specialization>> getAllSpecialization(){
		List<Specialization> specializations = specDao.findAll();
		return new ApiResponse<>(200, Constants.OK, specializations);
	}

	@GetMapping("/subSpec/{specId}")
	   public ApiResponse<List<Specialization>> getAllSubSpecialisation(@PathVariable long specId){
		Specialization specialization = specDao.findBySpecializationId(specId);
		return new ApiResponse<>(200, Constants.OK, subSpecDao.findBySpecialization(specialization));
	   }

}
