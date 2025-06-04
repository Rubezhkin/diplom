package pv.nedobezhkin.supcom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;
import pv.nedobezhkin.supcom.handler.CustomAccessDeniedHandler;
import pv.nedobezhkin.supcom.handler.CustomLogoutHandler;
import pv.nedobezhkin.supcom.service.UserService;
import pv.nedobezhkin.supcom.service.filter.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
	private final JwtFilter jwtFilter;
	private final UserService userService;
	private final CustomAccessDeniedHandler accessDeniedHandler;
	private final CustomLogoutHandler customLogoutHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);

		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/login/**", "/registration/**", "/api/posts/{id}", "/api/posts/author/{id}")
					.permitAll();
			auth.anyRequest().authenticated();
		})
				.userDetailsService(userService)
				.exceptionHandling(e -> {
					e.accessDeniedHandler(accessDeniedHandler);
					e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
				})
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.logout(log -> {
					log.logoutUrl("/logout");
					log.addLogoutHandler(customLogoutHandler);
					log.logoutSuccessHandler(
							(request, response, authentication) -> SecurityContextHolder.clearContext());
				});
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
