package pv.nedobezhkin.supcom.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.service.UserAuthorService;
import pv.nedobezhkin.supcom.service.dto.AuthorDTO;

@RestController
@RequestMapping("/api/userauthors")
@RequiredArgsConstructor
public class UserAuthorController {
	private final UserAuthorService userAuthorService;

	@PostMapping("/{authorId}")
	public ResponseEntity<Void> followAuthor(@PathVariable Long authorId, @AuthenticationPrincipal User user) {
		userAuthorService.followAuthor(authorId, user);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{authorId}")
	public ResponseEntity<Void> unfollowAuthor(@PathVariable Long authorId, @AuthenticationPrincipal User user) {
		userAuthorService.unfollowAuthor(authorId, user);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("")
	public ResponseEntity<List<AuthorDTO>> getFollowedAuthors(@AuthenticationPrincipal User user) {
		return ResponseEntity.ok(userAuthorService.getFollowedAuthors(user));
	}
}
