package pv.nedobezhkin.supcom.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.UserSubscription;
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

	@Override
	public UserSubscriptionDTO save(UserSubscriptionDTO userSubscriptionDTO) {
		LOG.debug("Request to save UserSubscription: {}", userSubscriptionDTO);
		UserSubscription userSubscription = userSubscriptionMapper.toEntity(userSubscriptionDTO);
		userSubscription = userSubscriptionRepository.save(userSubscription);
		return userSubscriptionMapper.toDto(userSubscription);
	}

	@Override
	public UserSubscriptionDTO update(UserSubscriptionDTO usersubscriptionDTO) {
		LOG.debug("Request to update UserSubscription: {}", usersubscriptionDTO);
		UserSubscription usersubscription = userSubscriptionMapper.toEntity(usersubscriptionDTO);
		usersubscription = userSubscriptionRepository.save(usersubscription);
		return userSubscriptionMapper.toDto(usersubscription);
	}

	@Override
	public UserSubscriptionDTO partialUpdate(UserSubscriptionDTO usersubscriptionDTO) {
		LOG.debug("Request to partically update UserSubscription: {}", usersubscriptionDTO);

		return userSubscriptionRepository
				.findById(usersubscriptionDTO.getId())
				.map(existingUserSubscription -> {
					userSubscriptionMapper.partialUpdate(existingUserSubscription, usersubscriptionDTO);
					return existingUserSubscription;
				})
				.map(userSubscriptionRepository::save)
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
