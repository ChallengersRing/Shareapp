package test;

import in.shareapp.user.dao.UserDao;
import in.shareapp.user.dao.UserDaoImpl;
import in.shareapp.user.entity.User;

public class ShareappTest {
    public static void main(String[] args) {
        User user = new User("abhayraj7631@gmail.com", "123");
        System.out.println(user.getId() + " " +
                user.getAvatar() + " " +
                user.getFirstName() + " " +
                user.getLastName() + " " +
                user.getDateOfBirth() + " " +
                user.getEmail() + " " +
                user.getPhone() + " " +
                user.getPassword() + "\n"
        );

        UserDao userDao = new UserDaoImpl();
        userDao.insertUser(user);
        userDao.selectUserByEmail(user.getEmail());
        System.out.println(user.getId() + " " +
                user.getAvatar() + " " +
                user.getFirstName() + " " +
                user.getLastName() + " " +
                user.getDateOfBirth() + " " +
                user.getEmail() + " " +
                user.getPhone() + " " +
                user.getPassword() + "\n"
        );

        user = new User("abhayraj7631@gmail.com", "1234");
        System.out.println(user.getId() + " " +
                user.getAvatar() + " " +
                user.getFirstName() + " " +
                user.getLastName() + " " +
                user.getDateOfBirth() + " " +
                user.getEmail() + " " +
                user.getPhone() + " " +
                user.getPassword() + "\n"
        );
    }
}
