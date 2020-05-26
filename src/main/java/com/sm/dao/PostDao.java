package com.sm.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sm.model.Post;
import com.sm.model.User;

@Repository
public interface PostDao extends JpaRepository<Post, Long>{
	
	Post findByPostId(long postId);
	List<Post> findAllByUser(User user);
	long countByUserAndRecordStatus(User user,boolean status);
	List<Post> findAllByRecordStatusOrderByPostIdDesc(boolean status,Pageable page);
	List<Post> findAllByUserAndRecordStatus(User user,boolean status, Pageable page);
}
