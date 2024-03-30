package in.shareapp.user.servlet;

import in.shareapp.dds.DatabaseDataSource;
import in.shareapp.user.entity.User;
import in.shareapp.user.service.UserService;
import in.shareapp.user.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class ProfileUpdateProcessServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DatabaseDataSource.class.getName());

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.info("From ProfileUpdateProcessServlet: ");

        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();
        User signedInUser = (User) session.getAttribute("USERDETAILS");
        UserService userService = new UserServiceImpl();

        String photo = signedInUser.getPhoto();
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

        if(password.isEmpty()){
            password=signedInUser.getPassword();
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
