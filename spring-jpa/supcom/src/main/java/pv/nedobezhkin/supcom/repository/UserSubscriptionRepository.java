package pv.nedobezhkin.supcom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pv.nedobezhkin.supcom.entity.UserSubscription;

import java.time.ZonedDateTime;
import java.util.List;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.entity.SubscriptionTier;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
	List<UserSubscription> findByUserAndTier(User user, SubscriptionTier tier);

	List<UserSubscription> findAllByUserAndEndDateAfter(User user, ZonedDateTime now);
}