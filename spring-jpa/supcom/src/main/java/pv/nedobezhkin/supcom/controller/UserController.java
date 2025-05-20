package pv.nedobezhkin.supcom.controller;

import java.util.List;
import java.util.Objects;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.service.UserService;
import pv.nedobezhkin.supcom.service.dto.UserDTO;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
// TODO сделать норм безопасность
public class UserController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;

	@PostMapping("")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
		LOG.debug("REST request to save User: {}", userDTO);
		UserDTO result = userService.save(userDTO);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		LOG.debug("REST request to get all Users");
		List<UserDTO> result = userService.findAll();
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		LOG.debug("REST request to get User: {}", id);
		UserDTO result = userService.findById(id).orElse(null);
		return ResponseEntity.ok().body(result);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO)
			throws BadRequestException {
		LOG.debug("REST request to put User: {} {}", id, userDTO);
		if (userDTO.getId() == null) {
			throw new BadRequestException("id null");
		}
		if (!Objects.equals(userDTO.getId(), id)) {
			throw new BadRequestException("id invalid");
		}
		if (userService.findById(id) == null) {
			throw new BadRequestException("id not found");
		}

		UserDTO result = userService.update(userDTO);
		return ResponseEntity.ok().body(result);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<UserDTO> partialUpdateUser(@PathVariable Long id,
			@NotNull @RequestBody UserDTO userDTO) throws BadRequestException {
		LOG.debug("REST request to patch User: {} {}", id, userDTO);
		if (userService.findById(id) == null) {
			throw new BadRequestException("id not found");
		}
		UserDTO result = userService.partialUpdate(userDTO);

		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		LOG.debug("REST request to delete user: {}", id);
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
