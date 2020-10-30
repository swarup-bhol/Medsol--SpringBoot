package com.sm.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sm.dao.PostDao;
import com.sm.dao.UserDao;
import com.sm.dto.PostDto;
import com.sm.exception.PostNotFoundException;
import com.sm.model.Post;
import com.sm.service.PostService;
import com.sm.service.UserService;
import com.sm.util.MedsolResponse;

@RestController
@RequestMapping("/api/medsol/posts")
public class PostController {

	@Autowired
	UserService userService;

	@Autowired
	PostService postService;

	@Autowired
	PostDao postDao;

	@Autowired
	UserDao userDao;

	// Upload post only text or file like image or video
	/**
	 * @author swarupb
	 * 
	 * 
	 * @param userId
	 * @param content
	 * @param file
	 * @param type
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/{userId}")
	public MedsolResponse<Post> uploadPost(@PathVariable Long userId, @RequestParam("content") String content,
			@RequestParam(required = false) MultipartFile file, @RequestParam("type") String type) {
		return postService.createNewPost(userId, content, file, type);
	}

	// Update post
	/**
	 * @author swarupb
	 * 
	 * 
	 * @param postId
	 * @param content
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PutMapping("post/{postId}")
	public MedsolResponse<Post> updateUploadedPost(@PathVariable Long postId, @RequestParam("content") String content,
			@RequestParam(required = false) MultipartFile file) throws IOException {
		return postService.updatePost(postId, content, file);
	}

	// Get each media file in format of jpeg or png
	/**
	 * 
	 * @author swarupb
	 * 
	 * 
	 * @param postId
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/img/{postId}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public @ResponseBody byte[] getClassPath(@PathVariable Long postId) throws IOException {
		Post post = postService.getPostById(postId);
		if (post == null || post.getPostImgPath() == null)
			return null;
		Path uploadPath = Paths.get(post.getPostImgPath());
		byte[] test = Files.readAllBytes(uploadPath);
		return test;
	}

	/**
	 * 
	 * @author swarupb
	 * 
	 * 
	 * @param resp
	 * @param postId
	 * @param rangeHeader
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/video/{postId}", produces = "application/octet-stream")
	public ResponseEntity<ResourceRegion> getVideo(HttpServletResponse resp, @PathVariable long postId,
			@RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {
		Post post = postDao.findByPostId(postId);
		if (post == null || post.getPostVideoPath() == null)
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		resp.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		ResponseEntity<ResourceRegion> stream = postService.getVideoRegion(post, rangeHeader);
		return stream;
	}

	// Delete post by postId
	/**
	 * 
	 * @author swarupb
	 * 
	 * @param postId
	 * @return
	 * @throws PostNotFoundException
	 */
	@DeleteMapping("/{postId}")
	public MedsolResponse<Post> deletePost(@PathVariable long postId) throws PostNotFoundException {
		return postService.deletePosts(postId);
	}

	// Get all post By postId
	/**
	 * 
	 * @author swarupb
	 * 
	 * 
	 * @param userId
	 * @param pageNo
	 * @return
	 */
	@GetMapping("/{userId}/post/{pageNo}")
	public MedsolResponse<List<PostDto>> getAllPostByUser(@PathVariable long userId, @PathVariable int pageNo) {
		return postService.postByUser(userId, pageNo);

	}

	/**
	 * @author swarupb
	 * 
	 * @param userId
	 * @param pageNo
	 * @return
	 */
	@GetMapping("/{userId}/feeds/{pageNo}")
	public MedsolResponse<List<PostDto>> getAllPost(@PathVariable long userId, @PathVariable int pageNo) {
		return postService.allPosts(userId, pageNo);
	}

	/**
	 * @author swarupb
	 * 
	 * 
	 * @param specList
	 * @param userId
	 * @param pageNo
	 * @return
	 */
	@GetMapping("feeds/{userId}/bySpec/{pageNo}")
	public MedsolResponse<PostDto> getPostBySpecifications(@RequestParam List<Long> specList, @PathVariable long userId,
			@PathVariable int pageNo) {
		return postService.postBySpec(specList, userId, pageNo);
	}

	/**
	 * @author swarupb
	 * 
	 * 
	 * @param postId
	 * @return
	 */
	@GetMapping("/{postId}")
	public MedsolResponse<PostDto> getPostByPostId(@PathVariable long postId) {
		return postService.postsById(postId);
	}

}
