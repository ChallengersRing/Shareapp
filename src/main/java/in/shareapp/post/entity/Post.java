package in.shareapp.post.entity;

import java.util.UUID;

public class Post {
    //PROPERTIES
    private Long id;
    private UUID extId;
    private Long userId;
    private String file;
    private String title;
    private String thumbnail;
    private String description;
    private String date;
    private int views;
    private int likes;
    private String comments;

    public Post() {
    }

    public Post(Long userId, String file, String title, String thumbnail, String description, String date) {
        this.userId = userId;
        this.file = file;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.date = date;
    }

    public Post(Long userId, String file, String title, String thumbnail, String description, String date, int views, int likes, String comments) {
        this.userId = userId;
        this.file = file;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.date = date;
        this.views = views;
        this.likes = likes;
        this.comments = comments;
    }

    public Post(Long id, Long userId, String file, String title, String thumbnail, String description, String date, int views, int likes, String comments) {
        this.id = id;
        this.userId = userId;
        this.file = file;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.date = date;
        this.views = views;
        this.likes = likes;
        this.comments = comments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFile() {
        return file;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getViews() {
        return views;
    }

    public int getLikes() {
        return likes;
    }

    public String getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", file='" + file + '\'' +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", views=" + views +
                ", likes=" + likes +
                ", comments='" + comments + '\'' +
                '}';
    }
}


