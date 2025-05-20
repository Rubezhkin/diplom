package pv.nedobezhkin.supcom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pv.nedobezhkin.supcom.entity.SubscriptionTier;
import pv.nedobezhkin.supcom.entity.TierTier;
import java.util.List;

@Repository
public interface TierTierRepository extends JpaRepository<TierTier, Long> {
	List<TierTier> findByChildTier(SubscriptionTier childTier);
}