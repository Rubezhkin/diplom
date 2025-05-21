package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;

import pv.nedobezhkin.supcom.service.dto.UserPostDTO;

public interface UserPostService {
	UserPostDTO save(UserPostDTO userPostDTO) throws BadRequestException;

	UserPostDTO update(UserPostDTO userPostDTO) throws BadRequestException;

	UserPostDTO partialUpdate(UserPostDTO userPostDTO) throws BadRequestException;

	Optional<UserPostDTO> findById(Long id);

	List<UserPostDTO> findAll();

	void delete(Long id);
}
