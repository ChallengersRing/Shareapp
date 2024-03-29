package in.shareapp.user.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class LinkControllerServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LinkControllerServlet.class.getName());

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.service(req, resp);
        //service?rqst=signout

        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();

        String linkName = req.getParameter("rqst");

        if(linkName.equals("signin")) {
            out.println("success");
        } else if(linkName.equals("signout")) {
                session.invalidate();
            out.print("success");
            logger.info("SIGNINSTATUS at ServiceControllerServlet: "+session.getAttribute("SIGNINSTATUS"));
        } else if(linkName.equals("signup")) {
            out.print("success");
        }
    }
}