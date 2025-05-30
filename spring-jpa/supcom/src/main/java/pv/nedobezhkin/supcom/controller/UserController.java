package pv.nedobezhkin.supcom.controller;

import java.util.List;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.UserService;
import pv.nedobezhkin.supcom.service.dto.BalanceResponseDto;
import pv.nedobezhkin.supcom.service.dto.UserDTO;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		LOG.debug("REST request to get all Users");
		List<UserDTO> result = userService.findAll();
		return ResponseEntity.ok().body(result);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		LOG.debug("REST request to get User: {}", id);
		UserDTO result = userService.findById(id).orElse(null);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/me")
	public ResponseEntity<UserDTO> getUserById(@AuthenticationPrincipal User user) {
		UserDTO result = userService.findById(user.getId()).orElse(null);
		return ResponseEntity.ok().body(result);
	}

	@PatchMapping("/balance")
	@Transactional
	public ResponseEntity<UserDTO> topUpBalance(@RequestBody BalanceResponseDto sum,
			@AuthenticationPrincipal User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setBalance(sum.getSum().add(user.getBalance()));
		userDTO.setId(user.getId());
		UserDTO result = userService.partialUpdate(userDTO, user);

		return ResponseEntity.ok().body(result);
	}

	@PatchMapping("")
	public ResponseEntity<UserDTO> partialUpdateUser(
			@NotNull @RequestBody UserDTO userDTO, @AuthenticationPrincipal User user) throws BadRequestException {
		LOG.debug("REST request to patch User: {} {}", user, userDTO);

		UserDTO result = userService.partialUpdate(userDTO, user);

		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("")
	public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal User user) {
		LOG.debug("REST request to delete user: {}", user);
		userService.delete(user);
		return ResponseEntity.noContent().build();
	}
}
