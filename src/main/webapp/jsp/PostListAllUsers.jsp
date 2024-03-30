<%@ page import="in.shareapp.post.entity.Post" %>
<%@ page import="in.shareapp.user.service.UserService" %>
<%@ page import="in.shareapp.user.service.UserServiceImpl" %>
<%@ page import="in.shareapp.user.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ShareApp</title>
</head>
<body>
<h1>Recommended</h1>
<div class="videos-container">
    <%
        System.out.println("From PostListAllUsers.jsp");
        Post[] posts = (Post[]) request.getAttribute("POSTLISTALLUSERS");
        System.out.println("POST LIST ALL USERS at PostListAllUsers.jsp: "+posts.length);

        UserService userService = new UserServiceImpl();

        for (int i = 0; i < posts.length; i++) {
            User user = userService.getUserInformationById(posts[i].getUserId());
    %>
            <%-- single video container--%>
            <div class="video">
                <div class="video-thumbnail">
                    <img src="./ClientResources/Posts/<%=posts[i].getPostThumbnail()%>" alt="thumbnail"/>
                </div>

                <div class="video-details">
                    <div class="author">
                        <img src="./ClientResources/ProfilePics/<%=user.getAvatar()%>" alt="author"/>
                    </div>

                    <div class="video-title">
                        <h3> <%= posts[i].getPostTitle() %> </h3>
                        <a href=""> <%= user.getFirstName() + " " + user.getLastName() %> </a>
                        <span>views: <%= posts[i].getPostViews() %> &#8226; <%= posts[i].getPostDate() %> </span>
                    </div>
                </div>
            </div>
            <%-- single video container--%>
    <%
        }
    %>
</div>
</body>
</html>
