package in.shareapp.user.servlet;

import in.shareapp.dds.DatabaseDataSource;
import in.shareapp.security.jwt.JwtUtil;
import in.shareapp.user.entity.User;
import in.shareapp.user.service.UserService;
import in.shareapp.user.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static in.shareapp.utils.ServletUtil.writeJsonResponse;

@WebServlet("/updateprofile")
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 20,
        fileSizeThreshold = 1024 * 1024 * 20, //if uploaded file’s size is greater, then it will be stored on the disk. otherwise memory
        maxRequestSize = 1024 * 1024 * 25, //all files include other form data size
        location = "/" //Temp loc for store upload file
)
public class ProfileUpdateProcessServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DatabaseDataSource.class.getName());

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final Map<String, Object> jsonResponse = new HashMap<>();
        logger.info("From ProfileUpdateProcessServlet: ");

        PrintWriter out = resp.getWriter();
        User signedInUser = (User) req.getAttribute("user");
        if (signedInUser == null) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "User not logged in, post upload failure");
            writeJsonResponse(resp, jsonResponse, HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        UserService userService = new UserServiceImpl();

        String photo = signedInUser.getAvatar();
        String firstName = req.getParameter("fname");
        String lastName = req.getParameter("lname");
        String dob = req.getParameter("dob");
        String gender = req.getParameter("gender");
        String email = signedInUser.getEmail();
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        boolean statusUploadInDatabase = false;
        boolean statusUploadInFileSystem = false;
        StringBuilder messageForClient = new StringBuilder();

        if (password.isEmpty()) {
            password = signedInUser.getPassword();
            messageForClient.append("Password didn't update,\n ");
        }

        // Photo upload in FileSystem
        // Retrieving the photo from request object & extracting the filename
        Part filePart = req.getPart("photo");
        logger.info("FILE Part:" + filePart);

        if (filePart != null && filePart.getSize() > 0) {
            photo = userService.getProfilePictureName(filePart);

            if (!photo.equals("FileNotReceived")) {
                String serverFileDirectory = getServletContext().getRealPath("/") + "ClientResources/ProfilePics";
                statusUploadInFileSystem = userService.saveProfilePicture(serverFileDirectory, photo, filePart);
                messageForClient.append(statusUploadInFileSystem ? "Photo update: success, " : "Photo update: Fail, ");
            } else {
                messageForClient.append("Photo Upload Fail.");
            }
        } else {
            messageForClient.append("No photo received for upload.");
        }

        User user = new User(photo, firstName, lastName, dob, gender, email, phone, password);
        logger.info(user.toString());

        // Details Updating in Database.
        if (email != null) {
            statusUploadInDatabase = userService.updateProfile(user);

            if (statusUploadInDatabase) {
                String token = JwtUtil.generateToken(user);
                JwtUtil.addTokenToResponse(token, resp);
                messageForClient.append("Details update: success, ");
            } else {
                messageForClient.append("Details update: Fail, ");
            }
        }

        logger.info("statusFileSystem: " + statusUploadInFileSystem);
        logger.info("statusDatabase: " + statusUploadInDatabase);
        logger.info(messageForClient.toString());
        out.print(messageForClient);
    }
}
