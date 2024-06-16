package in.shareapp.user.dao;

import in.shareapp.dds.DatabaseDataSource;
import in.shareapp.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class UserDaoImpl extends DatabaseDataSource implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User selectUserByColumnValue(final String column, final String value) {
        User user = null;
        final String sql = "SELECT * FROM shareapp.user_info WHERE " + column + " = ?";

        try (final Connection dbCon = getDbConnection();
             final PreparedStatement pstmt = dbCon.prepareStatement(sql)) {

            pstmt.setString(1, value);

            try (final ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = createUserFromResultSet(rs);
                }
            }
        } catch (final SQLException sqlEx) {
            logger.error("Error selecting user by column field name.", sqlEx);
        }

        return user;
    }


    @Override
    public Optional<User> selectUserByEmail(final String email) {
        User user = null;

        final String sql = "SELECT * FROM shareapp.user_info WHERE email=?";

        try (final Connection dbCon = getDbConnection();
             final PreparedStatement pstmt = dbCon.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (final ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = createUserFromResultSet(rs);
                }
            }
        } catch (final SQLException sqlEx) {
            logger.error("SelectUserByEmail query execution failed.", sqlEx);
        }

        return Optional.ofNullable(user);
    }

    @Override
    public User selectUserByID(final Long userId) {
        User user = null;
        final String sql = "SELECT * FROM shareapp.user_info WHERE id = ?";

        try (final Connection dbCon = getDbConnection();
             final PreparedStatement pstmt = dbCon.prepareStatement(sql)) {

            pstmt.setLong(1, userId);

            try (final ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = createUserFromResultSet(rs);
                }
            }
        } catch (final SQLException sqlEx) {
            logger.error("Error selecting user by ID.", sqlEx);
        }

        return user;
    }


    @Override
    public User selectUserByExtId(final UUID extId) {
        User user = null;
        final String sql = "SELECT * FROM shareapp.user_info WHERE id = ?";

        try (Connection dbCon = getDbConnection();
             final PreparedStatement pstmt = dbCon.prepareStatement(sql)) {

            pstmt.setObject(1, extId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = createUserFromResultSet(rs);
                }
            }
        } catch (final SQLException sqlEx) {
            logger.error("Error selecting user by external ID.", sqlEx);
        }

        return user;
    }


    @Override
    public boolean insertUser(final User user) {
        boolean status = false;

        final String sql = "INSERT INTO shareapp.user_info(avatar, firstname, lastname, email, phone, password) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (final Connection dbCon = getDbConnection();
             final PreparedStatement pstmt = dbCon.prepareStatement(sql)) {

            pstmt.setString(1, user.getAvatar());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPhone());
            pstmt.setString(6, user.getPassword());

            final int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                status = true;
            }
        } catch (final SQLException sqlEx) {
            logger.error("Error inserting user.", sqlEx);
        }// Resources are automatically closed by try-with-resources

        return status;
    }

    @Override
    @Deprecated(since = "May impact performance")
    public List<User> selectAllUsers() {
        final List<User> userList = new ArrayList<>();

        final String sql = "SELECT * FROM shareapp.user_info";

        try (final Connection dbCon = getDbConnection();
             final PreparedStatement pstmt = dbCon.prepareStatement(sql);
             final ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                userList.add(createUserFromResultSet(rs));
            }
        } catch (final SQLException sqlEx) {
            logger.error("SelectAllUsers query execution failed.", sqlEx);
        }

        return userList;
    }

    @Override
    public boolean updateUser(final User user) {
        boolean status = false;

        final StringBuilder sqlBuilder = new StringBuilder("UPDATE shareapp.user_info SET ");
        final List<Object> params = this.constructUpdateQueryAndParams(sqlBuilder, user);
        sqlBuilder.append(" WHERE email = ? AND deleted = FALSE");

        try (final Connection dbCon = getDbConnection();
             final PreparedStatement pstmt = dbCon.prepareStatement(sqlBuilder.toString())) {

            int paramIndex = 1;
            for (Object param : params) {
                pstmt.setObject(paramIndex++, param);
            }
            pstmt.setString(paramIndex, user.getEmail());

            final int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                status = true;
            }
        } catch (final SQLException sqlEx) {
            logger.error("Error updating user.", sqlEx);
        }

        return status;
    }

    // Should be scheduled cleanup in 90 days.
    @Override
    public boolean deleteUserByExtId(final UUID extId) {
        boolean success = false;
        final String sql = "UPDATE shareapp.user_info SET deleted = TRUE WHERE ext_id = ?";

        try (final Connection dbCon = getDbConnection();
             final PreparedStatement pstmt = dbCon.prepareStatement(sql)) {

            pstmt.setObject(1, extId);

            final int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }
        } catch (final SQLException sqlEx) {
            logger.error("Error soft deleting user by external ID.", sqlEx);
        }

        return success;
    }

    private User createUserFromResultSet(final ResultSet rs) throws SQLException {
        return new User.Builder(rs.getString("email"), rs.getString("password"))
                .id(rs.getLong("id"))
                .extId(rs.getObject("ext_id", UUID.class))
                .avatar(rs.getString("avatar"))
                .firstName(rs.getString("firstname"))
                .lastName(rs.getString("lastname"))
                .gender(rs.getString("gender"))
                .phone(rs.getString("phone"))
                .dateOfBirth(rs.getString("date_of_birth"))
                .isDeleted(rs.getBoolean("deleted"))
                .createdAt(rs.getObject("created_at", LocalDateTime.class))
                .updatedAt(rs.getObject("updated_at", LocalDateTime.class))
                .build();
    }

    private List<Object> constructUpdateQueryAndParams(final StringBuilder sqlBuilder, final User user) {
        final List<Object> params = new ArrayList<>();
        if (user.getAvatar() != null) {
            sqlBuilder.append("avatar = ?, ");
            params.add(user.getAvatar());
        }
        if (user.getFirstName() != null) {
            sqlBuilder.append("firstname = ?, ");
            params.add(user.getFirstName());
        }
        if (user.getLastName() != null) {
            sqlBuilder.append("lastname = ?, ");
            params.add(user.getLastName());
        }
        if (user.getDateOfBirth() != null) {
            sqlBuilder.append("date_of_birth = ?, ");
            params.add(java.sql.Date.valueOf(user.getDateOfBirth()));
        }
        if (user.getGender() != null) {
            sqlBuilder.append("gender = ?, ");
            params.add(user.getGender());
        }
        if (user.getPhone() != null) {
            sqlBuilder.append("phone = ?, ");
            params.add(user.getPhone());
        }
        if (user.getPassword() != null) {
            sqlBuilder.append("password = ?, ");
            params.add(user.getPassword());
        }

        // Remove the trailing comma and space
        sqlBuilder.delete(sqlBuilder.length() - 2, sqlBuilder.length());
        return params;
    }

}
