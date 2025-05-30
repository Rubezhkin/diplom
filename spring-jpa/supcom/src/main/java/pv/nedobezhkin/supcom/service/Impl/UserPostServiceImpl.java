package pv.nedobezhkin.supcom.service.Impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.Author;
import pv.nedobezhkin.supcom.entity.Post;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.entity.UserPost;
import pv.nedobezhkin.supcom.repository.AuthorRepository;
import pv.nedobezhkin.supcom.repository.PostRepository;
import pv.nedobezhkin.supcom.repository.UserPostRepository;
import pv.nedobezhkin.supcom.repository.UserRepository;
import pv.nedobezhkin.supcom.service.UserPostService;
import pv.nedobezhkin.supcom.service.dto.UserPostDTO;
import pv.nedobezhkin.supcom.service.mapper.UserPostMapper;

@Service
@RequiredArgsConstructor
public class UserPostServiceImpl implements UserPostService {
	private static final Logger LOG = LoggerFactory.getLogger(UserPostServiceImpl.class);
	private final UserPostRepository userPostRepository;
	private final UserPostMapper userPostMapper;
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final AuthorRepository authorRepository;

	@Override
	@Transactional
	public UserPostDTO save(Long postId, User user) throws BadRequestException {
		LOG.debug("Request to save UserPost: {} {}", postId, user);
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new EntityNotFoundException("post not found"));
		Author author = authorRepository.findById(post.getAuthor().getId())
				.orElseThrow(() -> new EntityNotFoundException("author not found"));
		if (post.getPrice() == null) {
			throw new IllegalArgumentException("This post is not for sale");
		}
		if (author.getOwner().equals(user)) {
			throw new BadRequestException("user - owner of this post");
		}
		BigDecimal price = post.getPrice();
		if (user.getBalance().compareTo(price) < 0) {
			throw new IllegalArgumentException("Not enough balance");
		}
		if (userPostRepository.existsByUserIdAndPostId(postId, postId)) {
			throw new IllegalArgumentException("Post already purchased");
		}
		user.setBalance(user.getBalance().subtract(price));
		User seller = userRepository.getById(author.getOwner().getId());
		seller.setBalance(seller.getBalance().add(price));

		userRepository.save(user);
		userRepository.save(seller);

		UserPost userPost = new UserPost();
		userPost.setPost(post);
		userPost.setUser(user);

		UserPost saved = userPostRepository.save(userPost);

		return userPostMapper.toDto(saved);
	}

	@Override
	public List<UserPostDTO> findByUser(User user) {
		return userPostRepository.findAllByUserId(user.getId())
				.stream().map(userPostMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id, User user) {
		LOG.debug("Request to delete UserPost: {}", id);
		UserPost userPost = userPostRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("userPost not found"));
		if (userPost.getUser().getId().equals(user.getId()))
			userPostRepository.deleteById(id);
	}

}
