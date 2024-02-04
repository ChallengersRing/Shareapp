package in.shareapp.user.service;

import in.shareapp.user.dao.UserDao;
import in.shareapp.user.dao.UserDaoImpl;
import in.shareapp.user.entity.User;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService{
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    UserDao userDao = new UserDaoImpl();

    @Override
    public boolean authenticateUserByEmailAndPassword(User user) {
        return userDao.selectUserByEmailAndPassword(user);
    }

    @Override
    public boolean checkUserBeforeRegister(User user){
        return userDao.selectUserByColumnFieldName(user,"user_email",user.getEmail());
    }

    @Override
    public boolean registerNewUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public String getProfilePictureName(Part part) {
        String profilePicName = (part.getSubmittedFileName().length()) == 0 ? "FileNotReceived" : part.getSubmittedFileName();

        return profilePicName;
    }

    @Override
    public boolean saveProfilePicture(String serverProfilePicDirectory, String profilePicName, Part part) {
        try {
            part.write(serverProfilePicDirectory + "\\" + profilePicName);
            return true;
        } catch (IOException ioException) {
            logger.log(Level.SEVERE, "Fail to save profile pic in FS", ioException);
            ioException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean getUserInformationByID(User user) {
        return userDao.selectUserByID(user);
    }

    @Override
    public User[] getAllUsersInformation() {
        return userDao.selectAllUsers();
    }

    @Override
    public boolean updateProfile(User user) {
        boolean status = userDao.updateUser(user);
        return status;
    }

}
