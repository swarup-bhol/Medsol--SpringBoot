package com.sm.controller;



import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.sm.dao.PostDao;
import com.sm.model.Post;
import com.sm.service.PostService;


@RestController
@RequestMapping("/api/medsol/posts")
public class VideoStreamController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PostService postService;

	@Autowired
	PostDao postDao;

	@GetMapping(value = "/video/{postId}")
	public ResponseEntity<?> streamVideo(HttpServletResponse resp, @PathVariable long postId,
			@RequestHeader(value = "Range", required = false) String httpRangeList) throws IOException {
//		resp.setContentType("video/mp4");
		Post post = postDao.findByPostId(postId);
		if (post == null || post.getPostVideoPath() == null)
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		return postService.prepareContent(post, httpRangeList);
		
	}
}