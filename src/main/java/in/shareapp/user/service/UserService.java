package in.shareapp.user.service;

import in.shareapp.user.entity.User;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> authenticateUserByEmailAndPassword(User user);

    boolean checkUserExists(String email);

    boolean registerNewUser(User user);

    String getProfilePictureName(Part part);

    //store in filesystem
    boolean saveProfilePicture(String serverProfilePicDirectory, String profilePicName, Part part);

    //store in db
    boolean updateProfile(User user);

    User getUserInformationById(Long id);

    List<User> getAllUsersInformation();

}
