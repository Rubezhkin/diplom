package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;

import pv.nedobezhkin.supcom.service.dto.UserSubscriptionDTO;

public interface UserSubscriptionService {
	UserSubscriptionDTO save(UserSubscriptionDTO userSubscriptionDTO) throws BadRequestException;

	UserSubscriptionDTO update(UserSubscriptionDTO userSubscriptionDTO) throws BadRequestException;

	UserSubscriptionDTO partialUpdate(UserSubscriptionDTO userSubscriptionDTO) throws BadRequestException;

	Optional<UserSubscriptionDTO> findById(Long id);

	List<UserSubscriptionDTO> findAll();

	void delete(Long id);
}
