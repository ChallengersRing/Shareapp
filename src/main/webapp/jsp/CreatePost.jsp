<%@ page import="in.shareapp.user.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%User user = (User) session.getAttribute("USERDETAILS");%>
<html>
<head>
    <title>Shareapp</title>
</head>
<body>
<style type="text/css">   @import url("./css/createPost.css"); </style>

<form action="./postupload" method="POST" enctype="multipart/form-data">
    <div class="post-dialog">
        <div class="post-dialog-header">
            <img src="./ClientResources/ProfilePics/<%=user.getPhoto()%>" alt=""
                 style="border-radius: 50%; height: 60px; width: 60px;">
            <span><%=user.getFirstName() + " " + user.getLastName()%></span>
            <span>Visibility: Public</span>
        </div>
        <div class="post-dialog-body">
            <input name="title" id="post-title" type="text" placeholder="Title" value="">
            <input name="description" id="post-description" type="text" placeholder="Description" value="">
            <input name="thumbnail" id="post-thumbnail" type="file" placeholder="Thumbnail" value="">
            <input name="video" id="post-video" type="file" placeholder="Choose File">
        </div>
        <button type="submit" role="button" class="upload-btn">Post</button>
    </div>
</form>
</body>
</html>
