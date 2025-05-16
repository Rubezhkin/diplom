package pv.nedobezhkin.supcom.controller;

import java.util.List;
import java.util.Objects;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.service.AuthorService;
import pv.nedobezhkin.supcom.service.dto.AuthorDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {
	private static final Logger LOG = LoggerFactory.getLogger(AuthorController.class);
	private final AuthorService authorService;

	@PostMapping("")
	public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
		LOG.debug("REST request to save Author: {}", authorDTO);
		AuthorDTO result = authorService.save(authorDTO);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("")
	public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
		LOG.debug("REST request to get all Authors");
		List<AuthorDTO> result = authorService.findAll();
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
		LOG.debug("REST request to get Author: {}", id);
		AuthorDTO result = authorService.findById(id).orElse(null);
		return ResponseEntity.ok().body(result);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO)
			throws BadRequestException {
		LOG.debug("REST request to put Author: {} {}", id, authorDTO);
		if (authorDTO.getId() == null) {
			throw new BadRequestException("id null");
		}
		if (!Objects.equals(authorDTO.getId(), id)) {
			throw new BadRequestException("id invalid");
		}
		if (authorService.findById(id) == null) {
			throw new BadRequestException("id not found");
		}

		AuthorDTO result = authorService.update(authorDTO);
		return ResponseEntity.ok().body(result);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<AuthorDTO> partialUpdateAuthor(@PathVariable Long id,
			@NotNull @RequestBody AuthorDTO authorDTO) throws BadRequestException {
		LOG.debug("REST request to patch Author: {} {}", id, authorDTO);
		if (authorService.findById(id) == null) {
			throw new BadRequestException("id not found");
		}
		authorDTO.setId(id);
		AuthorDTO result = authorService.partialUpdate(authorDTO);

		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
		LOG.debug("REST request to delete author: {}", id);
		authorService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
