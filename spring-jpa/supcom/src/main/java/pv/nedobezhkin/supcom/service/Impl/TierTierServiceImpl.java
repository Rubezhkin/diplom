package pv.nedobezhkin.supcom.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.TierTier;
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

	@Override
	public TierTierDTO save(TierTierDTO tiertierDTO) {
		LOG.debug("Request to save TierTier: {}", tiertierDTO);
		TierTier tiertier = tierTierMapper.toEntity(tiertierDTO);
		tiertier = tierTierRepository.save(tiertier);
		return tierTierMapper.toDto(tiertier);
	}

	@Override
	public TierTierDTO update(TierTierDTO tiertierDTO) {
		LOG.debug("Request to update TierTier: {}", tiertierDTO);
		TierTier tiertier = tierTierMapper.toEntity(tiertierDTO);
		tiertier = tierTierRepository.save(tiertier);
		return tierTierMapper.toDto(tiertier);
	}

	@Override
	public TierTierDTO partialUpdate(TierTierDTO tiertierDTO) {
		LOG.debug("Request to partically update TierTier: {}", tiertierDTO);

		return tierTierRepository
				.findById(tiertierDTO.getId())
				.map(existingTierTier -> {
					tierTierMapper.partialUpdate(existingTierTier, tiertierDTO);
					return existingTierTier;
				})
				.map(tierTierRepository::save)
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

}
