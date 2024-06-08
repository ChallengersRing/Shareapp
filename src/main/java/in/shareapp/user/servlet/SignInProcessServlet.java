package in.shareapp.user.servlet;

import in.shareapp.user.entity.User;
import in.shareapp.user.service.UserService;
import in.shareapp.user.service.UserServiceImpl;
import in.shareapp.security.jwt.JwtUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

@WebServlet("/signin")
public class SignInProcessServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SignInProcessServlet.class.getName());
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
            logger.warning("Failed authentication for user with email: " + email);
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"success\": false, \"message\": \"Invalid User\"}");
        }
    }
}
