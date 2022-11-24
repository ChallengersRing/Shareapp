package in.shareapp.post.servlet;

import in.shareapp.post.entity.Post;
import in.shareapp.post.service.PostService;
import in.shareapp.post.service.PostServiceImpl;
import in.shareapp.user.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class PostUploadServlet extends HttpServlet {
    private String getDateTime() {
        GregorianCalendar date = new GregorianCalendar();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DATE);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        int seconds = date.get(Calendar.SECOND);

        //Format: "'2022-12-20 16:06:15'"
        String dateTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + seconds;

        return dateTime;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        System.out.println("From PostUploadServlet:-----");

        User user = (User) session.getAttribute("USERDETAILS");
        if (user != null) {
            int userId = user.getId();
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

            System.out.println("Post Details: " + userId +
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
                        System.out.println("Post upload success");
                    }else {
                        System.out.println("File System failure");
                    }
                } else {
                    //Database insert failure
                    System.out.println("Database post failure");
                }
            } else {
                //Post receiving from client failure
                System.out.println("Post receiving failure");
            }
        } else {
            // User didn't login post upload failure
            System.out.println("User didn't login post upload failure");
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("./index.jsp");
        requestDispatcher.forward(req,resp);
    }
}
