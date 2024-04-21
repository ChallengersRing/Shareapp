package in.shareapp.user.service;

import in.shareapp.user.dao.UserDao;
import in.shareapp.user.dao.UserDaoImpl;
import in.shareapp.user.entity.User;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    UserDao userDao = new UserDaoImpl();

    @Override
    public Optional<User> authenticateUserByEmailAndPassword(User user) {
        // TODO: Encrypt passwd.
        Optional<User> dbUserOptional = userDao.selectUserByEmail(user.getEmail());
        if (dbUserOptional.isPresent() && user.getPassword().equals(dbUserOptional.get().getPassword())) {
            User dbUser = dbUserOptional.get();
            User authenticatedUser = new User.Builder(user.getEmail(), user.getPassword())
                    .id(dbUser.getId())
                    .extId(dbUser.getExtId())
                    .firstName(dbUser.getFirstName())
                    .lastName(dbUser.getLastName())
                    .avatar(dbUser.getAvatar())
                    .dateOfBirth(dbUser.getDateOfBirth())
                    .gender(dbUser.getGender())
                    .phone(dbUser.getPhone())
                    .isDeleted(dbUser.isDeleted())
                    .createdAt(dbUser.getCreatedAt())
                    .updatedAt(dbUser.getUpdatedAt())
                    .build();
            return Optional.of(authenticatedUser);
        }
        return Optional.empty();
    }

    @Override
    public boolean checkUserExists(String email) {
        return Optional.ofNullable(userDao.selectUserByColumnValue("email", email)).isPresent();
    }

    @Override
    public boolean registerNewUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public String getProfilePictureName(Part part) {
        return part.getSubmittedFileName().isEmpty() ? "FileNotReceived" : part.getSubmittedFileName();
    }

    @Override
    public boolean saveProfilePicture(String serverProfilePicDirectory, String profilePicName, Part part) {
        try {
            part.write(serverProfilePicDirectory + "\\" + profilePicName);
            return true;
        } catch (IOException ioException) {
            logger.log(Level.SEVERE, "Fail to save profile pic in FS", ioException);
            return false;
        }
    }

    public User getUserInformationById(Long userId) {
        return userDao.selectUserByID(userId);
    }

    @Override
    public List<User> getAllUsersInformation() {
        return userDao.selectAllUsers();
    }

    @Override
    public boolean updateProfile(User user) {
        return userDao.updateUser(user);
    }

}
