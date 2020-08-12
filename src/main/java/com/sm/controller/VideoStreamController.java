package com.sm.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.dao.PostDao;
import com.sm.model.Post;

@RestController
@RequestMapping("/api/medsol/posts")
public class VideoStreamController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PostDao postDao;
	
//	@GetMapping(value = "/video/{postId}",produces = "application/octet-stream")
	public ResponseEntity<ResourceRegion> getVideo(HttpServletResponse resp, @PathVariable long postId,
			@RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {
//		Post post = postDao.findByPostId(postId);
//		if (post == null || post.getPostVideoPath() == null)
//			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
//		resp.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//		ResponseEntity<ResourceRegion> stream = postService.getVideoRegion(post, rangeHeader);
		return null;
	}
//	@GetMapping(value = "/video/{postId}",produces = "application/octet-stream")
	public ResponseEntity<ResourceRegion> video( @PathVariable long postId,HttpServletRequest request, @RequestHeader HttpHeaders headers)
            throws Exception {
		Post post = postDao.findByPostId(postId);
		if (post == null || post.getPostVideoPath() == null) {
			logger.info("Path not found");
		}
		File file = new File(post.getPostVideoPath());
        if (!file.isFile()) {
        	logger.info("Not a file");
        }

        FileSystemResource resource = new FileSystemResource(file);
        ResourceRegion region = resourceRegion(resource, headers);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaType.parseMediaType(Files.probeContentType(Paths.get(post.getPostVideoPath())))).body(region);
    }
	private ResourceRegion resourceRegion(FileSystemResource video, HttpHeaders headers) throws IOException {
        long contentLength = video.contentLength();
        List<HttpRange> ranges = headers.getRange();
        if (ranges.size() > 0) {
            HttpRange range = ranges.get(0);
            long start = range.getRangeStart(contentLength);
            long end = range.getRangeEnd(contentLength);
            long rangeLength = Long.min(contentLength, end - start + 1);
            return new ResourceRegion(video, start, rangeLength);
        } else {
            return new ResourceRegion(video, 0, contentLength);
        }
    }

}
