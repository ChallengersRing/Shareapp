package in.shareapp.user.servlet;

import in.shareapp.user.entity.User;
import in.shareapp.user.service.UserService;
import in.shareapp.user.service.UserServiceImpl;
import in.shareapp.security.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/signin")
public class SignInProcessServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(SignInProcessServlet.class);
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final String email = req.getParameter("email");
        final String password = req.getParameter("password");

        final User user = new User(email, password);
        Optional<User> authenticatedUser = this.userService.authenticateUserByEmailAndPassword(user);

        if (authenticatedUser.isPresent()) {
            String token = JwtUtil.generateToken(authenticatedUser.get());
            JwtUtil.addTokenToResponse(token, resp);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"success\": true}");
        } else {
            // Authentication failed
            logger.warn("Failed authentication for user with email: {}", email);
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"success\": false, \"message\": \"Invalid User\"}");
        }
    }
}
