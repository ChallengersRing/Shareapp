package in.shareapp.post.dao;

import in.shareapp.post.entity.Post;

public interface PostDao {
    boolean uploadPost(Post post);
    boolean retrievePost(Post post);
    Post[] retrieveAllPost();
}
