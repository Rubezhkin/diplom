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
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.repository.AuthorRepository;
import pv.nedobezhkin.supcom.repository.UserRepository;
import pv.nedobezhkin.supcom.service.AuthorService;
import pv.nedobezhkin.supcom.service.dto.AuthorDTO;
import pv.nedobezhkin.supcom.service.mapper.AuthorMapper;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

	private static final Logger LOG = LoggerFactory.getLogger(AuthorServiceImpl.class);
	private final AuthorRepository authorRepository;
	private final AuthorMapper authorMapper;
	private final UserRepository userRepository;

	@Override
	public AuthorDTO save(AuthorDTO authorDTO) {
		LOG.debug("Request to save Author: {}", authorDTO);
		Author author = authorMapper.toEntity(authorDTO);
		User owner = userRepository.findById(authorDTO.getOwner())
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		author.setOwner(owner);
		author = authorRepository.save(author);
		return authorMapper.toDto(author);
	}

	@Override
	public AuthorDTO update(AuthorDTO authorDTO) {
		LOG.debug("Request to update Author: {}", authorDTO);
		Author author = authorMapper.toEntity(authorDTO);
		author = authorRepository.save(author);
		return authorMapper.toDto(author);
	}

	@Override
	public AuthorDTO partialUpdate(AuthorDTO authorDTO) {
		LOG.debug("Request to partically update Author: {}", authorDTO);

		return authorRepository
				.findById(authorDTO.getId())
				.map(existingAuthor -> {
					authorMapper.partialUpdate(existingAuthor, authorDTO, userRepository);
					return existingAuthor;
				})
				.map(authorRepository::save)
				.map(authorMapper::toDto).orElse(null);
	}

	@Override
	public Optional<AuthorDTO> findById(Long id) {
		LOG.debug("Request to get Author: {}", id);
		return authorRepository.findById(id).map(authorMapper::toDto);
	}

	@Override
	public List<AuthorDTO> findAll() {
		LOG.debug("Request to get all Authors");
		return authorRepository.findAll()
				.stream().map(authorMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		LOG.debug("Request to delete Author: {}", id);
		authorRepository.deleteById(id);
	}

}
