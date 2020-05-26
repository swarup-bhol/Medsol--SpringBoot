package com.sm.service;

import com.sm.dto.CommentDto;
import com.sm.dto.CommentListDto;


public interface CommentService {
	CommentListDto createNewComment(CommentDto commentDto);

//	Comment createNewComment(User user, Post post, String comments);
	
}
