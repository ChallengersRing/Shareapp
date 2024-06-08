package in.shareapp.user.service;

import in.shareapp.user.entity.User;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> authenticateUserByEmailAndPassword(final User user);

    boolean checkUserExists(final String email);

    boolean registerNewUser(final User user);

    String getProfilePictureName(final Part part);

    //store in filesystem
    boolean saveProfilePicture(final String serverProfilePicDirectory, final String profilePicName,final Part part);

    //store in db
    boolean updateProfile(final User user);

    User getUserInformationById(final Long id);

    List<User> getAllUsersInformation();

}
