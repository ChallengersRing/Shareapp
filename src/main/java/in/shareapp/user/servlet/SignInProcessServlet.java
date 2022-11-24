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

public class SignInProcessServlet extends HttpServlet {
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

        System.out.println("From SignInProcessServlet:---");
        System.out.println(email + " " + password);
        System.out.println("Result:" + result);
        System.out.println(user.getFirstName() + " " +
                user.getEmail() + " " +
                user.getPassword());

        if (result) {
            session.setAttribute("SIGNINSTATUS", "Success");
            session.setAttribute("USERDETAILS",user); //if i store user object it vanish after server restart.
            out.print("<p style=\"color:green;text-align:center;\">Success</p>"); //println add new line to success
        } else {
            session.setAttribute("SIGNINSTATUS", "Fail");
            out.print("<p style=\"color:red;text-align:center;\">Try Again</p>");
        }
        System.out.println("SIGNINSTATUS at SignInProcessServlet: "+session.getAttribute("SIGNINSTATUS"));
    }
}