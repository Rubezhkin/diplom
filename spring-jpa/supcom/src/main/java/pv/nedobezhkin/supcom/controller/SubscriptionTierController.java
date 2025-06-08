package pv.nedobezhkin.supcom.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.SubscriptionTierService;
import pv.nedobezhkin.supcom.service.dto.SubscriptionTierDTO;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionTierController {
	private static final Logger LOG = LoggerFactory.getLogger(SubscriptionTierController.class);
	private final SubscriptionTierService subscriptiontierService;

	@PostMapping("")
	public ResponseEntity<SubscriptionTierDTO> createSubscriptionTier(
			@Valid @RequestBody SubscriptionTierDTO subscriptiontierDTO, @AuthenticationPrincipal User user) {
		LOG.debug("REST request to save SubscriptionTier: {}", subscriptiontierDTO);
		SubscriptionTierDTO result = subscriptiontierService.save(subscriptiontierDTO, user);
		return ResponseEntity.ok().body(result);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<SubscriptionTierDTO> partialUpdateSubscriptionTier(@PathVariable Long id,
			@NotNull @RequestBody SubscriptionTierDTO subscriptiontierDTO, @AuthenticationPrincipal User user)
			throws AccessDeniedException {
		LOG.debug("REST request to patch SubscriptionTier: {} {}", id, subscriptiontierDTO);
		subscriptiontierDTO.setId(id);
		SubscriptionTierDTO result = subscriptiontierService.partialUpdate(subscriptiontierDTO, user);

		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/author/{id}")
	public ResponseEntity<List<SubscriptionTierDTO>> getAuthorsTiers(@PathVariable Long id) {
		LOG.debug("REST request to get SubscriptionTier by author : {}", id);
		try {
			List<SubscriptionTierDTO> result = subscriptiontierService.findAllByAuthor(id);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().header(e.getMessage()).build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSubscriptionTier(@PathVariable Long id, @AuthenticationPrincipal User user)
			throws AccessDeniedException {
		LOG.debug("REST request to delete subscriptiontier: {}", id);

		subscriptiontierService.delete(id, user);
		return ResponseEntity.noContent().build();

	}

}
