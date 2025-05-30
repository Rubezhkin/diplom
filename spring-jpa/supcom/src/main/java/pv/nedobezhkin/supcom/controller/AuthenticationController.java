package pv.nedobezhkin.supcom.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pv.nedobezhkin.supcom.service.AuthenticationService;
import pv.nedobezhkin.supcom.service.UserService;
import pv.nedobezhkin.supcom.service.dto.AuthenticationResponseDto;
import pv.nedobezhkin.supcom.service.dto.LoginRequestDto;
import pv.nedobezhkin.supcom.service.dto.RegistrationRequestDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
public class AuthenticationController {
	private final AuthenticationService authenticationService;

	private final UserService userService;

	@PostMapping("/registration")
	public ResponseEntity<String> register(@RequestBody RegistrationRequestDto registrationDto) {
		if (userService.existsByUsername(registrationDto.getUsername())) {
			return ResponseEntity.badRequest().body("username is occupied");
		}

		if (userService.existsByEmail(registrationDto.getEmail())) {
			return ResponseEntity.badRequest().body("email is occupied");
		}

		authenticationService.register(registrationDto);
		return ResponseEntity.ok("registration succesfull");
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponseDto> authenticate(
			@RequestBody LoginRequestDto request) {
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}
}
