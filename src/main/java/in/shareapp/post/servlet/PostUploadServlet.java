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
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        return formattedDateTime;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        logger.info("From PostUploadServlet:-----");

        User user = (User) session.getAttribute("USERDETAILS");
        if (user != null) {
            Long userId = user.getId();
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String thumbnailFilename = null;
            String videoFilename = null;
            String date = getDateTime();
            int views = 0;
            int likes = 0;
            String comments = "No Comments";


            //Receiving the "file" from request object, extract "filename"
            Part thumbnailFile = req.getPart("thumbnail");
            Part videoFile = req.getPart("video");
            PostService postService = new PostServiceImpl();
            thumbnailFilename = postService.getPostFileName(thumbnailFile);
            videoFilename = postService.getPostFileName(videoFile);

            logger.info("Post Details: " + userId +
                    " " + videoFilename + " " + title + " " +
                    thumbnailFilename + " " + " " + description + " " + date);

            Post post = new Post(userId, videoFilename, title, thumbnailFilename, description, date, views, likes, comments);

            req.setAttribute("PostUpload","fail");

            if (!(videoFilename.equals("PostNotReceived")) && !(thumbnailFilename.equals("PostNotReceived"))) {
                //Status after SAVING in DATABASE.
                boolean dbStatus = postService.uploadUserPost(post);

                //Setting the file directory where to store the posts.
                String serverFileDirectory = getServletContext().getRealPath("/") + "ClientResources/Posts";

                //STORE IN FILESYSTEM after successfully update in db.
                if (dbStatus) {
                    boolean fsStatusThumbnail = postService.savePostFile(serverFileDirectory, thumbnailFilename, videoFile);
                    boolean fsStatusVideo = postService.savePostFile(serverFileDirectory, videoFilename, thumbnailFile);

                    if(fsStatusThumbnail && fsStatusVideo){
                        req.setAttribute("PostUpload","success");
                        logger.fine("Post upload success");
                    }else {
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

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("./index.jsp");
        requestDispatcher.forward(req,resp);
    }
}
