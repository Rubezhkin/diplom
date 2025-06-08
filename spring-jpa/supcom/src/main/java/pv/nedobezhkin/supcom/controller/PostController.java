package pv.nedobezhkin.supcom.controller;

import java.util.List;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.PostService;
import pv.nedobezhkin.supcom.service.dto.PostDTO;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
	private static final Logger LOG = LoggerFactory.getLogger(PostController.class);
	private final PostService postService;

	@PostMapping("")
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, @AuthenticationPrincipal User user)
			throws AccessDeniedException {
		LOG.debug("REST request to save Post: {}", postDTO);
		PostDTO result = postService.save(postDTO, user);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("")
	public ResponseEntity<List<PostDTO>> getAllPosts(@AuthenticationPrincipal User user) {
		LOG.debug("REST request to get all Posts");
		List<PostDTO> result = postService.findAllAvailablePosts(user);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/author/{id}")
	public ResponseEntity<List<PostDTO>> getPostsByAuthor(@PathVariable Long id, @AuthenticationPrincipal User user) {
		LOG.debug("REST request to get Posts by author: {}", id);
		List<PostDTO> result = postService.findByAuthor(id, user);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Long id, @AuthenticationPrincipal User user) {
		LOG.debug("REST request to get Post: {}", id);
		PostDTO result = postService.findById(id, user);
		return ResponseEntity.ok().body(result);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<PostDTO> partialUpdatePost(@PathVariable Long id,
			@NotNull @RequestBody PostDTO postDTO, @AuthenticationPrincipal User user) throws BadRequestException {
		LOG.debug("REST request to patch Post: {} {}", id, postDTO);
		if (postService.findById(id, user) == null) {
			throw new BadRequestException("id not found");
		}
		postDTO.setId(id);
		try {
			PostDTO result = postService.partialUpdate(postDTO, user);

			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().header(e.getMessage()).build();
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable Long id, @AuthenticationPrincipal User user)
			throws BadRequestException {
		LOG.debug("REST request to delete post: {}", id);

		postService.delete(id, user);
		return ResponseEntity.noContent().build();

	}

}
