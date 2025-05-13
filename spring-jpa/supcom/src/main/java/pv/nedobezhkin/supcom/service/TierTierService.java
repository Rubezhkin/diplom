package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import pv.nedobezhkin.supcom.service.dto.TierTierDTO;

public interface TierTierService {
	TierTierDTO save(TierTierDTO tierTierDTO);

	TierTierDTO update(Long id, TierTierDTO tierTierDTO);

	TierTierDTO findOne(Long id);

	Optional<TierTierDTO> findById(Long id);

	List<TierTierDTO> findAll();

	void delete(Long id);
}
