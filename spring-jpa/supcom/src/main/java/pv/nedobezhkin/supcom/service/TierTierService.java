package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;

import pv.nedobezhkin.supcom.service.dto.TierTierDTO;

public interface TierTierService {
	TierTierDTO save(TierTierDTO tierTierDTO) throws BadRequestException;

	TierTierDTO update(TierTierDTO tierTierDTO) throws BadRequestException;

	TierTierDTO partialUpdate(TierTierDTO tierTierDTO) throws BadRequestException;

	Optional<TierTierDTO> findById(Long id);

	List<TierTierDTO> findAll();

	void delete(Long id);
}
