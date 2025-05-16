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
			@Valid @RequestBody SubscriptionTierDTO subscriptiontierDTO) {
		LOG.debug("REST request to save SubscriptionTier: {}", subscriptiontierDTO);
		SubscriptionTierDTO result = subscriptiontierService.save(subscriptiontierDTO);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("")
	public ResponseEntity<List<SubscriptionTierDTO>> getAllSubscriptionTiers() {
		LOG.debug("REST request to get all SubscriptionTiers");
		List<SubscriptionTierDTO> result = subscriptiontierService.findAll();
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<SubscriptionTierDTO> getSubscriptionTierById(@PathVariable Long id) {
		LOG.debug("REST request to get SubscriptionTier: {}", id);
		SubscriptionTierDTO result = subscriptiontierService.findById(id).orElse(null);
		return ResponseEntity.ok().body(result);
	}

	@PutMapping("/{id}")
	public ResponseEntity<SubscriptionTierDTO> updateSubscriptionTier(@PathVariable Long id,
			@Valid @RequestBody SubscriptionTierDTO subscriptiontierDTO)
			throws BadRequestException {
		LOG.debug("REST request to put SubscriptionTier: {} {}", id, subscriptiontierDTO);
		if (subscriptiontierDTO.getId() == null) {
			throw new BadRequestException("id null");
		}
		if (!Objects.equals(subscriptiontierDTO.getId(), id)) {
			throw new BadRequestException("id invalid");
		}
		if (subscriptiontierService.findById(id) == null) {
			throw new BadRequestException("id not found");
		}

		SubscriptionTierDTO result = subscriptiontierService.update(subscriptiontierDTO);
		return ResponseEntity.ok().body(result);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<SubscriptionTierDTO> partialUpdateSubscriptionTier(@PathVariable Long id,
			@NotNull @RequestBody SubscriptionTierDTO subscriptiontierDTO) throws BadRequestException {
		LOG.debug("REST request to patch SubscriptionTier: {} {}", id, subscriptiontierDTO);
		if (subscriptiontierService.findById(id) == null) {
			throw new BadRequestException("id not found");
		}
		subscriptiontierDTO.setId(id);
		SubscriptionTierDTO result = subscriptiontierService.partialUpdate(subscriptiontierDTO);

		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSubscriptionTier(@PathVariable Long id) {
		LOG.debug("REST request to delete subscriptiontier: {}", id);
		subscriptiontierService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
