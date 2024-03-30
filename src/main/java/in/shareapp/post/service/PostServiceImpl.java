package in.shareapp.post.service;

import in.shareapp.post.dao.PostDao;
import in.shareapp.post.dao.PostDaoImpl;
import in.shareapp.post.entity.Post;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostServiceImpl implements PostService{
    private static final Logger logger = Logger.getLogger(PostServiceImpl.class.getName());

    PostDao postDao = new PostDaoImpl();

    @Override
    public String getPostFileName(Part part) {
        String postFileName = part.getSubmittedFileName().isEmpty() ? "PostNotReceived" : part.getSubmittedFileName();
        return postFileName;
    }

    @Override
    public boolean savePostFile(String serverFileDirectory, String postFileName, Part part) {
        try {
            part.write(serverFileDirectory + "\\" + postFileName);
            return true;
        } catch (IOException ioException) {
            logger.log(Level.SEVERE, "Fail to save post in FS", ioException);
            ioException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean uploadUserPost(Post post) {
        return postDao.uploadPost(post);
    }

    @Override
    public boolean retrieveUserPost(Post post) {
        return postDao.retrievePost(post);
    }

    @Override
    public Post[] retrieveAllUsersPost() {
        return postDao.retrieveAllPost();
    }
}
