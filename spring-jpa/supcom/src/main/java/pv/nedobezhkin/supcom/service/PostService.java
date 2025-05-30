package pv.nedobezhkin.supcom.service;

import java.util.List;

import org.apache.coyote.BadRequestException;

import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.dto.PostDTO;

public interface PostService {
	PostDTO save(PostDTO postDTO, User user) throws BadRequestException;

	PostDTO partialUpdate(PostDTO postDTO, User user) throws BadRequestException;

	PostDTO findById(Long id, User user);

	List<PostDTO> findByAuthor(Long id, User user);

	List<PostDTO> findAllAvailablePosts(User user);

	void delete(Long id, User user);
}
