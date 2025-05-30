package pv.nedobezhkin.supcom.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pv.nedobezhkin.supcom.entity.Token;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.repository.TokenRepository;
import pv.nedobezhkin.supcom.repository.UserRepository;
import pv.nedobezhkin.supcom.service.dto.AuthenticationResponseDto;
import pv.nedobezhkin.supcom.service.dto.LoginRequestDto;
import pv.nedobezhkin.supcom.service.dto.RegistrationRequestDto;

@Service
@AllArgsConstructor
public class AuthenticationService {
	private final UserRepository userRepository;

	private final JwtService jwtService;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private final TokenRepository tokenRepository;

	public void register(RegistrationRequestDto request) {
		User user = new User();

		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setAdmin(false);
		user.setBalance(new BigDecimal(0));

		user = userRepository.save(user);
	}

	private void revokeAllToken(User user) {

		List<Token> validTokens = tokenRepository.findAllAccessTokenByUser(user.getId());

		if (!validTokens.isEmpty()) {
			validTokens.forEach(t -> {
				t.setLoggedOut(true);
			});
		}

		tokenRepository.saveAll(validTokens);
	}

	private void saveUserToken(String accessToken, User user) {

		Token token = new Token();

		token.setAccessToken(accessToken);
		token.setLoggedOut(false);
		token.setUser(user);

		tokenRepository.save(token);
	}

	public AuthenticationResponseDto authenticate(LoginRequestDto request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword()));

		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		String accessToken = jwtService.generateAccessToken(user);

		revokeAllToken(user);

		saveUserToken(accessToken, user);

		return new AuthenticationResponseDto(accessToken);
	}

}
