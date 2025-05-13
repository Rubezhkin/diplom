package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import pv.nedobezhkin.supcom.service.dto.UserSubscriptionDTO;

public interface UserSubscriptionService {
	UserSubscriptionDTO save(UserSubscriptionDTO userSubscriptionDTO);

	UserSubscriptionDTO update(Long id, UserSubscriptionDTO userSubscriptionDTO);

	UserSubscriptionDTO findOne(Long id);

	Optional<UserSubscriptionDTO> findById(Long id);

	List<UserSubscriptionDTO> findAll();

	void delete(Long id);
}
