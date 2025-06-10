package pv.nedobezhkin.supcom.service;

import java.util.Optional;

import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.dto.AuthorDTO;

public interface AuthorService {
	AuthorDTO save(AuthorDTO authorDTO, User user);

	AuthorDTO partialUpdate(AuthorDTO authorDTO, User user);

	Optional<AuthorDTO> findById(Long id);

	void delete(User user);

	Optional<AuthorDTO> findByUser(User user);
}
