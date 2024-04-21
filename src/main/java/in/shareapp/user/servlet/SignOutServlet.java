package in.shareapp.user.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet("/signout")
public class SignOutServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SignOutServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        Cookie tokenCookie = new Cookie("TOKEN", "");
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(0); // Set max age to 0 to invalidate the cookie
        resp.addCookie(tokenCookie);
        out.write("{\"success\": true}");
    }
}
