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
    <section class="p-dialog">
        <h1>Creator Studio</h1>

        <div class="p-mid">
            <div>
                <div class="p-title">
                    <label class="p-label" for="p-title">Title</label>
                    <textarea class="p-input" id="p-title" name="title" rows="1" cols="35" maxlength="100" type="text"
                              placeholder="Add a title that describes your video." value=""></textarea>
                </div>
                <div class="p-desc">
                    <label class="p-label" for="p-description">Description</label>
                    <textarea class="p-input" id="p-description" name="description" rows="4" cols="35" maxlength="500"
                              type="text" placeholder="Tell viewers about your video." value=""></textarea>
                </div>
            </div>
            <div class="p-vid">
                <p class="p-label">Preview</p>
                <label class="p-label custom-file-upload" for="post-video">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                         style="fill: rgba(0, 0, 0, 1);">
                        <path d="M11 15h2V9h3l-4-5-4 5h3z"></path>
                        <path d="M20 18H4v-7H2v7c0 1.103.897 2 2 2h16c1.103 0 2-.897 2-2v-7h-2v7z"></path>
                    </svg>
                    <video style="display: none" class="p-vid-preview" controls autoplay muted></video>
                </label>
                <input name="video" id="post-video"  type="file" placeholder="Choose File" value=""
                       onchange="document.querySelector('.p-vid-preview').src = window.URL.createObjectURL(this.files[0]);
                       document.querySelector('.p-vid-preview').style.display = 'block'"
                />
            </div>
        </div>

        <div class="p-thumbnail">
            <p class="p-label">Thumbnails</p>
            <label class="p-label custom-file-upload" for="post-thumbnail">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                     style="fill: rgba(0, 0, 0, 1);">
                    <path d="M11 15h2V9h3l-4-5-4 5h3z"></path>
                    <path d="M20 18H4v-7H2v7c0 1.103.897 2 2 2h16c1.103 0 2-.897 2-2v-7h-2v7z"></path>
                </svg>
                <img class="p-img-preview" alt=""/>
            </label>
            <input name="thumbnail" id="post-thumbnail" accept="image/*" type="file" placeholder="Thumbnail" value=""
                   onchange="document.querySelector('.p-img-preview').src = window.URL.createObjectURL(this.files[0])"/>
        </div>

        <div class="p-btn">
            <button type="submit" role="button" class="upload-btn">Upload</button>
        </div>
    </section>
</form>
</body>
</html>
