package com.sm.dao;


import com.sm.model.Specialization;
import com.sm.model.SubSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubSpecializationDao extends JpaRepository<SubSpecialization,Long> {
    SubSpecialization findBySubSpecId(long subSpecId);
    List<SubSpecialization> findBySpecialization(Specialization specialization);



}
