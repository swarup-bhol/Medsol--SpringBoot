package com.sm.testcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.sm.controller.ProfileController;
import com.sm.dao.ProfessionDao;
import com.sm.dao.SpecializationDao;
import com.sm.dao.SubSpecializationDao;
import com.sm.model.Profession;
import com.sm.model.Specialization;
import com.sm.model.SubSpecialization;
import com.sm.util.ApiResponse;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TestProfessionController {

	@InjectMocks
	ProfileController userProfession;
	@Mock
	ProfessionDao professionDao;
	@Mock
	SpecializationDao specDao;
	@Mock
	SubSpecializationDao DSpecDao;

	Profession profession = new Profession();
	Specialization specialization = new Specialization();
	SubSpecialization detailsSpecialization = new SubSpecialization();
	List<SubSpecialization> detailsSpecializations = new ArrayList<SubSpecialization>();
	List<Specialization> specializations = new ArrayList<Specialization>();
	List<Profession> professions = new ArrayList<Profession>();
 
//	@Before
//	public void setUp() {
//		profession.setProfessionId;(1);
//		profession.setProfessionName("Doctor");
//		specialization.setSpecializationId(1);
//		specialization.setSpecializationName("asd sda");
//		specialization.set(profession);
//		detailsSpecialization.setId(1);
//		detailsSpecialization.setName("weref");
//		detailsSpecialization.setSpecialization(specialization);
//		detailsSpecializations.add(detailsSpecialization);
//		specializations.add(specialization);
//		professions.add(profession);
//		
//	}
//	@Test
//	public void testGetAllProfession() {
//		when(professionDao.findAll()).thenReturn(professions);
//		ApiResponse<List<Profession>> allProfession = userProfession.getAllProfession();
//		assertEquals(professions, allProfession.getResult());
//	}
//	
//	@Test
//	public void testGetAllSpecialization() {
//		when(professionDao.findById(1)).thenReturn(profession);
//		when(specDao.findByProfession(profession)).thenReturn(specializations);
//		ApiResponse<List<Specialization>> allSpec = userProfession.getAllSpecialisation(1);
//		assertEquals(specializations, allSpec.getResult());
//	}
//	
//	@Test
//	public void testGetAllSubSpecialization() {
//		when(specDao.findById(1)).thenReturn(specialization);
//		when(DSpecDao.findBySpecialization(specialization)).thenReturn(detailsSpecializations);
//		ApiResponse<List<Specialization>> allDSpec = userProfession.getAllSubSpecialisation(1);
//		assertEquals(detailsSpecializations, allDSpec.getResult());
//	}
}
