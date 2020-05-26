package com.sm.service;


import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sm.dto.PostDto;
import com.sm.model.Post;
import com.sm.model.User;

@Service
public interface PostService  {
	public Post uploadMedia(MultipartFile file, User user,String content) throws IOException;
	public Post createPost(User user,String content);
	public Post getPostById(long postId);
	public Post updateMedia(Post post, String content, MultipartFile file) throws IOException;
	public List<PostDto> getNewsFeedPosts(User user, int pageNo);
	public List<PostDto> getUploadedPost(User user, int pageNo);
	
}
