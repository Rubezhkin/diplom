package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import pv.nedobezhkin.supcom.service.dto.PostDTO;

public interface PostService {
	PostDTO save(PostDTO postDTO);

	PostDTO update(Long id, PostDTO postDTO);

	PostDTO findOne(Long id);

	Optional<PostDTO> findById(Long id);

	List<PostDTO> findAll();

	void delete(Long id);
}
