package in.shareapp.post.dao;

import in.shareapp.dao.DatabaseDataSource;
import in.shareapp.post.entity.Post;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostDaoImpl extends DatabaseDataSource implements PostDao {
    @Override
    public boolean uploadPost(Post post) {
        boolean status = false;

        Connection dbCon = null;
        Statement stmt = null;

        try {
            dbCon = getDbConnection();
            stmt = dbCon.createStatement();

            String sql = "INSERT INTO user_post(user_id,post_file,post_title,post_thumbnail,post_description,post_date,post_views,post_likes,post_comments)VALUES('" +
                    post.getUserId() + "', '" +
                    post.getPostFile() + "', '" +
                    post.getPostTitle() + "', '" +
                    post.getPostThumbnail() + "', '" +
                    post.getPostDescription() + "', '" +
                    post.getPostDate() + "', '" +
                    post.getPostViews() + "', '" +
                    post.getPostLikes() + "', '" +
                    post.getPostComments() + "')";

            int row = stmt.executeUpdate(sql);
            if (row != 0) {
                status = true;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            closeStatement(stmt);
            closeDbConnection(dbCon);
        }

        return status;
    }

    @Override
    public boolean retrievePost(Post post) {
        Connection dbCon = null;
        Statement stmt = null;
        ResultSet rs = null;

        boolean status = false;

        try {
            dbCon = getDbConnection();
            stmt = dbCon.createStatement();

            String sql = "SELECT *" +
                    " FROM user_post WHERE " +
                    "user_id='" + post.getUserId() +
                    "'";

            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                post.setPostId(rs.getInt(1));
                //post.setUserId(rs.getInt(2));
                post.setPostFile(rs.getString(3));
                post.setPostTitle(rs.getString(4));
                post.setPostThumbnail(rs.getString(5));
                post.setPostDescription(rs.getString(6));
                post.setPostDate(rs.getString(7));
                post.setPostViews(rs.getInt(8));
                post.setPostLikes(rs.getInt(9));
                post.setPostComments(rs.getString(10));
                status = true;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeDbConnection(dbCon);
        }

        return status;
    }

    @Override
    public Post[] retrieveAllPost() {
        Post[] posts = null; //will store all users posts from the database in this array

        Connection dbCon = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            dbCon = getDbConnection();
            stmt = dbCon.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT * FROM user_post";

            rs = stmt.executeQuery(sql);

            int rowCounter = 0;
            while (rs.next()) {
                rowCounter++;
            }

            posts = new Post[rowCounter];

            int counter = 0;

            rs.beforeFirst();
            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt(1));
                post.setUserId(rs.getInt(2));
                post.setPostFile(rs.getString(3));
                post.setPostTitle(rs.getString(4));
                post.setPostThumbnail(rs.getString(5));
                post.setPostDescription(rs.getString(6));
                post.setPostDate(rs.getString(7));  //date*
                post.setPostViews(rs.getInt(8));
                post.setPostLikes(rs.getInt(9));
                post.setPostComments(rs.getString(10));

                posts[counter] = post;

                counter++;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeDbConnection(dbCon);
        }

        return posts;
    }
}
