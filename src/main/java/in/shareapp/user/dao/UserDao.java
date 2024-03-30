package in.shareapp.user.dao;

import in.shareapp.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    User selectUserByColumnValue(String column, String value);

    User selectUserByID(Long userId);

    User selectUserByExtId(UUID extId);

    Optional<User> selectUserByEmail(String email);

    boolean insertUser(User user);

    List<User> selectAllUsers();

    boolean updateUser(User user);

    boolean deleteUserByExtId(UUID id);
}