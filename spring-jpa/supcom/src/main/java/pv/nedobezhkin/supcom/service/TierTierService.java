package pv.nedobezhkin.supcom.service;

import java.util.List;

import org.apache.coyote.BadRequestException;

import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.dto.TierTierDTO;

public interface TierTierService {
	TierTierDTO save(TierTierDTO tierTierDTO, User user) throws BadRequestException;

	List<TierTierDTO> findAllByAuthor(User user);

	void delete(Long id, User user) throws BadRequestException;
}
