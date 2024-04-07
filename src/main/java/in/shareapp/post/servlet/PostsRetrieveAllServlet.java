package in.shareapp.post.servlet;

import in.shareapp.post.entity.Post;
import in.shareapp.post.service.PostService;
import in.shareapp.post.service.PostServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class PostsRetrieveAllServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(PostsRetrieveAllServlet.class.getName());

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Loading all posts...");
        PostService postService = new PostServiceImpl();
        List<Post> posts = postService.retrieveAllUsersPost();

        //forwarding request to PostListAllUsers.jsp to display the all post in html content
        req.setAttribute("POSTLISTALLUSERS", posts);
        List<Post> posts1 = (List<Post>) req.getAttribute("POSTLISTALLUSERS");
        RequestDispatcher rd = req.getRequestDispatcher("./jsp/PostListAllUsers.jsp");
        rd.forward(req, resp);
    }
}
