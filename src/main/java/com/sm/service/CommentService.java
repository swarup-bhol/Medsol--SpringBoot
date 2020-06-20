package com.sm.service;

import com.sm.dto.CommentDto;
import com.sm.dto.CommentListDto;
import com.sm.dto.ReCommentDto;


public interface CommentService {
	CommentListDto createNewComment(CommentDto commentDto);

	CommentListDto createNewReComment(ReCommentDto reCommentDto);

//	Comment createNewComment(User user, Post post, String comments);
	
}
