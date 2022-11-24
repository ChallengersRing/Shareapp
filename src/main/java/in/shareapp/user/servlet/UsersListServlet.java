package in.shareapp.user.servlet;

import in.shareapp.user.dao.UserDao;
import in.shareapp.user.dao.UserDaoImpl;
import in.shareapp.user.entity.User;
import in.shareapp.user.service.UserService;
import in.shareapp.user.service.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersListServlet extends HttpServlet {
	UserService userService = new UserServiceImpl();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User[] users = userService.getAllUsersInformation();
    	req.setAttribute("USERSLIST", users);
		RequestDispatcher rd = req.getRequestDispatcher("./UsersList.jsp");
		rd.forward(req, resp);
	}
}