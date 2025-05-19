package pv.nedobezhkin.supcom.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pv.nedobezhkin.supcom.entity.SubscriptionTier;
import pv.nedobezhkin.supcom.entity.TierTier;
import pv.nedobezhkin.supcom.service.dto.TierTierDTO;

@Mapper(componentModel = "spring")
public interface TierTierMapper extends EntityMapper<TierTierDTO, TierTier> {

	@Mapping(source = "parentTier.id", target = "parentTier")
	@Mapping(source = "childTier.id", target = "childTier")
	@Override
	TierTierDTO toDto(TierTier entity);

	@Mapping(target = "parentTier", ignore = true)
	@Mapping(target = "childTier")
	@Override
	TierTier toEntity(TierTierDTO dto);

	default SubscriptionTier mapTierIdToTier(Long tierId) {
		if (tierId == null)
			return null;
		SubscriptionTier tier = new SubscriptionTier();
		tier.setId(tierId);
		return tier;
	}

	default Long mapTierToTierId(SubscriptionTier tier) {
		return tier != null ? tier.getId() : null;
	}

}
