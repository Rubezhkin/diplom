package pv.nedobezhkin.supcom.controller;

import java.util.List;
import java.util.Objects;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.service.PostService;
import pv.nedobezhkin.supcom.service.dto.PostDTO;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
	private static final Logger LOG = LoggerFactory.getLogger(PostController.class);
	private final PostService postService;

	@PostMapping("")
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) throws BadRequestException {
		LOG.debug("REST request to save Post: {}", postDTO);
		PostDTO result = postService.save(postDTO);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("")
	public ResponseEntity<List<PostDTO>> getAllPosts() {
		LOG.debug("REST request to get all Posts");
		List<PostDTO> result = postService.findAll();
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
		LOG.debug("REST request to get Post: {}", id);
		PostDTO result = postService.findById(id).orElse(null);
		return ResponseEntity.ok().body(result);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @Valid @RequestBody PostDTO postDTO)
			throws BadRequestException {
		LOG.debug("REST request to put Post: {} {}", id, postDTO);
		if (postDTO.getId() == null) {
			throw new BadRequestException("id null");
		}
		if (!Objects.equals(postDTO.getId(), id)) {
			throw new BadRequestException("id invalid");
		}
		if (postService.findById(id) == null) {
			throw new BadRequestException("id not found");
		}

		PostDTO result = postService.update(postDTO);
		return ResponseEntity.ok().body(result);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<PostDTO> partialUpdatePost(@PathVariable Long id,
			@NotNull @RequestBody PostDTO postDTO) throws BadRequestException {
		LOG.debug("REST request to patch Post: {} {}", id, postDTO);
		if (postService.findById(id) == null) {
			throw new BadRequestException("id not found");
		}
		postDTO.setId(id);
		PostDTO result = postService.partialUpdate(postDTO);

		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable Long id) {
		LOG.debug("REST request to delete post: {}", id);
		postService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
