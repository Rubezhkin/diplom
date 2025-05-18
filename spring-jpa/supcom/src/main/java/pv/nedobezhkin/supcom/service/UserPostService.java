package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import pv.nedobezhkin.supcom.service.dto.UserPostDTO;

public interface UserPostService {
	UserPostDTO save(UserPostDTO userPostDTO);

	UserPostDTO update(UserPostDTO userPostDTO);

	UserPostDTO partialUpdate(UserPostDTO userPostDTO);

	Optional<UserPostDTO> findById(Long id);

	List<UserPostDTO> findAll();

	void delete(Long id);
}
