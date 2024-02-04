package in.shareapp.user.servlet;

import in.shareapp.user.entity.User;
import in.shareapp.user.service.UserService;
import in.shareapp.user.service.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class SignUpProcessServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SignUpProcessServlet.class.getName());

    UserService userService = new UserServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("From SignUpProcessServlet");
        HttpSession session = req.getSession();
        PrintWriter out = resp.getWriter();

        String photo = "defaultprofilepic.png";
        String firstName = req.getParameter("fname");
        String lastName = req.getParameter("lname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        User user = new User(photo, firstName, lastName, email, phone, password);
        boolean isUserAvailable = userService.checkUserBeforeRegister(user);
        logger.info("isUserAvailable: " + isUserAvailable);
        if (!isUserAvailable) {
            boolean result = userService.registerNewUser(user);
            RequestDispatcher rd;

            if (result) {
                session.setAttribute("SIGNUPSTATUS", "Success");
                out.print("<p style=\"color:green;text-align:center;\">Success</p>");
            } else {
                session.setAttribute("SIGNUPSTATUS", "Fail");
                out.print("<p style=\"color:red;text-align:center;\">Try Again</p>");
            }
        } else {
            session.setAttribute("SIGNUPSTATUS", "Fail");
            out.print("<p style=\"color:red;text-align:center;\">User Already Available</p>");
        }
        logger.info("SIGNUPSTATUS: " + session.getAttribute("SIGNUPSTATUS"));
    }
}