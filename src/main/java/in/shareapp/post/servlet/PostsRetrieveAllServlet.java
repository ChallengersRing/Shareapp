package in.shareapp.post.servlet;

import in.shareapp.post.entity.Post;
import in.shareapp.post.service.PostService;
import in.shareapp.post.service.PostServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "PostsRetrieveAllServlet",
        description = "Retrieve all public posts",
        urlPatterns = {"/postretrieveall"})
public class PostsRetrieveAllServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(PostsRetrieveAllServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Loading all posts...");
        PostService postService = new PostServiceImpl();
        List<Post> posts = postService.retrieveAllUsersPost();

        //forwarding request to PostListAllUsers.jsp to display the all post in html content
        req.setAttribute("POSTLISTALLUSERS", posts);
        RequestDispatcher rd = req.getRequestDispatcher("./jsp/PostListAllUsers.jsp");
        rd.forward(req, resp);
    }
}
