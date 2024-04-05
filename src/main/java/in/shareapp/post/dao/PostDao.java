package in.shareapp.post.dao;

import in.shareapp.post.entity.Post;

import java.util.List;

public interface PostDao {
    boolean uploadPost(Post post);

    boolean retrievePost(Post post);

    List<Post> retrieveAllPost();
}
