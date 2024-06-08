package in.shareapp.user.dao;

import in.shareapp.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    User selectUserByColumnValue(final String column, final String value);

    User selectUserByID(final Long userId);

    User selectUserByExtId(final UUID extId);

    Optional<User> selectUserByEmail(final String email);

    boolean insertUser(final User user);

    List<User> selectAllUsers();

    boolean updateUser(final User user);

    boolean deleteUserByExtId(final UUID id);
}