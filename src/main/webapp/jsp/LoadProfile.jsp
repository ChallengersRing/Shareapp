<%@ page import="in.shareapp.user.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%User user = (User) session.getAttribute("USERDETAILS");%>
<style type="text/css">   @import url("./css/loadprofile.css"); </style>
<html>
<head>
    <title>ShareApp</title>
</head>
<body>
<form enctype="multipart/form-data" class="profile-update" id="upd-form">
    <img src="./ClientResources/ProfilePics/<%=user.getAvatar()%>" alt=""
         style="border-radius: 50%; height: 60px; width: 60px;">
    <p class="heading-small text-muted mb-4"><%=user.getFirstName()%>'s Profile</p>

    <div class="profile-update-group">
        <label class="profile-update-label" for="input-photo">Photo</label>
        <input name="photo" type="file" id="input-photo" class="profile-update-input dis"
               placeholder="Photo" value="" disabled>
    </div>

    <div class="profile-update-group">
        <label class="profile-update-label" for="input-first-name">First name</label>
        <input name="fname" type="text" id="input-first-name" class="profile-update-input dis"
               placeholder="" value="<%=user.getFirstName()%>" disabled>
    </div>

    <div class="profile-update-group">
        <label class="profile-update-label" for="input-last-name">Last name</label>
        <input name="lname" type="text" id="input-last-name" class="profile-update-input dis"
               placeholder="" value="<%=user.getLastName()%>" disabled>
    </div>

    <div class="profile-update-group">
        <label class="profile-update-label" for="input-dob">Birthdate</label>
        <input name="dob" type="text" id="input-dob" class="profile-update-input dis"
               placeholder="" value="<%=user.getDateOfBirth()%>" disabled>
    </div>

    <div class="profile-update-group">
        <label class="profile-update-label" for="input-gender">Gender</label>
        <input name="gender" type="text" id="input-gender" class="profile-update-input dis"
               placeholder="" value="<%=user.getGender()%>" disabled>
    </div>

    </div>
    <div class="profile-update-group">
        <label class="profile-update-label" for="input-email">Email address</label>
        <input name="email" type="email" id="input-email" class="profile-update-input"
               placeholder="" value="<%=user.getEmail()%>" disabled>
    </div>

    <div class="profile-update-group">
        <label class="profile-update-label" for="input-phone">Phone</label>
        <input name="phone" type="text" id="input-phone" class="profile-update-input dis"
               placeholder="XXXXXXXXXX" value="<%=user.getPhone()%>" disabled>
    </div>

    <div class="profile-update-group">
        <label class="profile-update-label" for="input-password">Password</label>
        <input name="password" type="password" id="input-password" class="profile-update-input dis"
               placeholder="Enter new Password" value="" disabled>
    </div>

    <div class="profile-update-group">
        <button type="button" onclick="editProfile()" class="profile-update-btn">Edit</button>
        <button type="submit" id="upd" class="profile-update-btn dis" role="button" disabled>Update</button>
    </div>
    <div class="profile-update-group">
        <p id="updstatus"></p>
    </div>
</form>
</body>
</html>

<%--action="./updateprofile" method="POST" --%>
<%-- onclick="updateProfile()"--%>