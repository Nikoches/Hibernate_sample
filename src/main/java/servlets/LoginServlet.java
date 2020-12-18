package servlets;

import logic.Logic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(urlPatterns = "/Login")
public class LoginServlet extends HttpServlet {
    private final Logic logic = Logic.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      resp.sendRedirect("Views/login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean role = logic.isAuthorized(req.getParameter("user"), req.getParameter("pwd"));
        this.getServletContext().log("Login Servlet == " + role);
        if (role) {
            Cookie userName = new Cookie("user", "login");
            userName.setMaxAge(30 * 60);
            resp.addCookie(userName);
            resp.sendRedirect("main");
        } else {
            req.setAttribute("login", "false");
            req.getRequestDispatcher("Views/login.html").forward(req, resp);
        }
    }
}
