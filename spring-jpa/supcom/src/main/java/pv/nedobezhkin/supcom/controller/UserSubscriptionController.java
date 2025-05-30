package pv.nedobezhkin.supcom.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.UserSubscriptionService;
import pv.nedobezhkin.supcom.service.dto.UserSubscriptionDTO;

import java.util.List;

@RestController
@RequestMapping("/api/usersubscriptions")
@RequiredArgsConstructor
public class UserSubscriptionController {

	private final UserSubscriptionService userSubscriptionService;

	@PostMapping("/{id}")
	public ResponseEntity<UserSubscriptionDTO> create(@PathVariable Long id,
			@AuthenticationPrincipal User user) throws BadRequestException {
		UserSubscriptionDTO result = userSubscriptionService.save(id, user);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("")
	public List<UserSubscriptionDTO> getUsersSubscription(@AuthenticationPrincipal User user) {
		return userSubscriptionService.findByUser(user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User user)
			throws BadRequestException {
		try {
			userSubscriptionService.delete(id, user);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().header(e.getMessage()).build();
		}

	}
}
