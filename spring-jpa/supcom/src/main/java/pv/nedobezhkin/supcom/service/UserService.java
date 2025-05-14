package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import pv.nedobezhkin.supcom.service.dto.UserDTO;

public interface UserService {
	UserDTO save(UserDTO userDTO);

	UserDTO update(UserDTO userDTO);

	UserDTO partialUpdate(UserDTO userDTO);

	Optional<UserDTO> findById(Long id);

	List<UserDTO> findAll();

	void delete(Long id);
}
