package com.sm.dao;

import com.sm.model.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionDao extends JpaRepository<Profession,Long>{
    Profession findByProfessionId(long professionId);
    List<Profession> findAll();
}
