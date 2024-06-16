package org.hmcampoverde.security;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.hmcampoverde.dto.TokenDto;
import org.hmcampoverde.entity.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class CustomProvider {

	private static final Logger logger = LoggerFactory.getLogger(CustomProvider.class);

	@Value("${security.jwt.secret-key}")
	private String secretKey;

	@Value("${security.jwt.time.expiration}")
	private int expirationTime;

	public String buildToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		List<String> roles = userPrincipal
				.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		logger.info("token generado");
		return Jwts
				.builder()
				.subject(userPrincipal.getUsername())
				.claim("roles", roles)
				.claim("fullname", userPrincipal.getUser().getEmployee().getFullname())
				.issuedAt(new Date())
				.expiration(new Date(new Date().getTime() + expirationTime * 180))
				.signWith(getSecret(secretKey))
				.compact();
	}

	public String getUsername(String token) {
		return Jwts.parser().verifyWith(getSecret(secretKey)).build().parseSignedClaims(token).getPayload()
				.getSubject();
	}

	public Boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(getSecret(secretKey)).build().parseSignedClaims(token);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("token mal formado");
		} catch (UnsupportedJwtException e) {
			logger.error("token no soportado");
		} catch (ExpiredJwtException e) {
			logger.error("token expirado");
		} catch (IllegalArgumentException e) {
			logger.error("token vacío");
		} catch (SignatureException e) {
			logger.error("fail en la firma");
		}
		return false;
	}

	public String refreshToken(TokenDto tokenDto) throws ParseException {
		try {
			Jwts.parser().verifyWith(getSecret(secretKey)).build().parseSignedClaims(tokenDto.getToken());
		} catch (ExpiredJwtException e) {
			logger.error("token se refresco");

			JWT jwt = JWTParser.parse(tokenDto.getToken());
			JWTClaimsSet claims = jwt.getJWTClaimsSet();
			String username = claims.getSubject();
			String fullname = (String) claims.getClaim("fullname");
			List<?> roles = (List<?>) claims.getClaim("roles");

			return Jwts
					.builder()
					.subject(username)
					.claim("roles", roles)
					.claim("fullname", fullname)
					.issuedAt(new Date())
					.expiration(new Date(new Date().getTime() + expirationTime * 180))
					.signWith(getSecret(secretKey))
					.compact();
		}
		return null;
	}

	private SecretKey getSecret(String secret) {
		byte[] secretBytes = Decoders.BASE64URL.decode(secret);
		return Keys.hmacShaKeyFor(secretBytes);
	}
}
