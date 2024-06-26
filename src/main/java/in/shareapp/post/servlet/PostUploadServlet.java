package in.shareapp.post.servlet;

import in.shareapp.post.entity.Post;
import in.shareapp.post.service.PostService;
import in.shareapp.post.service.PostServiceImpl;
import in.shareapp.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static in.shareapp.utils.ServletUtil.writeJsonResponse;

@WebServlet("/postupload")
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 20,
        fileSizeThreshold = 1024 * 1024 * 20, //if uploaded file’s size is greater, then it will be stored on the disk. otherwise memory
        maxRequestSize = 1024 * 1024 * 25, //all files include other form data size
        location = "/" //Temp loc for store upload file
)
public class PostUploadServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(PostUploadServlet.class);

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final Map<String, Object> jsonResponse = new HashMap<>();
        logger.info("Trying to upload post...");

        final User user = (User) req.getAttribute("user");
        if (user == null) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "User not logged in, post upload failure");
            writeJsonResponse(resp, jsonResponse, HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        final String title = req.getParameter("title");
        final String description = req.getParameter("description");
        if (title == null || description == null) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Title or description not provided, post upload failure");
            writeJsonResponse(resp, jsonResponse, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        final Part thumbnailFile = req.getPart("thumbnail");
        final Part videoFile = req.getPart("video");
        if (thumbnailFile == null || videoFile == null) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Thumbnail or video file not received, post upload failure");
            writeJsonResponse(resp, jsonResponse, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        final String thumbnailFilename = getFileName(thumbnailFile);
        final String videoFilename = getFileName(videoFile);
        if (thumbnailFilename.equals("PostNotReceived") || videoFilename.equals("PostNotReceived")) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Thumbnail or video file not received from client, post upload failure");
            writeJsonResponse(resp, jsonResponse, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        final PostService postService = new PostServiceImpl();
        final String date = getDateTime();
        final int views = 0;
        final int likes = 0;
        final String comments = "No Comments";

        final Long userId = 1L;
        final Post post = new Post(userId, videoFilename, title, thumbnailFilename, description, date, views, likes, comments);

        // Status after saving in database.
        final boolean dbStatus = postService.uploadUserPost(post);
        if (!dbStatus) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Database post failure");
            writeJsonResponse(resp, jsonResponse, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Setting the file directory where to store the posts.
        final String serverFileDirectory = getServletContext().getRealPath("/") + "ClientResources/Posts";
        // Store in filesystem after successful update in db.
        final boolean fsStatusThumbnail = postService.savePostFile(serverFileDirectory, thumbnailFilename, videoFile);
        final boolean fsStatusVideo = postService.savePostFile(serverFileDirectory, videoFilename, thumbnailFile);
        if (!fsStatusThumbnail || !fsStatusVideo) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "File System failure");
            writeJsonResponse(resp, jsonResponse, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        jsonResponse.put("status", "success");
        writeJsonResponse(resp, jsonResponse, 200);
        logger.info("Successfully uploaded post");
    }

    private String getDateTime() {
        final LocalDateTime now = LocalDateTime.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    private String getFileName(Part part) {
        return (part != null) ? part.getSubmittedFileName() : "PostNotReceived";
    }

    private void forwardToIndexPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final RequestDispatcher requestDispatcher = req.getRequestDispatcher("./index.jsp");
        requestDispatcher.forward(req, resp);
    }
}
