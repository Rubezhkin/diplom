package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import pv.nedobezhkin.supcom.service.dto.UserDTO;

public interface UserService {
	UserDTO save(UserDTO userDTO);

	UserDTO update(Long id, UserDTO userDTO);

	UserDTO findOne(Long id);

	Optional<UserDTO> findById(Long id);

	List<UserDTO> findAll();

	void delete(Long id);
}
