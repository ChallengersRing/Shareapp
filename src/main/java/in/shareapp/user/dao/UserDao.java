package in.shareapp.user.dao;

import in.shareapp.user.entity.User;
public interface UserDao {
    boolean selectUserByColumnFieldName(User user, String columnName, String value);
    boolean selectUserByID(User user);
    boolean selectUserByEmailAndPassword(User user);
    boolean insertUser(User user);
    User[] selectAllUsers();
    boolean updateUser(User user);
}