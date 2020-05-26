package com.sm.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sm.dao.CommentDao;
import com.sm.dao.LikeDao;
import com.sm.dao.PostDao;
import com.sm.dao.ProfessionDao;
import com.sm.dto.CommentListDto;
import com.sm.dto.PostDto;
import com.sm.model.Comment;
import com.sm.model.Likes;
import com.sm.model.Post;
import com.sm.model.Profession;
import com.sm.model.User;
import com.sm.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	public static final String uploadingDir = System.getProperty("user.dir") + "/Uploads/Posts";

	@Autowired
	PostDao postdao;

	@Autowired
	CommentDao commentdao;

	@Autowired
	LikeDao likeDao;

	@Autowired
	ProfessionDao profDao;

	/**
	 * 
	 */
	@Override
	public Post uploadMedia(MultipartFile file, User user, String content) throws IOException {
		if (!new File(uploadingDir).exists()) {
			new File(uploadingDir).mkdirs();
		}
		Path uploadPath = Paths.get(uploadingDir, file.getOriginalFilename());
		Files.write(uploadPath, file.getBytes());

		Post post = new Post();
		post.setPostContent(content);
		post.setPostImgPath(uploadPath.toString());
		post.setUser(user); 
		post.setRecordStatus(true);
		return postdao.save(post);
	}

	@Override
	public Post createPost(User user, String content) {

		Post post = new Post();
		post.setPostContent(content);
		post.setUser(user);
		post.setRecordStatus(true);
		return postdao.save(post);
	}

	@Override
	public Post getPostById(long postId) {
		return postdao.findByPostId(postId);

	}

	@Override
	public Post updateMedia(Post post, String content, MultipartFile file) throws IOException {
		String uploadDirectory = post.getPostImgPath();
		if (uploadDirectory.contains(file.getOriginalFilename())) {
			Path uploadPath = Paths.get(uploadDirectory);
			Files.write(uploadPath, file.getBytes());
			post.setPostContent(content);
			return postdao.save(post);
		}
		File img = new File(uploadDirectory);
		img.delete();
		Path uploadPath = Paths.get(uploadingDir, file.getOriginalFilename());
		Files.write(uploadPath, file.getBytes());
		post.setPostImgPath(uploadPath.toString());
		post.setPostContent(content);
		return postdao.save(post);
	}

	@Override
	public List<PostDto> getNewsFeedPosts(User user, int pageNo) {

		List<Post> posts = postdao.findAllByRecordStatusOrderByPostIdDesc(true,PageRequest.of(pageNo, 10));
		if (posts.isEmpty()) {
			return new ArrayList<>();
		} else {

			return  preparePosts(posts,user);
		}
	}

	@Override
	public List<PostDto> getUploadedPost(User user, int pageNo) {
		List<Post> posts = postdao.findAllByUserAndRecordStatus(user,true, PageRequest.of(pageNo, 10));
//		List<Post> posts = postdao.findAllByRecordStatusAndUser(user, true);
		if (posts.isEmpty()) {
			return new ArrayList<>();
		} else { 
			return  preparePosts(posts,user);
			}
			
	}

	/**
	 * @author swarup
	 * 
	 * 
	 * @param posts
	 * @param user
	 * @return List
	 */
	private List<PostDto> preparePosts(List<Post> posts,User user) {
		List<PostDto> postDtos = new ArrayList<PostDto>();
		
		Iterator<Post> itr = posts.iterator();
		while (itr.hasNext()) {
			Post post = itr.next();
			PostDto postDto = new PostDto();
			List<Comment> comments = commentdao.findByPost(post);
			long likeCount = likeDao.countByPost(post);
			Likes likes = likeDao.findByPostAndUser(post, user);
			Profession profession = profDao.findByProfessionId(post.getUser().getProfessionId());
			postDto.setCommentLIst(prepareComment(comments));
			postDto.setPost(post);
			postDto.setLikeCount(likeCount);
			postDto.setUserId(post.getUser().getUserId());
			postDto.setFullName(post.getUser().getFullName());
			postDto.setInstituteName(post.getUser().getInstituteName());
			postDto.setCommentCount(comments.size());
			postDto.setProfession(profession.getProfessionName());
			if (likes == null)
				postDto.setLike(false);
			else
				postDto.setLike(likes.isRecordStatus());
			postDtos.add(postDto);
		}
		return	postDtos;
		
	} 
	
	private List<CommentListDto> prepareComment(List<Comment> comments) {
		
		Iterator<Comment> itr = comments.iterator();
		List<CommentListDto> listDtos = new ArrayList<CommentListDto>();
		while(itr.hasNext()) {
			Comment comt = itr.next();
			CommentListDto dto = new CommentListDto();
			dto.setUserId(comt.getUser().getUserId());
			dto.setUserName(comt.getUser().getFullName());
			dto.setCommentId(comt.getCommentId());
			dto.setCommentedText(comt.getComment());
			dto.setCommentedTime(comt.getCreatedTime());
			listDtos.add(dto); 
		}
		return listDtos;
	}
}
