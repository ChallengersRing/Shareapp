package in.shareapp.user.servlet;

import in.shareapp.user.entity.User;
import in.shareapp.user.service.UserService;
import in.shareapp.user.service.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet("/signup")
public class SignUpProcessServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SignUpProcessServlet.class.getName());
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("From SignUpProcessServlet");

        String photo = "defaultprofilepic.png";
        String firstName = req.getParameter("fname");
        String lastName = req.getParameter("lname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        User user = new User(photo, firstName, lastName, email, phone, password);
        boolean isUserAvailable = userService.checkUserExists(user.getEmail());
        logger.info("isUserAvailable: " + isUserAvailable);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        if (!isUserAvailable) {
            boolean result = userService.registerNewUser(user);

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
