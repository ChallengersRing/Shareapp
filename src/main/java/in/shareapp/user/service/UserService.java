package in.shareapp.user.service;

import in.shareapp.user.entity.User;

import javax.servlet.http.Part;
import java.util.List;

public interface UserService {
    boolean authenticateUserByEmailAndPassword(User user);
    public boolean checkUserExists(String email);
    boolean registerNewUser(User user);

    String getProfilePictureName(Part part);
    //store in filesystem
    public boolean saveProfilePicture(String serverProfilePicDirectory, String profilePicName, Part part);
    //store in db
    boolean updateProfile(User user);

    User getUserInformationById(Long id);
    List<User> getAllUsersInformation();

}
