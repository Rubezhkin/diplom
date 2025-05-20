package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;

import pv.nedobezhkin.supcom.service.dto.PostDTO;

public interface PostService {
	PostDTO save(PostDTO postDTO) throws BadRequestException;

	PostDTO update(PostDTO postDTO) throws BadRequestException;

	PostDTO partialUpdate(PostDTO postDTO) throws BadRequestException;

	Optional<PostDTO> findById(Long id);

	List<PostDTO> findAll();

	void delete(Long id);
}
