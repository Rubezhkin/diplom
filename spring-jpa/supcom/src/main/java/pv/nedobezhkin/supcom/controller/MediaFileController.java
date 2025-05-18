package pv.nedobezhkin.supcom.controller;

import java.util.List;
import java.util.Objects;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.service.MediaFileService;
import pv.nedobezhkin.supcom.service.dto.MediaFileDTO;

@RestController
@RequestMapping("/api/mediafiles")
@RequiredArgsConstructor
public class MediaFileController {
	private static final Logger LOG = LoggerFactory.getLogger(MediaFileController.class);
	private final MediaFileService mediaFileService;

	@PostMapping("")
	public ResponseEntity<MediaFileDTO> createMediaFile(@Valid @RequestBody MediaFileDTO mediaFileDTO) {
		LOG.debug("REST request to save MediaFile: {}", mediaFileDTO);
		MediaFileDTO result = mediaFileService.save(mediaFileDTO);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("")
	public ResponseEntity<List<MediaFileDTO>> getAllMediaFiles() {
		LOG.debug("REST request to get all MediaFiles");
		List<MediaFileDTO> result = mediaFileService.findAll();
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MediaFileDTO> getMediaFileById(@PathVariable Long id) {
		LOG.debug("REST request to get MediaFile: {}", id);
		MediaFileDTO result = mediaFileService.findById(id).orElse(null);
		return ResponseEntity.ok().body(result);
	}

	@PutMapping("/{id}")
	public ResponseEntity<MediaFileDTO> updateMediaFile(@PathVariable Long id,
			@Valid @RequestBody MediaFileDTO mediaFileDTO)
			throws BadRequestException {
		LOG.debug("REST request to put MediaFile: {} {}", id, mediaFileDTO);
		if (mediaFileDTO.getId() == null) {
			throw new BadRequestException("id null");
		}
		if (!Objects.equals(mediaFileDTO.getId(), id)) {
			throw new BadRequestException("id invalid");
		}
		if (mediaFileService.findById(id) == null) {
			throw new BadRequestException("id not found");
		}

		MediaFileDTO result = mediaFileService.update(mediaFileDTO);
		return ResponseEntity.ok().body(result);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<MediaFileDTO> partialUpdateMediaFile(@PathVariable Long id,
			@NotNull @RequestBody MediaFileDTO mediaFileDTO) throws BadRequestException {
		LOG.debug("REST request to patch MediaFile: {} {}", id, mediaFileDTO);
		if (mediaFileService.findById(id) == null) {
			throw new BadRequestException("id not found");
		}
		mediaFileDTO.setId(id);
		MediaFileDTO result = mediaFileService.partialUpdate(mediaFileDTO);

		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMediaFile(@PathVariable Long id) {
		LOG.debug("REST request to delete mediafile: {}", id);
		mediaFileService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
