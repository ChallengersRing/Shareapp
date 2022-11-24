package in.shareapp.post.entity;

public class Post {
    //PROPERTIES
    private int postId;
    private int userId;
    private String postFile;
    private String postTitle;
    private String postThumbnail;
    private String postDescription;
    private String postDate;
    private int postViews;
    private int postLikes;
    private String postComments;

    public Post() {
    }

    public Post(int userId, String postFile, String postTitle, String postThumbnail, String postDescription, String postDate) {
        this.userId = userId;
        this.postFile = postFile;
        this.postTitle = postTitle;
        this.postThumbnail = postThumbnail;
        this.postDescription = postDescription;
        this.postDate = postDate;
    }

    public Post(int userId, String postFile, String postTitle, String postThumbnail, String postDescription, String postDate, int postViews, int postLikes, String postComments) {
        this.userId = userId;
        this.postFile = postFile;
        this.postTitle = postTitle;
        this.postThumbnail = postThumbnail;
        this.postDescription = postDescription;
        this.postDate = postDate;
        this.postViews = postViews;
        this.postLikes = postLikes;
        this.postComments = postComments;
    }

    public Post(int postId, int userId, String postFile, String postTitle, String postThumbnail, String postDescription, String postDate, int postViews, int postLikes, String postComments) {
        this.postId = postId;
        this.userId = userId;
        this.postFile = postFile;
        this.postTitle = postTitle;
        this.postThumbnail = postThumbnail;
        this.postDescription = postDescription;
        this.postDate = postDate;
        this.postViews = postViews;
        this.postLikes = postLikes;
        this.postComments = postComments;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPostFile(String postFile) {
        this.postFile = postFile;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostThumbnail(String postThumbnail) {
        this.postThumbnail = postThumbnail;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public void setPostViews(int postViews) {
        this.postViews = postViews;
    }

    public void setPostLikes(int postLikes) {
        this.postLikes = postLikes;
    }

    public void setPostComments(String postComments) {
        this.postComments = postComments;
    }


    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public String getPostFile() {
        return postFile;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostThumbnail() {
        return postThumbnail;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public String getPostDate() {
        return postDate;
    }

    public int getPostViews() {
        return postViews;
    }

    public int getPostLikes() {
        return postLikes;
    }

    public String getPostComments() {
        return postComments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", postFile='" + postFile + '\'' +
                ", postTitle='" + postTitle + '\'' +
                ", postThumbnail='" + postThumbnail + '\'' +
                ", postDescription='" + postDescription + '\'' +
                ", postDate='" + postDate + '\'' +
                ", postViews=" + postViews +
                ", postLikes=" + postLikes +
                ", postComments='" + postComments + '\'' +
                '}';
    }
}


