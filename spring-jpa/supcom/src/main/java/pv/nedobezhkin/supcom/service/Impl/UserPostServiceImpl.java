package pv.nedobezhkin.supcom.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.Post;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.entity.UserPost;
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

	@Override
	public UserPostDTO save(UserPostDTO userPostDTO) {
		LOG.debug("Request to save UserPost: {}", userPostDTO);
		UserPost userPost = userPostMapper.toEntity(userPostDTO);
		User user = userRepository.findById(userPostDTO.getUser())
				.orElseThrow(() -> new EntityNotFoundException("user not found"));
		Post post = postRepository.findById(userPostDTO.getPost())
				.orElseThrow(() -> new EntityNotFoundException("post not found"));
		userPost.setUser(user);
		userPost.setPost(post);
		userPost = userPostRepository.save(userPost);
		return userPostMapper.toDto(userPost);
	}

	@Override
	public UserPostDTO update(UserPostDTO userPostDTO) {
		LOG.debug("Request to update UserPost: {}", userPostDTO);
		UserPost userPost = userPostMapper.toEntity(userPostDTO);
		User user = userRepository.findById(userPostDTO.getUser())
				.orElseThrow(() -> new EntityNotFoundException("user not found"));
		Post post = postRepository.findById(userPostDTO.getPost())
				.orElseThrow(() -> new EntityNotFoundException("post not found"));
		userPost.setUser(user);
		userPost.setPost(post);
		userPost = userPostRepository.save(userPost);
		return userPostMapper.toDto(userPost);
	}

	@Override
	public UserPostDTO partialUpdate(UserPostDTO userPostDTO) {
		LOG.debug("Request to partically update UserPost: {}", userPostDTO);

		return userPostRepository
				.findById(userPostDTO.getId())
				.map(existingUserPost -> {
					userPostMapper.partialUpdate(existingUserPost, userPostDTO);
					return existingUserPost;
				})
				.map(userPostRepository::save)
				.map(userPostMapper::toDto).orElse(null);
	}

	@Override
	public Optional<UserPostDTO> findById(Long id) {
		LOG.debug("Request to get UserPost: {}", id);
		return userPostRepository.findById(id).map(userPostMapper::toDto);
	}

	@Override
	public List<UserPostDTO> findAll() {
		LOG.debug("Request to get all UserPosts");
		return userPostRepository.findAll()
				.stream().map(userPostMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		LOG.debug("Request to delete UserPost: {}", id);
		userPostRepository.deleteById(id);
	}

}
