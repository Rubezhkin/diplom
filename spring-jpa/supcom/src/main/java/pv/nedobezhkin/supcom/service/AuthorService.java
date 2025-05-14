package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import pv.nedobezhkin.supcom.service.dto.AuthorDTO;

public interface AuthorService {
	AuthorDTO save(AuthorDTO authorDTO);

	AuthorDTO update(AuthorDTO authorDTO);

	AuthorDTO partialUpdate(AuthorDTO authorDTO);

	Optional<AuthorDTO> findById(Long id);

	List<AuthorDTO> findAll();

	void delete(Long id);
}
