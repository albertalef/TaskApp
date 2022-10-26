package me.albertalef.realtodoapp.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.albertalef.realtodoapp.exception.details.ExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	public final String jwtUrlPath;

	public JwtAuthorizationFilter( AuthenticationManager authenticationManager, String jwtUrlPath ) {
		super(authenticationManager);
		this.jwtUrlPath = jwtUrlPath;
	}

	@Override
	protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain chain ) throws IOException, ServletException {
		try {
			if(!request.getServletPath().equals(jwtUrlPath)) {
				String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
				if(authHeader != null && authHeader.startsWith("Bearer ")) {

					String token = authHeader.substring("Bearer ".length());
					Algorithm algorithm = Algorithm.HMAC256("test".getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);
					String username = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					Collection<SimpleGrantedAuthority> authorities = new ArrayDeque<>();
					Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
			chain.doFilter(request, response);
		} catch(Exception exception) {
			response.setHeader("error", exception.getMessage());
			response.setStatus(HttpStatus.FORBIDDEN.value());
			ExceptionDetails authError = ExceptionDetails.builder()
					.error("Authentication Error")
					.status(HttpStatus.FORBIDDEN.value())
					.timestamp(LocalDateTime.now())
					.message(exception.getMessage())
					.build();
			new ObjectMapper().registerModule(new JavaTimeModule()).writeValue(response.getOutputStream(), authError);
		}
	}
}
