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
    public boolean authenticateUserByEmailAndPassword(User user) {
        // TODO: Encrypt passwd.
        Optional<User> dbUser = userDao.selectUserByEmail(user.getEmail());
        if (dbUser.isPresent() && user.getPassword().equals(dbUser.get().getPassword())) {
            user.setId(dbUser.get().getId());
            user.setExtId(dbUser.get().getExtId());
            user.setFirstName(dbUser.get().getFirstName());
            user.setLastName(dbUser.get().getLastName());
            user.setAvatar(dbUser.get().getAvatar());
            user.setEmail(dbUser.get().getEmail());
            user.setDateOfBirth(dbUser.get().getDateOfBirth());
            user.setPhone(dbUser.get().getPhone());
            user.setGender(dbUser.get().getGender());
            return true;
        }
        return false;
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
