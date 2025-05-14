package pv.nedobezhkin.supcom.service;

import java.util.List;
import java.util.Optional;

import pv.nedobezhkin.supcom.service.dto.SubscriptionTierDTO;

public interface SubscriptionTierService {
	SubscriptionTierDTO save(SubscriptionTierDTO subscriptionTierDTO);

	SubscriptionTierDTO update(SubscriptionTierDTO subscriptionTierDTO);

	SubscriptionTierDTO partialUpdate(SubscriptionTierDTO subscriptionTierDTO);

	Optional<SubscriptionTierDTO> findById(Long id);

	List<SubscriptionTierDTO> findAll();

	void delete(Long id);
}
