package in.shareapp.user.dao;

import in.shareapp.dds.DatabaseDataSource;
import in.shareapp.user.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class UserDaoImpl extends DatabaseDataSource implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

    public boolean selectUserByColumnFieldName(User user, String columnName, String value){
        Connection dbCon = null;
        Statement stmt = null;
        ResultSet rs = null;

        boolean status = false;

        try {
            dbCon = getDbConnection();
            stmt = dbCon.createStatement();

            String sql = "SELECT *" +
                    " FROM user_info WHERE " +
                    columnName + "='" + value +
                    "'";

            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user.setId(rs.getInt(1));
                user.setPhoto(rs.getString(2));
                user.setFirstName(rs.getString(3));
                user.setLastName(rs.getString(4));
                user.setDateOfBirth(rs.getString(5));
                user.setGender(rs.getString(6));
                user.setEmail(rs.getString(7));
                user.setPhone(rs.getString(8));
                user.setPassword(rs.getString(9));
                status = true;
            }
        } catch (SQLException sqlEx) {
            logger.warning("Query not execution failed: ");
            sqlEx.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeDbConnection(dbCon);
        }
        return status;
    }

    @Override
    public boolean selectUserByID(User user) {
        boolean status = selectUserByColumnFieldName(user,"user_id", String.valueOf(user.getId()));
        return status;
    }

    @Override
    public boolean selectUserByEmailAndPassword(User user) {
        Connection dbCon = null;
        Statement stmt = null;
        ResultSet rs = null;

        boolean status = false;

        try {
            dbCon = getDbConnection();
            stmt = dbCon.createStatement();

            String sql = "SELECT *" +
                    " FROM user_info WHERE " +
                    "user_email='" + user.getEmail() +
                    "' AND user_password='" + user.getPassword() +
                    "'";

            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user.setId(rs.getInt(1));
                user.setPhoto(rs.getString(2));
                user.setFirstName(rs.getString(3));
                user.setLastName(rs.getString(4));
                user.setDateOfBirth(rs.getString(5));
                user.setGender(rs.getString(6));
                // user.setEmail(email);
                user.setPhone(rs.getString(8));
                // user.setPassword(password);
                status = true;
            }
        } catch (SQLException sqlEx) {
            logger.warning("Query not execution failed: ");
            sqlEx.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeDbConnection(dbCon);
        }

        return status;
    }

    @Override
    public boolean insertUser(User user) {
        boolean status = false;

        Connection dbCon = null;
        Statement stmt = null;

        try {
            dbCon = getDbConnection();
            stmt = dbCon.createStatement();

            String sql = "INSERT INTO user_info(user_photo, user_fname, user_lname, user_email, user_phone, user_password) VALUES('" +
                    user.getPhoto() + "', '" +
                    user.getFirstName() + "', '" +
                    user.getLastName() + "', '" +
                    user.getEmail() + "', '" +
                    user.getPhone() + "', '" +
                    user.getPassword() + "')";

            int row = stmt.executeUpdate(sql);
            if (row != 0) {
                status = true;
            }
        } catch (SQLException sqlEx) {
            logger.warning("Query not execution failed: ");
            sqlEx.printStackTrace();
        } finally {
            closeStatement(stmt);
            closeDbConnection(dbCon);
        }

        return status;
    }

    @Override
    public User[] selectAllUsers() {
        User[] users = null; //will store all users info from the database in this array

        Connection dbCon = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            dbCon = getDbConnection();
            stmt = dbCon.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT * FROM user_info";

            rs = stmt.executeQuery(sql);

            int rowCounter = 0;
            while (rs.next()) {
                rowCounter++;
            }

            users = new User[rowCounter];

            int counter = 0;

            rs.beforeFirst();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setPhoto(rs.getString(2));
                user.setFirstName(rs.getString(3));
                user.setLastName(rs.getString(4));
                user.setDateOfBirth(rs.getString(5));
                user.setGender(rs.getString(6));
                user.setEmail(rs.getString(7));
                user.setPhone(rs.getString(8));
                user.setPassword(rs.getString(9));

                users[counter] = user;

                counter++;
            }
        } catch (SQLException sqlEx) {
            logger.warning("Query not execution failed: ");
            sqlEx.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeDbConnection(dbCon);
        }

        return users;
    }

    @Override
    public boolean updateUser(User user) {
        Connection dbCon = null;
        Statement stmt = null;

        boolean status =false;

        try {
            dbCon = getDbConnection();
            stmt = dbCon.createStatement();

            String sql = "UPDATE shareapp.user_info t SET t.user_photo = '"+ user.getPhoto() +
                    "', t.user_fname = '"+ user.getFirstName() +
                    "', t.user_lname = '"+ user.getLastName()+
                    "', t.user_dob = '"+user.getDateOfBirth()+
                    "', t.user_gender = '"+user.getGender()+
                    "', t.user_phone = '"+user.getPhone()+
                    "', t.user_password = '"+user.getPassword()+
                    "' WHERE t.user_email = '" +user.getEmail()+"'";

            int row = stmt.executeUpdate(sql);
            if(row>0){
                status = true;
            }
        } catch (SQLException sqlEx) {
            logger.warning("Query not execution failed: ");
            sqlEx.printStackTrace();
        } finally {
            closeStatement(stmt);
            closeDbConnection(dbCon);
        }
        return status;
    }
}
