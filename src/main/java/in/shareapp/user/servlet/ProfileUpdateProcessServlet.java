package in.shareapp.user.servlet;

import in.shareapp.dds.DatabaseDataSource;
import in.shareapp.security.jwt.JwtUtil;
import in.shareapp.user.entity.User;
import in.shareapp.user.service.UserService;
import in.shareapp.user.service.UserServiceImpl;
import in.shareapp.utils.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@WebServlet("/updateprofile")
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 20,
        fileSizeThreshold = 1024 * 1024 * 20, //if uploaded file’s size is greater, then it will be stored on the disk. otherwise memory
        maxRequestSize = 1024 * 1024 * 25, //all files include other form data size
        location = "/" //Temp loc for store upload file
)
public class ProfileUpdateProcessServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DatabaseDataSource.class.getName());
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        logger.info("Try to update profile for user.");

        final User signedInUser = (User) req.getAttribute("user");
        if (signedInUser == null) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "User not logged in, post upload failure");
            ServletUtil.writeJsonResponse(resp, jsonResponse, HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        final StringBuilder messageForClient = new StringBuilder();
        final String avatar = this.saveAvatar(req.getPart("photo"), messageForClient);
        final User userDetails = this.extractUserDetails(req, signedInUser, avatar.isEmpty() ? signedInUser.getAvatar() : avatar);

        this.updateUserInDatabaseAndRefreshToken(userDetails, messageForClient, resp);

        logger.info("Message for Client: " + messageForClient);
        resp.getWriter().write(messageForClient.toString());
    }

    private String saveAvatar(Part filePart, StringBuilder messageForClient) {
        if (filePart == null || filePart.getSize() <= 0) {
            messageForClient.append("No photo received for upload. ");
            return "";
        }

        //TODO: verify image file type, size, dimension. (Maybe use TIKA)
        String avatar = this.userService.getProfilePictureName(filePart).trim();
        if (avatar.isEmpty()) {
            messageForClient.append("Unsupported file received. ");
            return "";
        }

        String uniqueIdentifier = UUID.randomUUID().toString();
        avatar = avatar.length() > 15 ? avatar.substring(0, 15).concat(uniqueIdentifier) : avatar.concat(uniqueIdentifier);

        final String serverFileDirectory = getServletContext().getRealPath("/") + "ClientResources/ProfilePics";
        final boolean statusUploadInFileSystem = this.userService.saveProfilePicture(serverFileDirectory, avatar, filePart);

        if (statusUploadInFileSystem) {
            messageForClient.append("Photo saved successfully. ");
            return avatar;
        } else {
            messageForClient.append("Fail to save Photo. ");
            return "";
        }
    }

    private User extractUserDetails(final HttpServletRequest req, final User signedInUser, final String avatar) {
        final String firstName = this.sanitize(req.getParameter("fname"));
        final String lastName = this.sanitize(req.getParameter("lname"));
        final String dob = this.sanitize(req.getParameter("dob"));
        final String gender = this.sanitize(req.getParameter("gender"));
        final String phone = this.sanitize(req.getParameter("phone"));
        final String password = this.sanitize(req.getParameter("password"));
        final String email = signedInUser.getEmail();

        return new User(signedInUser.getExtId(), avatar, firstName, lastName, dob, gender, email, phone, password);
    }

    private String sanitize(final String value) {
        return (value != null && !value.isEmpty() && !value.trim().equalsIgnoreCase("null")) ? value.trim() : null;
    }

    private void updateUserInDatabaseAndRefreshToken(final User userDetails, final StringBuilder messageForClient,
                                                     final HttpServletResponse resp) {
        final boolean statusUploadInDatabase = this.userService.updateProfile(userDetails);
        if (statusUploadInDatabase) {
            final String token = JwtUtil.generateToken(userDetails);
            JwtUtil.addTokenToResponse(token, resp);
            messageForClient.append("Details updated successfully.");
        } else {
            messageForClient.append("Fail to update Details.");
            logger.warning("Fail to update user in database");
        }
    }
}
