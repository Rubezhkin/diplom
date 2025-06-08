package pv.nedobezhkin.supcom.service;

import java.util.List;

import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.dto.AuthorDTO;

public interface UserAuthorService {
	void followAuthor(Long authorId, User user);

	void unfollowAuthor(Long authorId, User user);

	List<AuthorDTO> getFollowedAuthors(User user);
}
