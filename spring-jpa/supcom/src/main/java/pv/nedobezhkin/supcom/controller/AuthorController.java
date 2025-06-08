package pv.nedobezhkin.supcom.controller;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.AuthorService;
import pv.nedobezhkin.supcom.service.dto.AuthorDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {
	private static final Logger LOG = LoggerFactory.getLogger(AuthorController.class);
	private final AuthorService authorService;

	@PostMapping("")
	public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO,
			@AuthenticationPrincipal User user) {

		authorDTO.setOwner(user.getId());
		LOG.debug("REST request to save Author: {}", authorDTO);
		AuthorDTO result = authorService.save(authorDTO, user);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
		LOG.debug("REST request to get Author: {}", id);
		AuthorDTO result = authorService.findById(id).orElse(null);
		return ResponseEntity.ok().body(result);
	}

	@PatchMapping("")
	public ResponseEntity<AuthorDTO> partialUpdateAuthor(
			@NotNull @RequestBody AuthorDTO authorDTO, @AuthenticationPrincipal User user) throws BadRequestException {
		LOG.debug("REST request to patch Author: {} {}", user, authorDTO);

		AuthorDTO result = authorService.partialUpdate(authorDTO, user);

		return ResponseEntity.ok().body(result);

	}

	@DeleteMapping("")
	public ResponseEntity<Void> deleteAuthor(@AuthenticationPrincipal User user) {
		LOG.debug("REST request to delete author: {}", user);

		authorService.delete(user);
		return ResponseEntity.noContent().build();

	}

}
