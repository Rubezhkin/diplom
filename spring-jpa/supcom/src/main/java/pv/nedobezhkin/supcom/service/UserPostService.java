package pv.nedobezhkin.supcom.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;

import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.dto.UserPostDTO;

public interface UserPostService {
	UserPostDTO save(Long postId, User user) throws AccessDeniedException;

	void delete(Long id, User user) throws AccessDeniedException;

	public List<UserPostDTO> findByUser(User user);
}
