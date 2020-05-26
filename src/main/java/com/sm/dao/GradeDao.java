package com.sm.dao;


import com.sm.model.Grade;
import com.sm.model.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeDao extends JpaRepository<Grade,Long> {
    List<Grade> findByProfession(Profession profession);
    Grade findByGradeId(long gradeId);
}
