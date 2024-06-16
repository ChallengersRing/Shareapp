package in.shareapp.user.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/signout")
public class SignOutProcessServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(SignOutProcessServlet.class);

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        logger.debug("Signing out user...");
        resp.setContentType("application/json");
        Cookie tokenCookie = new Cookie("TOKEN", "");
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(0); // Set max age to 0 to invalidate the cookie
        resp.addCookie(tokenCookie);
        resp.getWriter().write("{\"success\": true}");
    }
}
