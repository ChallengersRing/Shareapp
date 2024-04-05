package in.shareapp.post.dao;

import in.shareapp.dds.DatabaseDataSource;
import in.shareapp.post.entity.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PostDaoImpl extends DatabaseDataSource implements PostDao {
    private static final Logger logger = Logger.getLogger(PostDaoImpl.class.getName());

    @Override
    public boolean uploadPost(Post post) {
        boolean status = false;
        final String sql = "INSERT INTO shareapp.user_post(user_id, file, title, thumbnail, description, views, likes, comments) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection dbCon = getDbConnection();
             PreparedStatement pstmt = dbCon.prepareStatement(sql)) {

            pstmt.setLong(1, post.getUserId());
            pstmt.setString(2, post.getFile());
            pstmt.setString(3, post.getTitle());
            pstmt.setString(4, post.getThumbnail());
            pstmt.setString(5, post.getDescription());
            pstmt.setInt(6, post.getViews());
            pstmt.setInt(7, post.getLikes());
            pstmt.setString(8, post.getComments());

            int row = pstmt.executeUpdate();
            if (row != 0) {
                status = true;
            }
        } catch (SQLException sqlEx) {
            logger.warning("Query execution failed: " + sqlEx.getMessage());
        }

        return status;
    }

    @Override
    public boolean retrievePost(Post post) {
        boolean status = false;
        final String sql = "SELECT * FROM shareapp.user_post WHERE user_id = ?";

        try (Connection dbCon = getDbConnection();
             PreparedStatement pstmt = dbCon.prepareStatement(sql)) {

            pstmt.setLong(1, post.getUserId());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    post.setId(rs.getLong("id"));
                    post.setFile(rs.getString("file"));
                    post.setTitle(rs.getString("title"));
                    post.setThumbnail(rs.getString("thumbnail"));
                    post.setDescription(rs.getString("description"));
                    post.setDate(rs.getString("created_at"));
                    post.setViews(rs.getInt("views"));
                    post.setLikes(rs.getInt("likes"));
                    post.setComments(rs.getString("comments"));
                    status = true;
                }
            }
        } catch (SQLException sqlEx) {
            logger.warning("Query execution failed: " + sqlEx.getMessage());
        }

        return status;
    }

    @Override
    public List<Post> retrieveAllPost() {
        List<Post> posts = new ArrayList<>();
        final String sql = "SELECT * FROM shareapp.user_post";

        try (Connection dbCon = getDbConnection();
             PreparedStatement pstmt = dbCon.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setUserId(rs.getLong("user_id"));
                post.setFile(rs.getString("file"));
                post.setTitle(rs.getString("title"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setDescription(rs.getString("description"));
                post.setDate(rs.getString("created_at"));
                post.setViews(rs.getInt("views"));
                post.setLikes(rs.getInt("likes"));
                post.setComments(rs.getString("comments"));

                posts.add(post);
            }
        } catch (SQLException sqlEx) {
            logger.warning("Query execution failed: " + sqlEx.getMessage());
        }

        return posts;
    }

}
