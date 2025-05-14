package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import pv.nedobezhkin.supcom.service.dto.PostDTO;

public interface PostService {
	PostDTO save(PostDTO postDTO);

	PostDTO update(PostDTO postDTO);

	PostDTO partialUpdate(PostDTO postDTO);

	Optional<PostDTO> findById(Long id);

	List<PostDTO> findAll();

	void delete(Long id);
}
