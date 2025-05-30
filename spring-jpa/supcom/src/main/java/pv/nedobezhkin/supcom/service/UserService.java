package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.dto.UserDTO;

public interface UserService extends UserDetailsService {

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	UserDTO partialUpdate(UserDTO userDTO, User user);

	Optional<UserDTO> findById(Long id);

	Optional<UserDTO> findByUsername(String username);

	List<UserDTO> findAll();

	void delete(User user);
}
