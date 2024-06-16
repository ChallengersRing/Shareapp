package in.shareapp.security.filter;

import in.shareapp.security.jwt.JwtUtil;
import in.shareapp.user.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

@WebFilter(
        filterName = "AuthFilter",
        urlPatterns = {"/postupload", "/updateprofile", "/jsp/LoadProfile.jsp"}
)
public class AuthFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;

        final Optional<String> token = JwtUtil.extractToken(httpRequest);

        try {
            final Optional<User> userOptional = token.isPresent() ? JwtUtil.getInstance().resolveClaims(token.get()) : Optional.empty();
            if (userOptional.isPresent()) {
                request.setAttribute("user", userOptional.get());
                chain.doFilter(request, response);
                return;
            }
            // If token is invalid or not present, send unauthorized response
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"success\": false, \"message\": \"Unauthorized: Invalid or expired token\"}");
        } catch (ExpiredJwtException e) {
            // Handle token expiration
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"success\": false, \"message\": \"Unauthorized: Token expired\"}");
            logger.warn("Unauthorized: Expired token.", e);
        } catch (JwtException e) {
            // Handle other JWT parsing errors
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"success\": false, \"message\": \"Unauthorized: Invalid token\"}");
            logger.error("Unauthorized: Invalid token.", e);
        } catch (Exception e) {
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"success\": false, \"message\": \"Internal server error\"}");
            logger.error("Exception while extracting token.", e);
        }
    }
}
