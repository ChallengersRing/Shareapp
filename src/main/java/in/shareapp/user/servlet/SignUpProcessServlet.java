package in.shareapp.user.servlet;

import in.shareapp.user.entity.User;
import in.shareapp.user.service.UserService;
import in.shareapp.user.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/signup")
public class SignUpProcessServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(SignUpProcessServlet.class);
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        logger.debug("Signing Up user...");

        final String photo = "defaultprofilepic.png";
        final String firstName = req.getParameter("fname");
        final String lastName = req.getParameter("lname");
        final String email = req.getParameter("email");
        final String phone = req.getParameter("phone");
        final String password = req.getParameter("password");

        final User user = new User(photo, firstName, lastName, email, phone, password);
        final boolean isUserAvailable = this.userService.checkUserExists(user.getEmail());
        logger.info("isUserAvailable: {}", isUserAvailable);

        resp.setContentType("application/json");
        final PrintWriter out = resp.getWriter();

        if (!isUserAvailable) {
            final boolean result = this.userService.registerNewUser(user);

            if (result) {
                out.write("{\"success\": true}");
            } else {
                out.write("{\"success\": false, \"message\": \"Failed to register user\"}");
            }
        } else {
            out.write("{\"success\": false, \"message\": \"User already exists\"}");
        }
        out.close();
    }
}
