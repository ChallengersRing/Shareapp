package in.shareapp.user.dao;

import in.shareapp.dds.DatabaseDataSource;
import in.shareapp.user.entity.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

public class UserDaoImpl extends DatabaseDataSource implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

    @Override
    public User selectUserByColumnValue(String column, String value) {
        User user = null;
        final String sql = "SELECT * FROM shareapp.user_info WHERE " + column + " = ?";

        try (Connection dbCon = getDbConnection();
             PreparedStatement pstmt = dbCon.prepareStatement(sql)) {

            pstmt.setString(1, value);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = createUserFromResultSet(rs);
                }
            }
        } catch (SQLException sqlEx) {
            logger.warning("Error selecting user by column field name: " + sqlEx.getMessage());
        }

        return user;
    }


    @Override
    public Optional<User> selectUserByEmail(String email) {
        User user = null;

        final String sql = "SELECT * FROM shareapp.user_info WHERE email=?";

        try (Connection dbCon = getDbConnection();
             PreparedStatement pstmt = dbCon.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = createUserFromResultSet(rs);
                }
            }
        } catch (SQLException sqlEx) {
            logger.warning("Query execution failed: " + sqlEx.getMessage());
        }

        return Optional.ofNullable(user);
    }

    @Override
    public User selectUserByID(Long userId) {
        User user = null;
        final String sql = "SELECT * FROM shareapp.user_info WHERE id = ?";

        try (Connection dbCon = getDbConnection();
             PreparedStatement pstmt = dbCon.prepareStatement(sql)) {

            pstmt.setLong(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = createUserFromResultSet(rs);
                }
            }
        } catch (SQLException sqlEx) {
            logger.warning("Error selecting user by ID: " + sqlEx.getMessage());
        }

        return user;
    }


    @Override
    public User selectUserByExtId(UUID extId) {
        User user = null;
        final String sql = "SELECT * FROM shareapp.user_info WHERE id = ?";

        try (Connection dbCon = getDbConnection();
             PreparedStatement pstmt = dbCon.prepareStatement(sql)) {

            pstmt.setObject(1, extId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = createUserFromResultSet(rs);
                }
            }
        } catch (SQLException sqlEx) {
            logger.warning("Error selecting user by external ID: " + sqlEx.getMessage());
        }

        return user;
    }


    @Override
    public boolean insertUser(User user) {
        boolean status = false;

        final String sql = "INSERT INTO shareapp.user_info(avatar, firstname, lastname, email, phone, password) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection dbCon = getDbConnection();
             PreparedStatement pstmt = dbCon.prepareStatement(sql)) {

            pstmt.setString(1, user.getAvatar());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPhone());
            pstmt.setString(6, user.getPassword());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                status = true;
            }
        } catch (SQLException sqlEx) {
            logger.warning("Error inserting user: " + sqlEx.getMessage());
            //TODO: Custom exception throw.
        }// Resources are automatically closed by try-with-resources

        return status;
    }

    @Override
    @Deprecated(since = "May impact performance")
    public List<User> selectAllUsers() {
        List<User> userList = new ArrayList<>();

        final String sql = "SELECT * FROM shareapp.user_info";

        try (Connection dbCon = getDbConnection();
             PreparedStatement pstmt = dbCon.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                userList.add(createUserFromResultSet(rs));
            }
        } catch (SQLException sqlEx) {
            logger.warning("Query execution failed: " + sqlEx.getMessage());
        }

        return userList;
    }


    @Override
    public boolean updateUser(User user) {
        boolean status = false;

        String sql = "UPDATE shareapp.user_info SET avatar = ?, firstname = ?, lastname = ?, " +
                "date_of_birth = ?, gender = ?, phone = ?, password = ? WHERE email = ? AND deleted = FALSE";

        try (Connection dbCon = getDbConnection();
             PreparedStatement pstmt = dbCon.prepareStatement(sql)) {

            pstmt.setString(1, user.getAvatar());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());

            if (user.getDateOfBirth() != null && !user.getDateOfBirth().equals("null")) {
                pstmt.setDate(4, java.sql.Date.valueOf(user.getDateOfBirth()));
            } else {
                pstmt.setNull(4, java.sql.Types.DATE);
            }

            pstmt.setString(5, user.getGender());
            pstmt.setString(6, user.getPhone());
            pstmt.setString(7, user.getPassword());
            pstmt.setString(8, user.getEmail());

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                status = true;
            }
        } catch (SQLException sqlEx) {
            logger.warning("Error updating user: " + sqlEx.getMessage());
        }

        return status;
    }

    // Should be scheduled cleanup in 90 days.
    @Override
    public boolean deleteUserByExtId(UUID extId) {
        boolean success = false;
        String sql = "UPDATE shareapp.user_info SET deleted = TRUE WHERE ext_id = ?";

        try (Connection dbCon = getDbConnection();
             PreparedStatement pstmt = dbCon.prepareStatement(sql)) {

            pstmt.setObject(1, extId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }
        } catch (SQLException sqlEx) {
            logger.warning("Error soft deleting user by external ID: " + sqlEx.getMessage());
        }

        return success;
    }

    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getLong("id"));
        user.setExtId(rs.getObject("ext_id", UUID.class));
        user.setAvatar(rs.getString("avatar"));
        user.setFirstName(rs.getString("firstname"));
        user.setLastName(rs.getString("lastname"));
        user.setGender(rs.getString("gender"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setPassword(rs.getString("password"));
        user.setDateOfBirth(rs.getString("date_of_birth"));
        user.setDeleted(rs.getBoolean("deleted"));
        user.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        user.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));

        return user;
    }

}
