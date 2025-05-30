package pv.nedobezhkin.supcom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pv.nedobezhkin.supcom.entity.SubscriptionTier;
import java.util.List;

@Repository
public interface SubscriptionTierRepository extends JpaRepository<SubscriptionTier, Long> {
	List<SubscriptionTier> findAllByAuthorId(Long id);
}
