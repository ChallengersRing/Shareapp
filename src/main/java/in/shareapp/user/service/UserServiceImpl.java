package in.shareapp.user.service;

import in.shareapp.user.dao.UserDao;
import in.shareapp.user.dao.UserDaoImpl;
import in.shareapp.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public Optional<User> authenticateUserByEmailAndPassword(final User user) {
        // TODO: Encrypt passwd.
        final Optional<User> dbUserOptional = userDao.selectUserByEmail(user.getEmail());
        if (dbUserOptional.isPresent() && user.getPassword().equals(dbUserOptional.get().getPassword())) {
            final User dbUser = dbUserOptional.get();
            final User authenticatedUser = new User.Builder(user.getEmail(), user.getPassword())
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
    public boolean checkUserExists(final String email) {
        return Optional.ofNullable(userDao.selectUserByColumnValue("email", email)).isPresent();
    }

    @Override
    public boolean registerNewUser(final User user) {
        return userDao.insertUser(user);
    }

    @Override
    public String getProfilePictureName(final Part part) {
        return part.getSubmittedFileName().isEmpty() ? "" : part.getSubmittedFileName();
    }

    @Override
    public boolean saveProfilePicture(final String serverProfilePicDirectory, final String profilePicName, final Part part) {
        try {
            part.write(serverProfilePicDirectory + "\\" + profilePicName);
            return true;
        } catch (IOException ioException) {
            logger.error("Fail to save profile pic in FS.", ioException);
            return false;
        }
    }

    public User getUserInformationById(final Long userId) {
        return userDao.selectUserByID(userId);
    }

    @Override
    public List<User> getAllUsersInformation() {
        return userDao.selectAllUsers();
    }

    @Override
    public boolean updateProfile(final User user) {
        return userDao.updateUser(user);
    }
}
