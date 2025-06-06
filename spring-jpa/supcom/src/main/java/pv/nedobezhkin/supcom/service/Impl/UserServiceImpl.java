package pv.nedobezhkin.supcom.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Поиск пользователя в репозитории
		return userRepository.findByUsername(username)
				// Если пользователь не найден, выбрасываем исключение
				.orElseThrow(() -> new UsernameNotFoundException("Пользователь с именем " + username + " не найден"));
	}

	@Override
	public boolean existsByUsername(String username) {
		User user = userRepository.findByUsername(username).orElse(null);
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean existsByEmail(String email) {
		User user = userRepository.findByEmail(email).orElse(null);
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	public UserDTO partialUpdate(UserDTO userDTO, User user) {
		LOG.debug("Request to partically update User: {}", userDTO);
		if (userDTO.getPassword() != null) {
			userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		}
		return userRepository
				.findById(user.getId())
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
	public void delete(User user) {
		LOG.debug("Request to delete User: {}", user);
		userRepository.deleteById(user.getId());
	}

	@Override
	public Optional<UserDTO> findByUsername(String username) {
		return userRepository.findByUsername(username).map(userMapper::toDto);
	}

}
