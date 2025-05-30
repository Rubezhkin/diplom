package pv.nedobezhkin.supcom.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import pv.nedobezhkin.supcom.entity.User;
import pv.nedobezhkin.supcom.repository.TokenRepository;

@Service
public class JwtService {
	@Value("${security.jwt.secret_key}")
	private String secretKey;

	@Value("${security.jwt.access_token_expiration}")
	private long accessTokenExpiration;

	private final TokenRepository tokenRepository;

	public JwtService(TokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}

	private SecretKey getSgningKey() {

		byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);

		return Keys.hmacShaKeyFor(keyBytes);
	}

	private String generateToken(User user, long expiryTime) {
		JwtBuilder builder = Jwts.builder()
				.subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expiryTime))
				.signWith(getSgningKey());

		return builder.compact();
	}

	public String generateAccessToken(User user) {

		return generateToken(user, accessTokenExpiration);
	}

	private Claims extractAllClaims(String token) {

		JwtParserBuilder parser = Jwts.parser();
		parser.verifyWith(getSgningKey());

		return parser.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public <T> T extractClaim(String token, Function<Claims, T> resolver) {

		Claims claims = extractAllClaims(token);

		return resolver.apply(claims);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private boolean isAccessTokenExpired(String token) {
		return !extractExpiration(token).before(new Date());
	}

	public boolean isValid(String token, UserDetails user) {

		String username = extractUsername(token);

		boolean isValidToken = tokenRepository.findByAccessToken(token)
				.map(t -> !t.isLoggedOut()).orElse(false);

		return username.equals(user.getUsername())
				&& isAccessTokenExpired(token)
				&& isValidToken;
	}
}
