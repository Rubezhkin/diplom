package pv.nedobezhkin.supcom.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;

import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.dto.SubscriptionTierDTO;

public interface SubscriptionTierService {
	SubscriptionTierDTO save(SubscriptionTierDTO subscriptionTierDTO, User user);

	SubscriptionTierDTO partialUpdate(SubscriptionTierDTO subscriptionTierDTO, User user) throws AccessDeniedException;

	public List<SubscriptionTierDTO> findAllByAuthor(Long id);

	void delete(Long id, User suer) throws AccessDeniedException;
}
