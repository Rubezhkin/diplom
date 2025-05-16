package pv.nedobezhkin.supcom.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.Author;
import pv.nedobezhkin.supcom.entity.SubscriptionTier;
import pv.nedobezhkin.supcom.repository.AuthorRepository;
import pv.nedobezhkin.supcom.repository.SubscriptionTierRepository;
import pv.nedobezhkin.supcom.service.SubscriptionTierService;
import pv.nedobezhkin.supcom.service.dto.SubscriptionTierDTO;
import pv.nedobezhkin.supcom.service.mapper.SubscriptionTierMapper;

@RequiredArgsConstructor
@Service
public class SubscriptionTierServiceImpl implements SubscriptionTierService {

	private static final Logger LOG = LoggerFactory.getLogger(SubscriptionTierServiceImpl.class);
	private final SubscriptionTierRepository subscriptionTierRepository;
	private final SubscriptionTierMapper subscriptionTierMapper;
	private final AuthorRepository authorRepository;

	@Override
	public SubscriptionTierDTO save(SubscriptionTierDTO subscriptionTierDTO) {
		LOG.debug("Request to save SubscriptionTier: {}", subscriptionTierDTO);
		SubscriptionTier subscriptionTier = subscriptionTierMapper.toEntity(subscriptionTierDTO);
		Author author = authorRepository.findById(subscriptionTierDTO.getAuthor())
				.orElseThrow(() -> new EntityNotFoundException("Author not found"));
		subscriptionTier.setAuthor(author);
		subscriptionTier = subscriptionTierRepository.save(subscriptionTier);
		return subscriptionTierMapper.toDto(subscriptionTier);
	}

	@Override
	public SubscriptionTierDTO update(SubscriptionTierDTO subscriptionTierDTO) {
		LOG.debug("Request to update SubscriptionTier: {}", subscriptionTierDTO);
		SubscriptionTier subscriptionTier = subscriptionTierMapper.toEntity(subscriptionTierDTO);
		Author author = authorRepository.findById(subscriptionTierDTO.getAuthor())
				.orElseThrow(() -> new EntityNotFoundException("Author not found"));
		subscriptionTier.setAuthor(author);
		subscriptionTier = subscriptionTierRepository.save(subscriptionTier);
		return subscriptionTierMapper.toDto(subscriptionTier);
	}

	@Override
	public SubscriptionTierDTO partialUpdate(SubscriptionTierDTO subscriptionTierDTO) {
		LOG.debug("Request to partically update SubscriptionTier: {}", subscriptionTierDTO);

		return subscriptionTierRepository
				.findById(subscriptionTierDTO.getId())
				.map(existingSubscriptionTier -> {
					subscriptionTierMapper.partialUpdate(existingSubscriptionTier, subscriptionTierDTO);
					return existingSubscriptionTier;
				})
				.map(subscriptionTierRepository::save)
				.map(subscriptionTierMapper::toDto).orElse(null);
	}

	@Override
	public Optional<SubscriptionTierDTO> findById(Long id) {
		LOG.debug("Request to get SubscriptionTier: {}", id);
		return subscriptionTierRepository.findById(id).map(subscriptionTierMapper::toDto);
	}

	@Override
	public List<SubscriptionTierDTO> findAll() {
		LOG.debug("Request to get all SubscriptionTiers");
		return subscriptionTierRepository.findAll()
				.stream().map(subscriptionTierMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		LOG.debug("Request to delete SubscriptionTier: {}", id);
		subscriptionTierRepository.deleteById(id);
	}
}
