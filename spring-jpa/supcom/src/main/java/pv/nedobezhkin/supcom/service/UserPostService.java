package pv.nedobezhkin.supcom.service;

import java.util.List;

import org.apache.coyote.BadRequestException;

import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.dto.UserPostDTO;

public interface UserPostService {
	UserPostDTO save(Long postId, User user) throws BadRequestException;

	void delete(Long id, User user);

	public List<UserPostDTO> findByUser(User user);
}
