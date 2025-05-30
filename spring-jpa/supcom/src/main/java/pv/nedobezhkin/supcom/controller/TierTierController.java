package pv.nedobezhkin.supcom.controller;

import java.util.List;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.TierTierService;
import pv.nedobezhkin.supcom.service.dto.TierTierDTO;

@RestController
@RequestMapping("/api/tiertiers")
@RequiredArgsConstructor
public class TierTierController {
	private static final Logger LOG = LoggerFactory.getLogger(TierTierController.class);
	private final TierTierService TierTierService;

	@PostMapping("")
	public ResponseEntity<TierTierDTO> createTierTier(
			@Valid @RequestBody TierTierDTO TierTierDTO,
			@AuthenticationPrincipal User user) throws BadRequestException {
		LOG.debug("REST request to save TierTier: {}", TierTierDTO);
		TierTierDTO result = TierTierService.save(TierTierDTO, user);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("")
	public ResponseEntity<List<TierTierDTO>> getAllTierTiersByUser(@AuthenticationPrincipal User user) {
		LOG.debug("REST request to get all TierTiers");
		List<TierTierDTO> result = TierTierService.findAllByAuthor(user);
		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTierTier(@PathVariable Long id, @AuthenticationPrincipal User user)
			throws BadRequestException {
		LOG.debug("REST request to delete TierTier: {}", id);
		try {
			TierTierService.delete(id, user);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().header(e.getMessage()).build();
		}

	}
}
