package pv.nedobezhkin.supcom.service.Impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.Author;
import pv.nedobezhkin.supcom.entity.SubscriptionTier;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.entity.UserSubscription;
import pv.nedobezhkin.supcom.repository.AuthorRepository;
import pv.nedobezhkin.supcom.repository.SubscriptionTierRepository;
import pv.nedobezhkin.supcom.repository.UserRepository;
import pv.nedobezhkin.supcom.repository.UserSubscriptionRepository;
import pv.nedobezhkin.supcom.service.UserSubscriptionService;
import pv.nedobezhkin.supcom.service.dto.UserSubscriptionDTO;
import pv.nedobezhkin.supcom.service.mapper.UserSubscriptionMapper;

@Service
@RequiredArgsConstructor
public class UserSubscriptionServiceImpl implements UserSubscriptionService {

	private static final Logger LOG = LoggerFactory.getLogger(UserSubscriptionServiceImpl.class);
	private final UserSubscriptionRepository userSubscriptionRepository;
	private final UserSubscriptionMapper userSubscriptionMapper;
	private final UserRepository userRepository;
	private final SubscriptionTierRepository subscriptionTierRepository;
	private final AuthorRepository authorRepository;

	@Override
	@Transactional
	public UserSubscriptionDTO save(Long subId, User user) throws BadRequestException {
		LOG.debug("Request to save UserSubscription: {}, {}", subId, user);
		SubscriptionTier subscriptionTier = subscriptionTierRepository.findById(subId)
				.orElseThrow(() -> new EntityNotFoundException("subscription not found"));
		Author author = authorRepository.findById(subscriptionTier.getAuthor().getId())
				.orElseThrow(() -> new EntityNotFoundException("author not found"));
		if (author.getOwner().getId().equals(user.getId())) {
			throw new BadRequestException("user - owner of this tier");
		}
		if (subscriptionTier.getPrice() != null) {
			if (user.getBalance().compareTo(subscriptionTier.getPrice()) < 0) {
				throw new BadRequestException("not enough money");
			}
			if (hasActiveSubscription(user, subscriptionTier)) {
				throw new BadRequestException("subcription is active");
			}
			user.setBalance(user.getBalance().subtract(subscriptionTier.getPrice()));
			userRepository.save(user);

			User seller = userRepository.findById(author.getOwner().getId())
					.orElseThrow(() -> new EntityNotFoundException("author's owner not found"));
			seller.setBalance(seller.getBalance().add(subscriptionTier.getPrice()));
			userRepository.save(seller);
		}

		UserSubscription subscription = new UserSubscription();
		subscription.setUser(user);
		subscription.setTier(subscriptionTier);
		subscription.setStartDate(ZonedDateTime.now());
		subscription.setEndDate(ZonedDateTime.now().plusMonths(1));

		subscription = userSubscriptionRepository.save(subscription);
		return userSubscriptionMapper.toDto(subscription);
	}

	@Override
	public List<UserSubscriptionDTO> findByUser(User user) {
		LOG.debug("Request to get all UserSubscriptions");
		return userSubscriptionRepository.findAllByUserAndEndDateAfter(user, ZonedDateTime.now())
				.stream().map(userSubscriptionMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id, User user) throws BadRequestException {
		LOG.debug("Request to delete UserSubscription: {}", id);
		UserSubscription userSubscription = userSubscriptionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("userSub not found"));
		if (user.isAdmin() || userSubscription.getUser().getId().equals(user.getId())) {
			userSubscriptionRepository.deleteById(id);
		} else
			throw new BadRequestException("it not user's userSubscription");
	}

	public boolean hasActiveSubscription(User user, SubscriptionTier tier) {
		return userSubscriptionRepository
				.findByUserAndTier(user, tier)
				.stream()
				.anyMatch(sub -> sub.getEndDate().isAfter(ZonedDateTime.now()));
	}
}
