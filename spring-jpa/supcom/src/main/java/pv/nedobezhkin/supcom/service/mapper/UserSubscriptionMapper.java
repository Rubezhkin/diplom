package pv.nedobezhkin.supcom.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pv.nedobezhkin.supcom.entity.SubscriptionTier;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.entity.UserSubscription;
import pv.nedobezhkin.supcom.service.dto.UserSubscriptionDTO;

@Mapper(componentModel = "spring")
public interface UserSubscriptionMapper extends EntityMapper<UserSubscriptionDTO, UserSubscription> {
	@Mapping(source = "user.id", target = "user")
	@Mapping(source = "tier.id", target = "tier")
	@Override
	UserSubscriptionDTO toDto(UserSubscription entity);

	@Mapping(target = "user", ignore = true)
	@Mapping(target = "tier", ignore = true)
	@Override
	UserSubscription toEntity(UserSubscriptionDTO dto);

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

	default User mapUserIdToUser(Long userId) {
		if (userId == null)
			return null;
		User user = new User();
		user.setId(userId);
		return user;
	}

	default Long mapUserToUserId(User user) {
		return user != null ? user.getId() : null;
	}
}
