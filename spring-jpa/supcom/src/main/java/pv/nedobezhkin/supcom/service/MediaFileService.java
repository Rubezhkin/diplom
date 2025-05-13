package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import pv.nedobezhkin.supcom.service.dto.MediaFileDTO;

public interface MediaFileService {
	MediaFileDTO save(MediaFileDTO mediaFileDTO);

	MediaFileDTO update(Long id, MediaFileDTO mediaFileDTO);

	MediaFileDTO findOne(Long id);

	Optional<MediaFileDTO> findById(Long id);

	List<MediaFileDTO> findAll();

	void delete(Long id);
}
