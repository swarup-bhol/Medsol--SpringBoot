package com.sm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sm.model.Likes;
import com.sm.model.Post;
import com.sm.model.User;


@Repository
public interface LikeDao extends JpaRepository<Likes, Long>{
	Likes getByLikeId(long likeId);
	List<Likes> getByPost(Post post);
	long countByPost(Post post);
	Likes findByPostAndUser(Post post, User user);
	boolean findRecordStatusByPostAndUser(Post post, User user);
}
