package in.shareapp.post.service;

import in.shareapp.post.entity.Post;

import javax.servlet.http.Part;

public interface PostService {
    public String getPostFileName(Part part);
    public boolean savePostFile(String serverFileDirectory, String postFileName, Part part);
    boolean uploadUserPost(Post post);
    boolean retrieveUserPost(Post post);
    Post[] retrieveAllUsersPost();
}
