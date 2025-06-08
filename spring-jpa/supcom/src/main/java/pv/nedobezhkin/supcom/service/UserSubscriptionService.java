package pv.nedobezhkin.supcom.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;

import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.dto.UserSubscriptionDTO;

public interface UserSubscriptionService {
	UserSubscriptionDTO save(Long subId, User user) throws AccessDeniedException;

	List<UserSubscriptionDTO> findByUser(User user);

	void delete(Long id, User user) throws AccessDeniedException;
}
