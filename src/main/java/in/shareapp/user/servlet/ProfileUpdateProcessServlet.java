package in.shareapp.user.servlet;

import in.shareapp.user.entity.User;
import in.shareapp.user.service.UserService;
import in.shareapp.user.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class ProfileUpdateProcessServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("From ProfileUpdateProcessServlet: ");

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
        String messageForClient = "";

        if(password==""){
            password=signedInUser.getPassword();
            messageForClient = messageForClient + "Password didn't update,\n ";
        }

        User user = new User(photo, firstName, lastName, dob, gender, email, phone, password);

        System.out.println(user.toString());



        // Details Updating in Database.
        if(email!=null) {
            statusUploadInDatabase = userService.updateProfile(user);

            if (statusUploadInDatabase) {
                messageForClient = messageForClient + "Details update: success, ";
            } else {
                messageForClient = messageForClient + "Details update: Fail, ";
            }
        }

        //Photo upload in FileSystem
        /*
            // Retrieving the photo from request object & extracting the filename
            Part filePart = req.getPart("photo");
            System.out.println("FILE Part:" + filePart);
            photo = userService.getProfilePictureName(filePart);


            if (photo.equals("FileNotReceived")) {
                messageForClient = messageForClient + "Photo Upload Fail.";
            } else {
                String serverFileDirectory = getServletContext().getRealPath("/") + "ClientResources/ProfilePics";
                statusUploadInFileSystem = userService.saveProfilePicture(serverFileDirectory, photo, filePart); //*
            }

         */
        if (statusUploadInFileSystem) {
            messageForClient = messageForClient + "Photo update: success, ";
        } else {
            messageForClient = messageForClient + "Photo update: Fail, ";
        }

        System.out.println("statusFileSystem: " + statusUploadInFileSystem);
        System.out.println("statusDatabase: " + statusUploadInDatabase);
        System.out.println(messageForClient);
        out.print(messageForClient);
    }
}
