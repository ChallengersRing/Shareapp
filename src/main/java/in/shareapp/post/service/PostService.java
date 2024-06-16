package in.shareapp.post.service;

import in.shareapp.post.entity.Post;

import jakarta.servlet.http.Part;
import java.util.List;

public interface PostService {
    String getPostFileName(Part part);

    boolean savePostFile(String serverFileDirectory, String postFileName, Part part);

    boolean uploadUserPost(Post post);

    boolean retrieveUserPost(Post post);

    List<Post> retrieveAllUsersPost();
}
