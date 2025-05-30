package pv.nedobezhkin.supcom.service.Impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.Author;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.repository.AuthorRepository;
import pv.nedobezhkin.supcom.service.AuthorService;
import pv.nedobezhkin.supcom.service.dto.AuthorDTO;
import pv.nedobezhkin.supcom.service.mapper.AuthorMapper;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

	private static final Logger LOG = LoggerFactory.getLogger(AuthorServiceImpl.class);
	private final AuthorRepository authorRepository;
	private final AuthorMapper authorMapper;

	@Override
	public AuthorDTO save(AuthorDTO authorDTO, User user) {
		LOG.debug("Request to save Author: {}", authorDTO);
		Author author = authorMapper.toEntity(authorDTO);
		author.setOwner(user);
		author = authorRepository.save(author);
		return authorMapper.toDto(author);
	}

	@Override
	public AuthorDTO partialUpdate(AuthorDTO authorDTO, User user) {
		LOG.debug("Request to partically update Author: {}", authorDTO);
		authorRepository.findByOwner(user)
				.orElseThrow(() -> new EntityNotFoundException("author not found"));
		return authorRepository
				.findByOwner(user)
				.map(existingAuthor -> {
					authorMapper.partialUpdate(existingAuthor, authorDTO);
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
	public void delete(User user) {
		LOG.debug("Request to delete Author: {}", user);
		Author author = authorRepository.findByOwner(user)
				.orElseThrow(() -> new EntityNotFoundException("author not found"));
		authorRepository.delete(author);
	}

}
