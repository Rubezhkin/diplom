package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import pv.nedobezhkin.supcom.service.dto.TierTierDTO;

public interface TierTierService {
	TierTierDTO save(TierTierDTO tierTierDTO);

	TierTierDTO update(TierTierDTO tierTierDTO);

	TierTierDTO partialUpdate(TierTierDTO tierTierDTO);

	Optional<TierTierDTO> findById(Long id);

	List<TierTierDTO> findAll();

	void delete(Long id);
}
