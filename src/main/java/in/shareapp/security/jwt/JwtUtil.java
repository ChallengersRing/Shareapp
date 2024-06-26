package in.shareapp.security.jwt;

import in.shareapp.user.entity.User;
import in.shareapp.utils.PropertyHolder;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final String TOKEN = "TOKEN";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(PropertyHolder.getProperty("jwt.secret").getBytes()); // set in env //Jwts.SIG.HS512.key().build();
    private static final long EXPIRATION_TIME_MILLIS = Long.parseLong(PropertyHolder.getProperty("jwt.expirationTime"));

    private static volatile JwtUtil instance;
    private final JwtParser jwtParser;

    private JwtUtil() {
        this.jwtParser = Jwts.parser().verifyWith(SECRET_KEY).build();
    }

    public static JwtUtil getInstance() {
        if (instance == null) {
            synchronized (JwtUtil.class) {
                if (instance == null) {
                    instance = new JwtUtil();
                }
            }
        }
        return instance;
    }

    public static String generateToken(User user) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME_MILLIS);

        ClaimsBuilder claimsBuilder = Jwts.claims()
                .id(UUID.randomUUID().toString())
                .subject(user.getEmail())
                .issuedAt(now)
                .expiration(expirationDate);

        claimsBuilder.add("id", user.getExtId());
        claimsBuilder.add("firstname", user.getFirstName());
        claimsBuilder.add("lastname", user.getLastName());
        claimsBuilder.add("avatar", user.getAvatar());
        claimsBuilder.add("dob", user.getDateOfBirth());
        claimsBuilder.add("gender", user.getGender());
        claimsBuilder.add("email", user.getEmail());
        claimsBuilder.add("phone", user.getPhone());

        return Jwts.builder()
                .claims(claimsBuilder.build())
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Optional<String> extractToken(HttpServletRequest request) {
        // Try to extract token from Authorization header[TBD]
        String bearerToken = request.getHeader(AUTH_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER)) {
            return Optional.of(bearerToken.substring(BEARER.length()));
        }

        // If token is not found in header, try to extract from cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (TOKEN.equals(cookie.getName())) {
                    return Optional.of(cookie.getValue());
                }
            }
        }

        return Optional.empty();
    }

    public Optional<User> resolveClaims(String token) {
        Claims claims = parseJwtClaims(token);
        if (!this.validateClaims(claims)) {
            return Optional.empty();
        }

        return Optional.of(new User.Builder(claims.get("email", String.class), "")
                .extId(UUID.fromString(claims.get("id", String.class)))
                .firstName(claims.get("firstname", String.class))
                .lastName(claims.get("lastname", String.class))
                .avatar(claims.get("avatar", String.class))
                .dateOfBirth(claims.get("dob", String.class))
                .gender(claims.get("gender", String.class))
                .phone(claims.get("phone", String.class))
                .build());
    }

    private Claims parseJwtClaims(String token) {
        try {
            return jwtParser.parseSignedClaims(token).getPayload();
        } catch (JwtException e) {
            logger.error("Error parsing JWT claims. {}", e.getMessage());
            throw e;
        }
    }

    private boolean validateClaims(Claims claims) {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            logger.error("Error validating JWT claims. {}", e.getMessage());
            throw e;
        }
    }

    public static void addTokenToResponse(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie(TOKEN, token);
        cookie.setPath("/");
        cookie.setHttpOnly(true); // Set HttpOnly flag for added security
        cookie.setSecure(true); // Ensure the cookie is only sent over HTTPS
        cookie.setMaxAge((int) TimeUnit.MILLISECONDS.toSeconds(EXPIRATION_TIME_MILLIS)); // Set expiration time
        response.addCookie(cookie);
    }
}
