<%@ page import="in.shareapp.post.entity.Post" %>
<%@ page import="in.shareapp.user.service.UserService" %>
<%@ page import="in.shareapp.user.service.UserServiceImpl" %>
<%@ page import="in.shareapp.user.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="in.shareapp.Shareapp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<%Logger logger = LoggerFactory.getLogger(Shareapp.class);%>
<html>
<head>
    <title>ShareApp</title>
</head>
<body>
<h1>Recommended</h1>
<div class="videos-container">
    <%
        logger.info("From PostListAllUsers.jsp");
        List<Post> posts = (List<Post>) request.getAttribute("POSTLISTALLUSERS");
        logger.info("POST LIST ALL USERS at PostListAllUsers.jsp: {}", posts.size());

        UserService userService = new UserServiceImpl();

        for (int i = 0; i < posts.size(); i++) {
            User user = userService.getUserInformationById(posts.get(i).getUserId());
    %>
            <%-- single video container--%>
            <div class="video">
                <div class="video-thumbnail">
                    <img src="./ClientResources/Posts/<%=posts.get(i).getThumbnail()%>" alt="thumbnail"/>
                </div>

                <div class="video-details">
                    <div class="author">
                        <img src="./ClientResources/ProfilePics/<%=user.getAvatar()%>" alt="author"/>
                    </div>

                    <div class="video-title">
                        <h3> <%= posts.get(i).getTitle() %> </h3>
                        <a href=""> <%= user.getFirstName() + " " + user.getLastName() %> </a>
                        <span>views: <%= posts.get(i).getViews() %> &#8226; <%= posts.get(i).getDate() %> </span>
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
