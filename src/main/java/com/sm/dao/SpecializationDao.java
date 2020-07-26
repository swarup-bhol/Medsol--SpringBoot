package com.sm.dao;



import com.sm.model.Specialization;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationDao extends JpaRepository<Specialization, Long> {
    Specialization findBySpecializationId(long specId);
    List<Specialization> findBySpecializationIdIn(List<Long> specList);
}
