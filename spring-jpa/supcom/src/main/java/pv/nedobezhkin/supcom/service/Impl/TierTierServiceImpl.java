package pv.nedobezhkin.supcom.service.Impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.SubscriptionTier;
import pv.nedobezhkin.supcom.entity.TierTier;
import pv.nedobezhkin.supcom.repository.SubscriptionTierRepository;
import pv.nedobezhkin.supcom.repository.TierTierRepository;
import pv.nedobezhkin.supcom.service.TierTierService;
import pv.nedobezhkin.supcom.service.dto.TierTierDTO;
import pv.nedobezhkin.supcom.service.mapper.TierTierMapper;

@Service
@RequiredArgsConstructor
public class TierTierServiceImpl implements TierTierService {

	private static final Logger LOG = LoggerFactory.getLogger(TierTierServiceImpl.class);
	private final TierTierRepository tierTierRepository;
	private final TierTierMapper tierTierMapper;
	private final SubscriptionTierRepository tierRepository;

	@Override
	public TierTierDTO save(TierTierDTO tiertierDTO) throws BadRequestException {
		LOG.debug("Request to save TierTier: {}", tiertierDTO);
		TierTier tiertier = tierTierMapper.toEntity(tiertierDTO);
		SubscriptionTier parentTier = tierRepository.findById(tiertierDTO.getParentTier())
				.orElseThrow(() -> new EntityNotFoundException("parent tier not found"));
		SubscriptionTier childTier = tierRepository.findById(tiertierDTO.getChildTier())
				.orElseThrow(() -> new EntityNotFoundException("child tier not found"));
		if (!parentTier.getAuthor().equals(childTier.getAuthor()))
			throw new BadRequestException("tiers' author not same");
		checkLops(parentTier, childTier);
		tiertier.setParentTier(parentTier);
		tiertier.setChildTier(childTier);
		tiertier = tierTierRepository.save(tiertier);
		return tierTierMapper.toDto(tiertier);
	}

	@Override
	public TierTierDTO update(TierTierDTO tiertierDTO) throws BadRequestException {
		LOG.debug("Request to update TierTier: {}", tiertierDTO);
		TierTier tiertier = tierTierMapper.toEntity(tiertierDTO);
		SubscriptionTier parentTier = tierRepository.findById(tiertierDTO.getParentTier())
				.orElseThrow(() -> new EntityNotFoundException("parent tier not found"));
		SubscriptionTier childTier = tierRepository.findById(tiertierDTO.getChildTier())
				.orElseThrow(() -> new EntityNotFoundException("child tier not found"));
		if (!parentTier.getAuthor().equals(childTier.getAuthor()))
			throw new BadRequestException("tiers' authors not same");
		checkLops(parentTier, childTier);
		tiertier.setParentTier(parentTier);
		tiertier.setChildTier(childTier);
		tiertier = tierTierRepository.save(tiertier);
		return tierTierMapper.toDto(tiertier);
	}

	@Override
	public TierTierDTO partialUpdate(TierTierDTO tiertierDTO) throws BadRequestException {
		LOG.debug("Request to partically update TierTier: {}", tiertierDTO);

		TierTierDTO result = tierTierRepository
				.findById(tiertierDTO.getId())
				.map(existingTierTier -> {
					tierTierMapper.partialUpdate(existingTierTier, tiertierDTO);
					return existingTierTier;
				}).map(tierTierMapper::toDto).orElse(null);
		SubscriptionTier parentTier = tierRepository.findById(result.getParentTier())
				.orElseThrow(() -> new EntityNotFoundException("parent tier not found"));
		SubscriptionTier childTier = tierRepository.findById(result.getChildTier())
				.orElseThrow(() -> new EntityNotFoundException("child tier not found"));
		if (!parentTier.getAuthor().equals(childTier.getAuthor()))
			throw new BadRequestException("tiers' authors not same");
		checkLops(childTier, parentTier);
		return tierTierRepository.findById(result.getId()).map(tierTierRepository::save)
				.map(tierTierMapper::toDto).orElse(null);
	}

	@Override
	public Optional<TierTierDTO> findById(Long id) {
		LOG.debug("Request to get TierTier: {}", id);
		return tierTierRepository.findById(id).map(tierTierMapper::toDto);
	}

	@Override
	public List<TierTierDTO> findAll() {
		LOG.debug("Request to get all TierTiers");
		return tierTierRepository.findAll()
				.stream().map(tierTierMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		LOG.debug("Request to delete TierTier: {}", id);
		tierTierRepository.deleteById(id);
	}

	private void checkLops(SubscriptionTier parent, SubscriptionTier child) throws BadRequestException {
		if (parent.equals(child)) {
			throw new BadRequestException("Parent and child cannot be the same tier");
		}

		Set<Long> visited = new HashSet<>();
		Queue<SubscriptionTier> queue = new LinkedList<>();
		queue.add(parent);

		while (!queue.isEmpty()) {
			SubscriptionTier current = queue.poll();

			if (current.equals(child)) {
				throw new BadRequestException("Cycle detected in tier hierarchy");
			}

			if (visited.add(current.getId())) {
				List<TierTier> parentRelations = tierTierRepository.findByChildTier(current);
				for (TierTier relation : parentRelations) {
					queue.add(relation.getParentTier());
				}
			}
		}

		queue.add(child);

		while (!queue.isEmpty()) {
			SubscriptionTier current = queue.poll();

			if (current.equals(parent)) {
				throw new BadRequestException("Cycle detected in tier hierarchy");
			}

			if (visited.add(current.getId())) {
				List<TierTier> parentRelations = tierTierRepository.findByChildTier(current);
				for (TierTier relation : parentRelations) {
					queue.add(relation.getParentTier());
				}
			}
		}
	}
}
