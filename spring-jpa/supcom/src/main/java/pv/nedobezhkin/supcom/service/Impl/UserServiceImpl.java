package pv.nedobezhkin.supcom.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.repository.UserRepository;
import pv.nedobezhkin.supcom.service.UserService;
import pv.nedobezhkin.supcom.service.dto.UserDTO;
import pv.nedobezhkin.supcom.service.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Override
	public UserDTO save(UserDTO userDTO) {
		LOG.debug("Request to save User: {}", userDTO);
		User user = userMapper.toEntity(userDTO);
		user = userRepository.save(user);
		return userMapper.toDto(user);
	}

	@Override
	public UserDTO update(UserDTO userDTO) {
		LOG.debug("Request to update User: {}", userDTO);
		User user = userMapper.toEntity(userDTO);
		user = userRepository.save(user);
		return userMapper.toDto(user);
	}

	@Override
	public UserDTO partialUpdate(UserDTO userDTO) {
		LOG.debug("Request to partically update User: {}", userDTO);

		return userRepository
				.findById(userDTO.getId())
				.map(existingUser -> {
					userMapper.partialUpdate(existingUser, userDTO);
					return existingUser;
				})
				.map(userRepository::save)
				.map(userMapper::toDto).orElse(null);
	}

	@Override
	public Optional<UserDTO> findById(Long id) {
		LOG.debug("Request to get User: {}", id);
		return userRepository.findById(id).map(userMapper::toDto);
	}

	@Override
	public List<UserDTO> findAll() {
		LOG.debug("Request to get all Users");
		return userRepository.findAll()
				.stream().map(userMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		LOG.debug("Request to delete User: {}", id);
		userRepository.deleteById(id);
	}

}
