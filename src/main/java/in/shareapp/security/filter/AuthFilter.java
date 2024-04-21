package in.shareapp.security.filter;

import in.shareapp.security.jwt.JwtUtil;
import in.shareapp.user.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebFilter(
        filterName = "AuthFilter",
        urlPatterns = {"/postupload", "/updateprofile"}
)
public class AuthFilter implements Filter {

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
        } catch (JwtException e) {
            // Handle other JWT parsing errors
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"success\": false, \"message\": \"Unauthorized: Invalid token\"}");
        } catch (Exception e) {
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"success\": false, \"message\": \"Internal server error\"}");
            e.printStackTrace();
        }
    }
}
