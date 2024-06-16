//package in.shareapp.post.servlet;
//
//import in.shareapp.post.entity.Post;
//import in.shareapp.post.service.PostService;
//import in.shareapp.post.service.PostServiceImpl;
//import in.shareapp.user.entity.User;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import jakarta.servlet.http.Part;
//import java.io.IOException;
//
//public class PostRetrieveServlet {
//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServletException, IOException {
//        HttpSession session = req.getSession();
//
//        System.out.println("From PostUploadServlet:-----");
//
//        User user = (User) session.getAttribute("USERDETAILS");
//        if (user != null) {
//            int userId = user.getId();
//            String fillename = null;
//            String title = req.getParameter("title");
//            String thumbnail = req.getParameter("thumbnail");
//            String description = req.getParameter("description");
//            String date = getDateTime();
//            int views = 0;
//            int likes = 0;
//            String comments = "No Comments";
//
//
//            Part filePart = req.getPart("file");
//            PostService postService = new PostServiceImpl();
//
//            fillename = postService.getPostFileName(filePart);
//
//            System.out.println("Post Details: " + userId +
//                    " " + fillename + " " + title + " " +
//                    thumbnail + " " + " " + description + " " + date);
//
//            Post post = new Post(userId, fillename, title, thumbnail, description, date, views, likes, comments);
//
//            if (!(fillename.equals("PostNotReceived"))) {
//                //status after saving in database.
//                boolean status = postService.uploadUserPost(post);
//
//                //Setting the file directory where to store the posts.
//                String serverFileDirectory = getServletContext().getRealPath("/") + "posts";
//
//                if (status) {
//                    postService.savePostFile(serverFileDirectory, fillename, filePart);
//                    session.setAttribute("POSTUPLOADSTATUS", "success");
//                    System.out.println("Post upload success");
//                } else {
//                    //Database insert failure
//                    System.out.println("Database post failure");
//                    session.setAttribute("POSTUPLOADSTATUS", "failure");
//                }
//            } else {
//                //Post receiving from client failure
//                System.out.println("Post receiving failure");
//                session.setAttribute("POSTUPLOADSTATUS", "failure");
//            }
//        } else {
//            // User didn't login post upload failure
//            System.out.println("User didn't login post upload failure");
//            session.setAttribute("POSTUPLOADSTATUS", "failure");
//        }
//    }
//}
//}
