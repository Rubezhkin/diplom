package pv.nedobezhkin.supcom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pv.nedobezhkin.supcom.entity.SubscriptionTier;

@Repository
public interface SubscriptionTierRepository extends JpaRepository<SubscriptionTier, Long> {

}
