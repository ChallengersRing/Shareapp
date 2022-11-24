package in.shareapp.user.service;

import in.shareapp.user.entity.User;

import javax.servlet.http.Part;

public interface UserService {
    boolean authenticateUserByEmailAndPassword(User user);
    public boolean checkUserBeforeRegister(User user);
    boolean registerNewUser(User user);

    String getProfilePictureName(Part part);
    //store in filesystem
    public boolean saveProfilePicture(String serverProfilePicDirectory, String profilePicName, Part part);
    //store in db
    boolean updateProfile(User user);

    boolean getUserInformationByID(User user);
    User[] getAllUsersInformation();

}
