package in.shareapp.user.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/signout")
public class SignOutProcessServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SignOutProcessServlet.class.getName());

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        Cookie tokenCookie = new Cookie("TOKEN", "");
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(0); // Set max age to 0 to invalidate the cookie
        resp.addCookie(tokenCookie);
        resp.getWriter().write("{\"success\": true}");
    }
}
