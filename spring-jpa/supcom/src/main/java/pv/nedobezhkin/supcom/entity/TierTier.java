package pv.nedobezhkin.supcom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tier_tier")
public class TierTier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = false)
	private SubscriptionTier parentTier;

	@ManyToOne
	@JoinColumn(name = "child_id", referencedColumnName = "id", nullable = false)
	private SubscriptionTier childTier;
}
