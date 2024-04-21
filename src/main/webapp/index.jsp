<%@ page import="in.shareapp.user.entity.User" %>
<%@ page import="in.shareapp.security.jwt.JwtUtil" %>
<%@ page import="java.util.Optional" %>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="icon" type="image/x-icon" href="./images/icons/favicon.png">
    <!-- CSS File -->
    <link rel="stylesheet" href="./css/index.css"/>
    <link rel="stylesheet" href="./css/loginDetailModal.css">
    <title>ShareApp</title>
</head>
<body>
<%-- NavBar --%>
<header class="header">
    <!-- Sidebar toggle btn && home/App logo -->
    <div class="hd-left">
        <button onclick="toggleNav()">
            <i class='bx bx-menu-alt-left bx-sm'></i>
        </button>
        <a href="/">ShareApp</a>
    </div>

    <!-- Search bar -->
    <div class="hd-center">
        <form action="/search" method="post">
            <input type="text" placeholder="Search"/>
            <img onclick="searchApp()" src="./images/icons/search.svg" alt="C"
                 class="icon-cst">
            <img onclick="mic()" src="./images/icons/mic.svg" alt="C"
                 class="icon-cst">
        </form>
    </div>

    <!-- Right section (user/signin/signup/signout) -->
    <div class="hd-right">
        <button class="theme-change" type="button" onclick="toggleTheme()">
            <i class='bx bx-sun bx-sm'></i>
        </button>

        <%
            Optional<String> token = JwtUtil.extractToken(request);
            Optional<User> userOptional = token.isPresent() ? JwtUtil.getInstance().resolveClaims(token.get()) : Optional.empty();
            System.out.println("Token: "+token);
            System.out.println(userOptional);
            boolean isAuthorized = false;
            if (userOptional.isPresent()) {
                isAuthorized = true;
                User user = userOptional.get();
        %>
        <img src="./ClientResources/ProfilePics/<%=user.getAvatar()%>" onclick="signinDetails()" alt=""/>
        <!-- The login detail Modal (css in Modal.css)-->
        <div id="loginDetailModal" class="logindetail-container">
            <span class="logindetail-close">CLOSE</span>
            <!-- Modal content -->
            <div class="card">
                <img src="./ClientResources/ProfilePics/<%=user.getAvatar()%>" alt="USER" style="width:100%">
                <p><%=user.getFirstName() + " " + user.getLastName()%>
                </p>
                <a href="javascript:loadProfile()">
                    <button>View Profile</button>
                </a>
                <a href="javascript:signOut()">
                    <button>Sign Out</button>
                </a>
            </div>
        </div>
        <%
        } else {
        %>
        <a onclick="loadSignin()">Sign In</a>
        <%
            }
        %>
    </div>
</header>

<%--TODO: replace with toaster.--%>
<%
    if (request.getAttribute("PostUpload") != null) {
        String color = "green";
        if (request.getAttribute("PostUpload").equals("fail")) {
            color = "red";
        }
%>
<div style="background: black; display: flex; justify-content: center">
    <p style="color: <%=color%>">
        Post upload: <%= request.getAttribute("PostUpload") %>
        <button onclick="goHome()" style="padding: 5px 15px 5px 15px;">OK</button>
    </p>
</div>
<%
    }
%>

<div class="modal-container">
    <div class="modal">
        <div class="modal-close">
            <span class="modal-close-icon">&times;</span>
        </div>
        <div class="modal-title"></div>
        <div class="modal-content"></div>
    </div>
</div>

<%-- The Body --%>
<main>
    <!--Left Side Menu Panel-->
    <div class="side-bar">
        <div class="nav">
            <a class="nav-link active" onclick="loadVideos()">
                <img src="./images/icons/videos.svg" alt="C" class="icon-cst">
                <span>Videos</span>
            </a>
            <a class="nav-link" onclick="loadAudios()">
                <img src="./images/icons/audio.svg" alt="C" class="icon-cst">
                <span>Audio</span>
            </a>
            <a class="nav-link" onclick="loadBlogs()">
                <img src="./images/icons/blog.png" alt="C" class="icon-cst">
                <span>Blogs</span>
            </a>
            <a class="nav-link" onclick="loadHistory()">
                <img src="./images/icons/history.svg" alt="C" class="icon-cst">
                <span>History</span>
            </a>
            <hr/>
            <%
                if (isAuthorized) {
            %>
            <a class="nav-link" onclick="loadMyPosts()">
                <img src="./images/icons/posts.svg" alt="C" class="icon-cst">
                <span>My Posts</span>
            </a>
            <a class="nav-link" onclick="loadLikedItems()">
                <img src="./images/icons/like.svg" alt="C" class="icon-cst">
                <span>Liked items</span>
            </a>
            <a class="nav-link" onclick="openPostCreatorWindow()">
                <img src="./images/icons/upload.svg" alt="C" class="icon-cst">
                <span>Upload</span>
            </a>
            <hr/>
            <%
                }
            %>
        </div>
    </div>

    <!--Content start-->
    <div class="videos">
        <h1>Recommended </h1>
        <div class="videos-container">
            <%-- single video container--%>
            <div class="video">
                <div class="video-thumbnail">
                    <img src="./ClientResources/Posts/Charminar.png" alt="thumbnail"/>
                </div>

                <div class="video-details">
                    <div class="author">
                        <img src="./ClientResources/ProfilePics/Suliman.jpg" alt="author"/>
                    </div>

                    <div class="video-title">
                        <h3>Travel hyderabad!!</h3>
                        <a href="">Travel</a>
                        <span>15M Views . 2 Months Ago</span>
                    </div>
                </div>
            </div>
            <%-- single video container--%>

            <%-- single video container--%>
            <div class="video">
                <div class="video-thumbnail">
                    <img src="./ClientResources/Posts/cooking.jpg" alt="thumbnail"/>
                </div>

                <div class="video-details">
                    <div class="author">
                        <img src="./ClientResources/ProfilePics/Chloe.jpg" alt="author"/>
                    </div>

                    <div class="video-title">
                        <h3>How To COOK?</h3>
                        <a href="">Cooking</a>
                        <span>10M Views . 3 Months Ago</span>
                    </div>
                </div>
            </div>
            <%-- single video container--%>
        </div>
    </div>
    <!--Content End-->
</main>

<!-- Scripts -->
<script src="./js/custom.js"></script>

<link href='' rel='stylesheet'>
<style type="text/css">
    @import url("./css/authform.css");
    @import url("./css/toaster.css");
    @import url("./css/createPost.css");
    @import url("./css/Modal.css");
    @import url("https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css");
</style>
</body>
</html>