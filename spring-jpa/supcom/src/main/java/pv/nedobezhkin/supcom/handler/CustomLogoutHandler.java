package pv.nedobezhkin.supcom.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import pv.nedobezhkin.supcom.entity.Token;
import pv.nedobezhkin.supcom.repository.TokenRepository;

@Component
@AllArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

	private final TokenRepository tokenRepository;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer")) {
			return;
		}

		String token = authHeader.substring(7);

		Token tokenEntity = tokenRepository.findByAccessToken(token).orElse(null);

		if (tokenEntity != null) {
			tokenEntity.setLoggedOut(true);
			tokenRepository.save(tokenEntity);
		}
	}

}
