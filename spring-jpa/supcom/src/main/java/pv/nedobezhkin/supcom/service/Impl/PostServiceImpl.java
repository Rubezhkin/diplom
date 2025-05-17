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
import pv.nedobezhkin.supcom.entity.Author;
import pv.nedobezhkin.supcom.entity.Post;
import pv.nedobezhkin.supcom.entity.SubscriptionTier;
import pv.nedobezhkin.supcom.repository.AuthorRepository;
import pv.nedobezhkin.supcom.repository.PostRepository;
import pv.nedobezhkin.supcom.repository.SubscriptionTierRepository;
import pv.nedobezhkin.supcom.service.PostService;
import pv.nedobezhkin.supcom.service.dto.PostDTO;
import pv.nedobezhkin.supcom.service.mapper.PostMapper;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

	private static final Logger LOG = LoggerFactory.getLogger(PostServiceImpl.class);
	private final PostRepository postRepository;
	private final PostMapper postMapper;
	private final AuthorRepository authorRepository;
	private final SubscriptionTierRepository subscriptionTierRepository;

	@Override
	public PostDTO save(PostDTO postDTO) {
		LOG.debug("Request to save Post: {}", postDTO);
		Post post = postMapper.toEntity(postDTO);
		Author author = authorRepository.findById(postDTO.getAuthor())
				.orElseThrow(() -> new EntityNotFoundException("Author not found"));
		SubscriptionTier tier;
		if (postDTO.getTier() == null)
			tier = null;
		else
			tier = subscriptionTierRepository.findById(postDTO.getTier())
					.orElseThrow(() -> new EntityNotFoundException("Tier not found"));
		post.setAuthor(author);
		post.setTier(tier);
		post.setCreationTime(ZonedDateTime.now());
		post = postRepository.save(post);
		return postMapper.toDto(post);
	}

	@Override
	public PostDTO update(PostDTO postDTO) {
		LOG.debug("Request to update Post: {}", postDTO);
		Post post = postMapper.toEntity(postDTO);
		Author author = authorRepository.findById(postDTO.getAuthor())
				.orElseThrow(() -> new EntityNotFoundException("Author not found"));
		SubscriptionTier tier;
		if (postDTO.getTier() == null)
			tier = null;
		else
			tier = subscriptionTierRepository.findById(postDTO.getTier())
					.orElseThrow(() -> new EntityNotFoundException("Tier not found"));
		post.setAuthor(author);
		post.setTier(tier);
		post = postRepository.save(post);
		return postMapper.toDto(post);
	}

	@Override
	public PostDTO partialUpdate(PostDTO postDTO) {
		LOG.debug("Request to partically update Post: {}", postDTO);

		return postRepository
				.findById(postDTO.getId())
				.map(existingPost -> {
					postMapper.partialUpdate(existingPost, postDTO);
					return existingPost;
				})
				.map(postRepository::save)
				.map(postMapper::toDto).orElse(null);
	}

	@Override
	public Optional<PostDTO> findById(Long id) {
		LOG.debug("Request to get Post: {}", id);
		return postRepository.findById(id).map(postMapper::toDto);
	}

	@Override
	public List<PostDTO> findAll() {
		LOG.debug("Request to get all Posts");
		return postRepository.findAll()
				.stream().map(postMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		LOG.debug("Request to delete Post: {}", id);
		postRepository.deleteById(id);
	}
}
