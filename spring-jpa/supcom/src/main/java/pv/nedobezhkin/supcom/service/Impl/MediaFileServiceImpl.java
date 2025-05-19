package pv.nedobezhkin.supcom.service.Impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.MediaFile;
import pv.nedobezhkin.supcom.entity.Post;
import pv.nedobezhkin.supcom.repository.MediaFileRepository;
import pv.nedobezhkin.supcom.repository.PostRepository;
import pv.nedobezhkin.supcom.service.MediaFileService;
import pv.nedobezhkin.supcom.service.dto.MediaFileDTO;
import pv.nedobezhkin.supcom.service.mapper.MediaFileMapper;

@Service
@RequiredArgsConstructor
public class MediaFileServiceImpl implements MediaFileService {

	private static final Logger LOG = LoggerFactory.getLogger(MediaFileServiceImpl.class);
	private final MediaFileRepository mediafileRepository;
	private final MediaFileMapper mediafileMapper;
	private final PostRepository postRepository;

	@Override
	public MediaFileDTO save(MediaFileDTO mediafileDTO) {
		LOG.debug("Request to save MediaFile: {}", mediafileDTO);
		MediaFile mediafile = mediafileMapper.toEntity(mediafileDTO);
		Post post = postRepository.findById(mediafileDTO.getPost())
				.orElseThrow(() -> new EntityNotFoundException("Post not found"));
		mediafile.setPost(post);
		mediafile.setUploadedAt(ZonedDateTime.now());
		mediafile = mediafileRepository.save(mediafile);
		return mediafileMapper.toDto(mediafile);
	}

	@Override
	public MediaFileDTO update(MediaFileDTO mediafileDTO) {
		LOG.debug("Request to update MediaFile: {}", mediafileDTO);
		MediaFile mediafile = mediafileMapper.toEntity(mediafileDTO);
		Post post = postRepository.findById(mediafileDTO.getPost())
				.orElseThrow(() -> new EntityNotFoundException("Post not found"));
		mediafile.setPost(post);
		mediafile = mediafileRepository.save(mediafile);
		return mediafileMapper.toDto(mediafile);
	}

	@Override
	public MediaFileDTO partialUpdate(MediaFileDTO mediafileDTO) {
		LOG.debug("Request to partically update MediaFile: {}", mediafileDTO);

		return mediafileRepository
				.findById(mediafileDTO.getId())
				.map(existingMediaFile -> {
					mediafileMapper.partialUpdate(existingMediaFile, mediafileDTO);
					return existingMediaFile;
				})
				.map(mediafileRepository::save)
				.map(mediafileMapper::toDto).orElse(null);
	}

	@Override
	public Optional<MediaFileDTO> findById(Long id) {
		LOG.debug("Request to get MediaFile: {}", id);
		return mediafileRepository.findById(id).map(mediafileMapper::toDto);
	}

	@Override
	public List<MediaFileDTO> findAll() {
		LOG.debug("Request to get all MediaFiles");
		return mediafileRepository.findAll()
				.stream().map(mediafileMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		LOG.debug("Request to delete MediaFile: {}", id);
		mediafileRepository.deleteById(id);
	}

}
