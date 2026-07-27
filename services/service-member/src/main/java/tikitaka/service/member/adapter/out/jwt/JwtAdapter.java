package tikitaka.service.member.adapter.out.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import tikitaka.service.member.application.port.out.auth.JwtPort;
import tikitaka.service.member.config.JwtProperties;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtAdapter implements JwtPort {

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtAdapter(
            JwtProperties jwtProperties
    ) {

        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(
                jwtProperties.secret().getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public String generateAccessToken(
            UUID memberId
    ) {

        return generateToken(
                memberId,
                "access",
                jwtProperties.accessTokenExpiration()
        );
    }

    @Override
    public String generateRefreshToken(
            UUID memberId
    ) {

        return generateToken(
                memberId,
                "refresh",
                jwtProperties.refreshTokenExpiration()
        );
    }

    @Override
	public boolean validateToken(
			String token
	) {

        try {

            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception e) {

            return false;
		}
	}

	@Override
	public boolean validateRefreshToken(
			String token
	) {

		try {

			Claims claims = Jwts.parser()
					.verifyWith(secretKey)
					.build()
					.parseSignedClaims(token)
					.getPayload();

			return "refresh".equals(claims.get("type", String.class));

		} catch (Exception e) {

			return false;
		}
	}

	@Override
	public boolean validateAccessToken(
			String token
	) {

		try {

			Claims claims = Jwts.parser()
					.verifyWith(secretKey)
					.build()
					.parseSignedClaims(token)
					.getPayload();

			return "access".equals(claims.get("type", String.class));

		} catch (Exception e) {

			return false;
		}
	}

	@Override
    public UUID getMemberId(
            String token
    ) {

        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return UUID.fromString(claims.getSubject());
    }

    private String generateToken(
            UUID memberId,
            String type,
            Long expiration
    ) {

        Date now = new Date();
        Date expireDate = new Date(
                now.getTime() + expiration
        );

        return Jwts.builder()
                .subject(memberId.toString())
                .claim("type", type)
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(secretKey)
                .compact();
    }
}
