package com.sm.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sm.model.Follower;

@Repository
public interface FollowDao extends JpaRepository<Follower, Long>{
	Follower findByUserIdAndFollowedBy(long userId,long followingId);
	List<Follower> findByUserIdAndIsFollowing(long userId,boolean isFollowing);
	List<Follower> findByFollowedByAndIsFollowing(long userId,boolean isFollowing);
	@Query("SELECT COUNT (f.followedBy) FROM Follower f WHERE f.userId = ?1 AND isFollowing = ?2")
	long totalCountByFollowedByAndIsFollowing(long userId,boolean isFollowing);
	@Query("SELECT COUNT (f.userId) FROM Follower f WHERE f.followedBy = ?1 AND isFollowing = ?2")
	long totalCountByUserIdAndIsFollowing(long userId,boolean isFollowing);
	@Query("select f.userId from Follower f where f.followedBy = ?1 and f.isFollowing = ?2")
	List<Long> findAllFolowerId(long userId,boolean isFollowing);
	
//	@Query("select f.isFollowing from Follower f where f.userId = ?1 and f.followedBy = ?2")
//	boolean findIsFollowingByUserIdAndPFollowedBy(long userId, long followedBy);
	
	@Query("select f.isFollowing from Follower f where f.userId = ?1 and f.followedBy = ?2")
	List<Boolean> findIsFollowingByUserIdAndPFollowedBy(long userId, long followedBy);
}
