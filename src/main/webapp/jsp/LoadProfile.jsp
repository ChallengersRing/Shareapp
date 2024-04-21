<%@ page import="in.shareapp.user.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%User user = (User) request.getAttribute("user");%>
<style type="text/css">   @import url("./css/loadprofile.css"); </style>
<html>
<head>
    <title>ShareApp</title>
</head>
<body>
<form enctype="multipart/form-data" class="profile-update" id="upd-form">
    <button class="edit-button" type="button" onclick="editProfile()">
        <i class='bx bxs-pencil bx-sm' ></i>
    </button>

    <%-- TODO: Add camera icon. --%>
    <div class="u-avatar">
        <input name="photo" type="file" id="input-photo" accept="image/*" placeholder="Photo" value=""
               onchange="document.querySelector('.u-avatar-img').src = window.URL.createObjectURL(this.files[0])"/>
        <label for="input-photo">
            <img class="u-avatar-img" src="./ClientResources/ProfilePics/<%=user.getAvatar()%>" alt=""/>
        </label>
    </div>

    <div class="u-field">
        <label class="profile-update-label" for="input-first-name">First name</label>
        <input name="fname" type="text" id="input-first-name" class="profile-update-input dis"
               placeholder="" value="<%=user.getFirstName()%>" disabled>
    </div>

    <div class="u-field">
        <label class="profile-update-label" for="input-last-name">Last name</label>
        <input name="lname" type="text" id="input-last-name" class="profile-update-input dis"
               placeholder="" value="<%=user.getLastName()%>" disabled>
    </div>


    <div class="u-field">
        <label class="profile-update-label" for="input-dob">Birthdate</label>
        <input name="dob" type="text" id="input-dob" class="profile-update-input dis"
               placeholder="" value="<%=user.getDateOfBirth()%>" disabled>
    </div>

    <div class="u-field">
        <label class="profile-update-label" for="input-gender">Gender</label>
        <input name="gender" type="text" id="input-gender" class="profile-update-input dis"
               placeholder="" value="<%=user.getGender()%>" disabled>
    </div>

    <div class="u-field">
        <label class="profile-update-label" for="input-email">Email address</label>
        <input name="email" type="email" id="input-email" class="profile-update-input"
               placeholder="" value="<%=user.getEmail()%>" disabled>
    </div>

    <div class="u-field">
        <label class="profile-update-label" for="input-phone">Phone</label>
        <input name="phone" type="text" id="input-phone" class="profile-update-input dis"
               placeholder="XXXXXXXXXX" value="<%=user.getPhone()%>" disabled>
    </div>

    <div class="u-field">
        <label class="profile-update-label" for="input-password">Password</label>
        <input name="password" type="password" id="input-password" class="profile-update-input dis"
               placeholder="Enter new Password" value="" disabled>
    </div>

    <div class="u-field">
        <button type="submit" class="u-btn-save dis" role="button" disabled>Update</button>
    </div>

    <%-- TODO: Don't update status whatever comes in response validate first. --%>
    <div class="u-field">
        <p id="updstatus"></p>
    </div>
</form>
</body>
</html>
