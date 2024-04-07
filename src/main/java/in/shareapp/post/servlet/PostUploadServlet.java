package in.shareapp.post.servlet;

import in.shareapp.post.entity.Post;
import in.shareapp.post.service.PostService;
import in.shareapp.post.service.PostServiceImpl;
import in.shareapp.user.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class PostUploadServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(PostUploadServlet.class.getName());

    private String getDateTime() {
        final LocalDateTime now = LocalDateTime.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();

        logger.info("From PostUploadServlet:-----");

        final User user = (User) session.getAttribute("USERDETAILS");
        if (user != null) {
            final String date = this.getDateTime();
            final int views = 0;
            final int likes = 0;
            final String comments = "No Comments";

            final Long userId = user.getId();
            final String title = req.getParameter("title");
            final String description = req.getParameter("description");

            //Receiving the "file" from request object, extract "filename"
            final Part thumbnailFile = req.getPart("thumbnail");
            final Part videoFile = req.getPart("video");
            final PostService postService = new PostServiceImpl();
            final String thumbnailFilename = postService.getPostFileName(thumbnailFile);
            final String videoFilename = postService.getPostFileName(videoFile);

            logger.info("Post Details: " + userId +
                    " " + videoFilename + " " + title + " " +
                    thumbnailFilename + " " + " " + description + " " + date);

            final Post post = new Post(userId, videoFilename, title, thumbnailFilename, description, date, views, likes, comments);

            req.setAttribute("PostUpload", "fail");

            if (!(videoFilename.equals("PostNotReceived")) && !(thumbnailFilename.equals("PostNotReceived"))) {
                //Status after SAVING in DATABASE.
                final boolean dbStatus = postService.uploadUserPost(post);

                //Setting the file directory where to store the posts.
                final String serverFileDirectory = getServletContext().getRealPath("/") + "ClientResources/Posts";

                //STORE IN FILESYSTEM after successfully update in db.
                if (dbStatus) {
                    final boolean fsStatusThumbnail = postService.savePostFile(serverFileDirectory, thumbnailFilename, videoFile);
                    final boolean fsStatusVideo = postService.savePostFile(serverFileDirectory, videoFilename, thumbnailFile);

                    if (fsStatusThumbnail && fsStatusVideo) {
                        req.setAttribute("PostUpload", "success");
                        logger.fine("Post upload success");
                    } else {
                        logger.severe("File System failure");
                    }
                } else {
                    //Database insert failure
                    logger.warning("Database post failure");
                }
            } else {
                //Post receiving from client failure
                logger.warning("Post receiving failure");
            }
        } else {
            // User didn't login post upload failure
            logger.warning("User didn't login post upload failure");
        }

        final RequestDispatcher requestDispatcher = req.getRequestDispatcher("./index.jsp");
        requestDispatcher.forward(req, resp);
    }
}
