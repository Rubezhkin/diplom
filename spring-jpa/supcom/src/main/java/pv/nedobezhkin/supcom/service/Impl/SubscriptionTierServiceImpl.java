package pv.nedobezhkin.supcom.service.Impl;

import java.util.List;
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
	public SubscriptionTierDTO save(SubscriptionTierDTO subscriptionTierDTO, User user) {
		LOG.debug("Request to save SubscriptionTier: {}", subscriptionTierDTO);
		SubscriptionTier subscriptionTier = subscriptionTierMapper.toEntity(subscriptionTierDTO);
		Author author = authorRepository.findByOwner(user)
				.orElseThrow(() -> new EntityNotFoundException("Author not found"));
		subscriptionTier.setAuthor(author);
		subscriptionTier = subscriptionTierRepository.save(subscriptionTier);
		return subscriptionTierMapper.toDto(subscriptionTier);
	}

	@Override
	public SubscriptionTierDTO partialUpdate(SubscriptionTierDTO dto, User user) throws BadRequestException {
		LOG.debug("Request to partially update SubscriptionTier: {}", dto);

		SubscriptionTier existing = subscriptionTierRepository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("SubscriptionTier not found"));

		Author author = authorRepository.findByOwner(user).orElse(null);
		if (!existing.getAuthor().getId().equals(author.getId())) {
			throw new BadRequestException("Access denied: not the owner of this tier.");
		}

		subscriptionTierMapper.partialUpdate(existing, dto);
		existing = subscriptionTierRepository.save(existing);
		return subscriptionTierMapper.toDto(existing);
	}

	@Override
	public List<SubscriptionTierDTO> findAllByAuthor(Long id) {
		LOG.debug("Request to get all SubscriptionTiers");
		return subscriptionTierRepository.findAllByAuthorId(id)
				.stream().map(subscriptionTierMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id, User user) {
		LOG.debug("Request to delete SubscriptionTier: {}", id);
		SubscriptionTier tier = subscriptionTierRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("tier not found"));
		Author author = authorRepository.findByOwner(user).orElse(null);
		if (tier.getAuthor().getId().equals(author.getId()))
			subscriptionTierRepository.deleteById(id);
	}
}
