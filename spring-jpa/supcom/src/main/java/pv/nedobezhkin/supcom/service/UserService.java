package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import pv.nedobezhkin.supcom.service.dto.UserDTO;

public interface UserService extends UserDetailsService {

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	UserDTO save(UserDTO userDTO);

	UserDTO update(UserDTO userDTO);

	UserDTO partialUpdate(UserDTO userDTO);

	Optional<UserDTO> findById(Long id);

	List<UserDTO> findAll();

	void delete(Long id);
}
