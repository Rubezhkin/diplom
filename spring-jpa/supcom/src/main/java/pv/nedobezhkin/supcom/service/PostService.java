package pv.nedobezhkin.supcom.service;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.security.access.AccessDeniedException;

import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.dto.PostDTO;

public interface PostService {
	PostDTO save(PostDTO postDTO, User user) throws AccessDeniedException;

	PostDTO partialUpdate(PostDTO postDTO, User user) throws BadRequestException, AccessDeniedException;

	PostDTO findById(Long id, User user);

	List<PostDTO> findByAuthor(Long id, User user);

	List<PostDTO> findAllAvailablePosts(User user);

	void delete(Long id, User user) throws BadRequestException, AccessDeniedException;
}
