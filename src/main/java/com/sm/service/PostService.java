package com.sm.service;


import java.io.IOException;
import java.util.List;

import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sm.dto.PostDto;
import com.sm.model.Post;
import com.sm.model.User;
import com.sm.util.MedsolResponse;

@Service
public interface PostService  {
	public Post uploadMedia(MultipartFile file, User user,String content, String specialization) throws IOException;
	
	public Post createPost(User user,String content, String specialization);
	
	public Post getPostById(long postId);
	
	public Post updateMedia(Post post, String content, MultipartFile file) throws IOException;
	
	public List<PostDto> getNewsFeedPosts(User user, int pageNo);
	
	public List<PostDto> getUploadedPost(User user, int pageNo);
	
	public PostDto findByPostId(Post post);
	
//	public ResponseEntity<byte[]> prepareContent(Post post, String httpRangeList);
	
	public List<PostDto> getPostSpecType(List<Long> specList, User user, int pageNo);
	
	public ResponseEntity<ResourceRegion> getVideoRegion(Post post, String rangeHeader) throws IOException;

	public MedsolResponse<Post> createNewPost(Long userId, String content, MultipartFile file, String type);

	public MedsolResponse<Post> updatePost(Long postId, String content, MultipartFile file);

	public MedsolResponse<Post> deletePosts(long postId);

	public MedsolResponse<List<PostDto>> postByUser(long userId, int pageNo);

	public MedsolResponse<List<PostDto>> allPosts(long userId, int pageNo);

	public MedsolResponse<PostDto> postBySpec(List<Long> specList, long userId, int pageNo);

	public MedsolResponse<PostDto> postsById(long postId);
	
}
