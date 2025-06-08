package pv.nedobezhkin.supcom.service.Impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.Author;
import pv.nedobezhkin.supcom.entity.SubscriptionTier;
import pv.nedobezhkin.supcom.entity.TierTier;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.repository.AuthorRepository;
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
	private final AuthorRepository authorRepository;

	@Override
	public TierTierDTO save(TierTierDTO tiertierDTO, User user) throws AccessDeniedException {
		LOG.debug("Request to save TierTier: {}", tiertierDTO);

		SubscriptionTier parentTier = tierRepository.findById(tiertierDTO.getParentTier())
				.orElseThrow(() -> new EntityNotFoundException("parent tier not found"));
		SubscriptionTier childTier = tierRepository.findById(tiertierDTO.getChildTier())
				.orElseThrow(() -> new EntityNotFoundException("child tier not found"));
		if (!parentTier.getAuthor().equals(childTier.getAuthor()))
			throw new AccessDeniedException("tiers' authors not same");
		if (!parentTier.getAuthor().getId().equals(authorRepository.findByOwner(user)
				.orElseThrow(() -> new EntityNotFoundException("Author not found")).getId())) {
			throw new AccessDeniedException("it's not user's tiers");
		}

		checkLops(parentTier, childTier);

		TierTier tiertier = tierTierMapper.toEntity(tiertierDTO);
		tiertier.setParentTier(parentTier);
		tiertier.setChildTier(childTier);

		tiertier = tierTierRepository.save(tiertier);
		return tierTierMapper.toDto(tiertier);
	}

	@Override
	public List<TierTierDTO> findAllByAuthor(User user) {
		LOG.debug("Request to get all TierTiers");
		Author author = authorRepository.findByOwner(user)
				.orElseThrow(() -> new EntityNotFoundException("Author not found"));
		return tierTierRepository.findByAuthor(author)
				.stream().map(tierTierMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id, User user) throws AccessDeniedException {
		LOG.debug("Request to delete TierTier: {}", id);
		Author author = authorRepository.findByOwner(user)
				.orElseThrow(() -> new EntityNotFoundException("Author not found"));
		TierTier tierTier = tierTierRepository.findById(id).orElse(null);
		List<TierTier> list = tierTierRepository.findByAuthor(author);
		if (list.indexOf(tierTier) != -1)
			tierTierRepository.deleteById(id);
		else
			throw new AccessDeniedException("it's not user's tierTier");
	}

	private void checkLops(SubscriptionTier parent, SubscriptionTier child) throws AccessDeniedException {
		if (parent.equals(child)) {
			throw new AccessDeniedException("Parent and child cannot be the same tier");
		}

		if (isReachable(parent, child)) {
			throw new AccessDeniedException("Cycle detected: parent already связан с child");
		}

		if (isReachable(child, parent)) {
			throw new AccessDeniedException("Cycle detected: child уже связан с parent");
		}
	}

	private boolean isReachable(SubscriptionTier start, SubscriptionTier target) {
		Set<Long> visited = new HashSet<>();
		Queue<SubscriptionTier> queue = new LinkedList<>();
		queue.add(start);

		while (!queue.isEmpty()) {
			SubscriptionTier current = queue.poll();
			if (current.equals(target)) {
				return true;
			}
			if (visited.add(current.getId())) {
				List<TierTier> parentRelations = tierTierRepository.findByChildTier(current);
				for (TierTier relation : parentRelations) {
					queue.add(relation.getParentTier());
				}
			}
		}
		return false;
	}
}
