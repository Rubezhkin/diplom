package pv.nedobezhkin.supcom.service;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.Post;
import pv.nedobezhkin.supcom.entity.SubscriptionTier;
import pv.nedobezhkin.supcom.entity.TierTier;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.entity.UserSubscription;
import pv.nedobezhkin.supcom.repository.AuthorRepository;
import pv.nedobezhkin.supcom.repository.TierTierRepository;
import pv.nedobezhkin.supcom.repository.UserPostRepository;
import pv.nedobezhkin.supcom.repository.UserSubscriptionRepository;

@Service
@RequiredArgsConstructor
public class AccessService {

	private final UserPostRepository userPostRepository;
	private final UserSubscriptionRepository userSubscriptionRepository;
	private final TierTierRepository tierTierRepository;
	private final AuthorRepository authorRepository;

	@Transactional(readOnly = true)
	public boolean hasAccessToPost(User user, Post post) {
		if (user.isAdmin()) {
			return true;
		}

		if (post.getAuthor().getId().equals(authorRepository.findByOwner(user).orElse(null).getId())) {
			return true;
		}

		boolean purchased = userPostRepository.existsByUserIdAndPostId(user.getId(), post.getId());
		if (purchased) {
			return true;
		}

		SubscriptionTier postTier = post.getTier();
		List<SubscriptionTier> userTiers = userSubscriptionRepository
				.findAllByUser(user)
				.stream()
				.filter(us -> us.getEndDate().isAfter(ZonedDateTime.now()))
				.map(UserSubscription::getTier)
				.collect(Collectors.toList());

		Set<SubscriptionTier> allAccessibleTiers = new HashSet<>();
		for (SubscriptionTier tier : userTiers) {
			collectAccessibleTiers(tier, allAccessibleTiers, new HashSet<>());
		}

		return allAccessibleTiers.contains(postTier);
	}

	private void collectAccessibleTiers(SubscriptionTier current, Set<SubscriptionTier> result, Set<Long> visited) {
		if (current == null || visited.contains(current.getId()))
			return;

		visited.add(current.getId());
		result.add(current);

		List<TierTier> inherited = tierTierRepository.findByParentTier(current);
		for (TierTier relation : inherited) {
			collectAccessibleTiers(relation.getChildTier(), result, visited);
		}
	}
}