package in.shareapp.post.service;

import in.shareapp.post.dao.PostDao;
import in.shareapp.post.dao.PostDaoImpl;
import in.shareapp.post.entity.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

public class PostServiceImpl implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    PostDao postDao = new PostDaoImpl();

    @Override
    public String getPostFileName(Part part) {
        return part.getSubmittedFileName().isEmpty() ? "PostNotReceived" : part.getSubmittedFileName();
    }

    @Override
    public boolean savePostFile(String serverFileDirectory, String postFileName, Part part) {
        try {
            part.write(serverFileDirectory + "\\" + postFileName);
            return true;
        } catch (IOException ioException) {
            logger.error("Fail to save post in FS", ioException);
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
    public List<Post> retrieveAllUsersPost() {
        return postDao.retrieveAllPost();
    }
}
