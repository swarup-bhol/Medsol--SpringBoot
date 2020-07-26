package com.sm.dao;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sm.model.Post;
import com.sm.model.PostType;
import com.sm.model.Specialization;


@Repository
public interface PostTypeDao extends JpaRepository<PostType, Long>{
	@Query( "select p.post from PostType p where p.specialization in ?1" )
	List<Post> findPostBySpecializationIn(List<Specialization> specList, PageRequest pageRequest);
}
