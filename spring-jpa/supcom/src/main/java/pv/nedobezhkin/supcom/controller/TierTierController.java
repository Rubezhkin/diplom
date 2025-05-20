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
			@Valid @RequestBody TierTierDTO TierTierDTO) throws BadRequestException {
		LOG.debug("REST request to save TierTier: {}", TierTierDTO);
		TierTierDTO result = TierTierService.save(TierTierDTO);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("")
	public ResponseEntity<List<TierTierDTO>> getAllTierTiers() {
		LOG.debug("REST request to get all TierTiers");
		List<TierTierDTO> result = TierTierService.findAll();
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TierTierDTO> getTierTierById(@PathVariable Long id) {
		LOG.debug("REST request to get TierTier: {}", id);
		TierTierDTO result = TierTierService.findById(id).orElse(null);
		return ResponseEntity.ok().body(result);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TierTierDTO> updateTierTier(@PathVariable Long id,
			@Valid @RequestBody TierTierDTO TierTierDTO)
			throws BadRequestException {
		LOG.debug("REST request to put TierTier: {} {}", id, TierTierDTO);
		if (TierTierDTO.getId() == null) {
			throw new BadRequestException("id null");
		}
		if (!Objects.equals(TierTierDTO.getId(), id)) {
			throw new BadRequestException("id invalid");
		}
		if (TierTierService.findById(id) == null) {
			throw new BadRequestException("id not found");
		}

		TierTierDTO result = TierTierService.update(TierTierDTO);
		return ResponseEntity.ok().body(result);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<TierTierDTO> partialUpdateTierTier(@PathVariable Long id,
			@NotNull @RequestBody TierTierDTO TierTierDTO) throws BadRequestException {
		LOG.debug("REST request to patch TierTier: {} {}", id, TierTierDTO);
		if (TierTierService.findById(id) == null) {
			throw new BadRequestException("id not found");
		}
		TierTierDTO.setId(id);
		TierTierDTO result = TierTierService.partialUpdate(TierTierDTO);

		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTierTier(@PathVariable Long id) {
		LOG.debug("REST request to delete TierTier: {}", id);
		TierTierService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
