package pv.nedobezhkin.supcom.service.Impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.Author;
import pv.nedobezhkin.supcom.entity.Post;
import pv.nedobezhkin.supcom.entity.SubscriptionTier;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.repository.AuthorRepository;
import pv.nedobezhkin.supcom.repository.PostRepository;
import pv.nedobezhkin.supcom.repository.SubscriptionTierRepository;
import pv.nedobezhkin.supcom.service.AccessService;
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
	private final AccessService accessService;

	@Override
	public PostDTO save(PostDTO postDTO, User user) throws AccessDeniedException {
		LOG.debug("Request to save Post: {}", postDTO);
		Post post = postMapper.toEntity(postDTO);
		Author author = authorRepository.findByOwner(user)
				.orElseThrow(() -> new EntityNotFoundException("Author not found"));
		SubscriptionTier tier;
		if (postDTO.getTier() == null)
			tier = null;
		else
			tier = subscriptionTierRepository.findById(postDTO.getTier())
					.orElseThrow(() -> new EntityNotFoundException("Tier not found"));
		if (tier != null && !tier.getAuthor().equals(author))
			throw new AccessDeniedException("the tier doesn't belong to the author");
		post.setAuthor(author);
		post.setTier(tier);
		post.setCreationTime(ZonedDateTime.now());
		post = postRepository.save(post);
		return postMapper.toDto(post);
	}

	@Override
	public PostDTO partialUpdate(PostDTO postDTO, User user) throws BadRequestException, AccessDeniedException {
		LOG.debug("Request to partically update Post: {}", postDTO);

		Post existingPost = postRepository.findById(postDTO.getId())
				.orElseThrow(() -> new EntityNotFoundException("Post not found"));

		Author author = authorRepository.findByOwner(user)
				.orElseThrow(() -> new EntityNotFoundException("Author not found"));

		if (!existingPost.getAuthor().getId().equals(author.getId())) {
			throw new AccessDeniedException("User is not the author of this post");
		}

		SubscriptionTier tier;
		if (postDTO.getTier() != null && postDTO.getTier() == 0) {
			tier = null; // сбросить уровень подписки
		} else if (postDTO.getTier() != null) {
			tier = subscriptionTierRepository.findById(postDTO.getTier())
					.orElseThrow(() -> new EntityNotFoundException("Tier not found"));
			if (!tier.getAuthor().equals(author)) {
				throw new AccessDeniedException("The tier doesn't belong to the author");
			}
		} else {
			tier = existingPost.getTier(); // оставить без изменений
		}

		postMapper.partialUpdate(existingPost, postDTO);
		existingPost.setTier(tier);
		Post saved = postRepository.save(existingPost);
		return postMapper.toDto(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public PostDTO findById(Long id, User user) {
		LOG.debug("Request to get Post: {}", id);
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Post not found"));

		boolean access = accessService.hasAccessToPost(user, post);
		return postMapper.toDto(post, access);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PostDTO> findByAuthor(Long id, User user) {
		return postRepository.findAllByAuthorId(id)
				.stream()
				.map(post -> {
					boolean access = accessService.hasAccessToPost(user, post);
					return postMapper.toDto(post, access);
				})
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<PostDTO> findAllAvailablePosts(User user) {
		LOG.debug("Request to get all Posts");
		return postRepository.findPostBySubscriptions(user)
				.stream().map(post -> {
					boolean access = accessService.hasAccessToPost(user, post);
					return postMapper.toDto(post, access);
				})
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id, User user) throws AccessDeniedException {
		LOG.debug("Request to delete Post: {}", id);
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("post not found"));
		Author author = authorRepository.findByOwner(user).orElse(null);
		if (post.getAuthor().getId().equals(author.getId()))
			postRepository.deleteById(id);
		else
			throw new AccessDeniedException("it's not user's post");
	}

}
