package pv.nedobezhkin.supcom.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.Author;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.entity.UserAuthor;
import pv.nedobezhkin.supcom.repository.AuthorRepository;
import pv.nedobezhkin.supcom.repository.UserAuthorRepository;
import pv.nedobezhkin.supcom.service.UserAuthorService;
import pv.nedobezhkin.supcom.service.dto.AuthorDTO;
import pv.nedobezhkin.supcom.service.dto.UserAuthorDTO;
import pv.nedobezhkin.supcom.service.mapper.AuthorMapper;
import pv.nedobezhkin.supcom.service.mapper.UserAuthorMapper;

@RequiredArgsConstructor
@Service
public class UserAuthorServiceImpl implements UserAuthorService {

	private final UserAuthorRepository userAuthorRepository;
	private final AuthorRepository authorRepository;
	private final AuthorMapper authorMapper;

	@Override
	public void followAuthor(Long authorId, User user) {
		Author author = authorRepository.findById(authorId)
				.orElseThrow(() -> new EntityNotFoundException("Author not found"));

		UserAuthor relation = new UserAuthor();
		relation.setUser(user);
		relation.setAuthor(author);
		userAuthorRepository.save(relation);
	}

	@Override
	public void unfollowAuthor(Long authorId, User user) {
		Author author = authorRepository.findById(authorId)
				.orElseThrow(() -> new EntityNotFoundException("Author not found"));

		UserAuthor relation = userAuthorRepository.findByUserAndAuthor(user, author)
				.orElseThrow(() -> new EntityNotFoundException("Relation not found"));

		userAuthorRepository.delete(relation);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AuthorDTO> getFollowedAuthors(User user) {
		return userAuthorRepository.findAllByUser(user)
				.stream()
				.map(UserAuthor::getAuthor)
				.map(authorMapper::toDto)
				.collect(Collectors.toList());
	}
}