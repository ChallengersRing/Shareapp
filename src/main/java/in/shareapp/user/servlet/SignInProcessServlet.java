package in.shareapp.user.servlet;

import in.shareapp.user.entity.User;
import in.shareapp.user.service.UserService;
import in.shareapp.user.service.UserServiceImpl;
import in.shareapp.security.jwt.JwtUtil;
import in.shareapp.utils.PropertyHolder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@WebServlet("/signin")
public class SignInProcessServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SignInProcessServlet.class.getName());
    private static final long expirationTimeMillis = Long.parseLong(PropertyHolder.getProperty("jwt.expirationTime"));
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String email = req.getParameter("email");
        final String password = req.getParameter("password");

        final User user = new User(email, password);
        Optional<User> authenticatedUser = this.userService.authenticateUserByEmailAndPassword(user);

        if (authenticatedUser.isPresent()) {
            // Generate JWT token
            String token = JwtUtil.generateToken(authenticatedUser.get(), expirationTimeMillis);

            Cookie cookie = new Cookie("TOKEN", token);
            cookie.setPath("/");
            cookie.setMaxAge((int) TimeUnit.MILLISECONDS.toSeconds(expirationTimeMillis));
            resp.addCookie(cookie);

            // Set token in response header
            // resp.setHeader("Authorization", "Bearer " + token);

            // Send JSON response indicating success
            resp.setContentType("application/json");
            resp.getWriter().write("{\"success\": true}");
        } else {
            // Authentication failed
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"success\": false, \"message\": \"Authentication failed\"}");
        }
    }
}
