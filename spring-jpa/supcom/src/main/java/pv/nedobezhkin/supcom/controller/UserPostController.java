package pv.nedobezhkin.supcom.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.UserPostService;
import pv.nedobezhkin.supcom.service.dto.UserPostDTO;

@RestController
@RequestMapping("/api/userposts")
@RequiredArgsConstructor
public class UserPostController {
	private static final Logger LOG = LoggerFactory.getLogger(UserPostController.class);
	private final UserPostService userPostService;

	@PostMapping("/{postId}")
	public ResponseEntity<UserPostDTO> purchasePost(
			@PathVariable Long postId,
			@AuthenticationPrincipal User user) throws AccessDeniedException {
		LOG.debug("REST request to purchase post: {} by user {}", postId, user.getId());

		UserPostDTO result = userPostService.save(postId, user);
		return ResponseEntity.ok(result);

	}

	@GetMapping("")
	public ResponseEntity<List<UserPostDTO>> getPurchasedPosts(@AuthenticationPrincipal User user) {
		List<UserPostDTO> result = userPostService.findByUser(user);
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> delete(@PathVariable Long postId, @AuthenticationPrincipal User user)
			throws AccessDeniedException {

		userPostService.delete(postId, user);
		return ResponseEntity.noContent().build();

	}
}
