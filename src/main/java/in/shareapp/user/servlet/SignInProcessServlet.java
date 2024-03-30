package in.shareapp.user.servlet;

import in.shareapp.user.entity.User;
import in.shareapp.user.service.UserService;
import in.shareapp.user.service.UserServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class SignInProcessServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SignInProcessServlet.class.getName());
    UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //resp.setContentType("text/html");
        HttpSession session = req.getSession();
        PrintWriter out = resp.getWriter();

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = new User(email, password);
        boolean result = userService.authenticateUserByEmailAndPassword(user);

        logger.info("From SignInProcessServlet:---");
        logger.info(email + " " + password);
        logger.info("Result:" + result);
        logger.info(user.getFirstName() + " " +
                user.getEmail() + " " +
                user.getPassword());

        if (result) {
            session.setAttribute("SIGNINSTATUS", "Success");
            session.setAttribute("USERDETAILS", user); //if i store user object it vanish after server restart.
            out.print("<p style=\"color:green;text-align:center;\">Success</p>"); //println add new line to success
        } else {
            session.setAttribute("SIGNINSTATUS", "Fail");
            out.print("<p style=\"color:red;text-align:center;\">Try Again</p>");
        }
        logger.info("SIGNINSTATUS at SignInProcessServlet: " + session.getAttribute("SIGNINSTATUS"));
    }
}