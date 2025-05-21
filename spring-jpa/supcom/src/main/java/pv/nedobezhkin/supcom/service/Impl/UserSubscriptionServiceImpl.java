package pv.nedobezhkin.supcom.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
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
	public UserSubscriptionDTO save(UserSubscriptionDTO userSubscriptionDTO) throws BadRequestException {
		LOG.debug("Request to save UserSubscription: {}", userSubscriptionDTO);
		UserSubscription userSubscription = userSubscriptionMapper.toEntity(userSubscriptionDTO);
		User user = userRepository.findById(userSubscriptionDTO.getUser())
				.orElseThrow(() -> new EntityNotFoundException("user not found"));
		SubscriptionTier subscriptionTier = subscriptionTierRepository.findById(userSubscriptionDTO.getTier())
				.orElseThrow(() -> new EntityNotFoundException("subscription not found"));
		Author author = authorRepository.findById(subscriptionTier.getAuthor().getId())
				.orElseThrow(() -> new EntityNotFoundException("author not found"));
		if (author.getOwner().equals(user)) {
			throw new BadRequestException("user - owner of this tier");
		}
		userSubscription.setUser(user);
		userSubscription.setTier(subscriptionTier);
		userSubscription = userSubscriptionRepository.save(userSubscription);
		return userSubscriptionMapper.toDto(userSubscription);
	}

	@Override
	public UserSubscriptionDTO update(UserSubscriptionDTO userSubscriptionDTO) throws BadRequestException {
		LOG.debug("Request to update UserSubscription: {}", userSubscriptionDTO);
		UserSubscription userSubscription = userSubscriptionMapper.toEntity(userSubscriptionDTO);
		User user = userRepository.findById(userSubscriptionDTO.getUser())
				.orElseThrow(() -> new EntityNotFoundException("user not found"));
		SubscriptionTier subscriptionTier = subscriptionTierRepository.findById(userSubscriptionDTO.getTier())
				.orElseThrow(() -> new EntityNotFoundException("subscription not found"));
		Author author = authorRepository.findById(subscriptionTier.getAuthor().getId())
				.orElseThrow(() -> new EntityNotFoundException("author not found"));
		if (author.getOwner().equals(user)) {
			throw new BadRequestException("user - owner of this tier");
		}
		userSubscription.setUser(user);
		userSubscription.setTier(subscriptionTier);
		userSubscription = userSubscriptionRepository.save(userSubscription);
		return userSubscriptionMapper.toDto(userSubscription);
	}

	@Override
	public UserSubscriptionDTO partialUpdate(UserSubscriptionDTO usersubscriptionDTO) throws BadRequestException {
		LOG.debug("Request to partically update UserSubscription: {}", usersubscriptionDTO);

		UserSubscriptionDTO result = userSubscriptionRepository
				.findById(usersubscriptionDTO.getId())
				.map(existingUserSubscription -> {
					userSubscriptionMapper.partialUpdate(existingUserSubscription, usersubscriptionDTO);
					return existingUserSubscription;
				}).map(userSubscriptionMapper::toDto)
				.orElse(null);
		User user = userRepository.findById(result.getUser())
				.orElseThrow(() -> new EntityNotFoundException("user not found"));
		SubscriptionTier subscriptionTier = subscriptionTierRepository.findById(result.getTier())
				.orElseThrow(() -> new EntityNotFoundException("subscription not found"));
		Author author = authorRepository.findById(subscriptionTier.getAuthor().getId())
				.orElseThrow(() -> new EntityNotFoundException("author not found"));
		if (author.getOwner().equals(user)) {
			throw new BadRequestException("user - owner of this tier");
		}
		return result = userSubscriptionRepository
				.findById(result.getId()).map(userSubscriptionRepository::save)
				.map(userSubscriptionMapper::toDto).orElse(null);
	}

	@Override
	public Optional<UserSubscriptionDTO> findById(Long id) {
		LOG.debug("Request to get UserSubscription: {}", id);
		return userSubscriptionRepository.findById(id).map(userSubscriptionMapper::toDto);
	}

	@Override
	public List<UserSubscriptionDTO> findAll() {
		LOG.debug("Request to get all UserSubscriptions");
		return userSubscriptionRepository.findAll()
				.stream().map(userSubscriptionMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		LOG.debug("Request to delete UserSubscription: {}", id);
		userSubscriptionRepository.deleteById(id);
	}

}
