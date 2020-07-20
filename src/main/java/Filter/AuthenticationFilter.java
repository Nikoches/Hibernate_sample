package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig)  {
        this.context = fConfig.getServletContext();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        Cookie[] cookies = req.getCookies();
        this.context.log("Requested Resource::" + uri);
        boolean auth = false;
        if (cookies != null) {
            for (Cookie x : cookies) {
                if (x.getName().equals("user")) {
                    auth = true;
                    break;
                }
            }
        }
        if (auth || uri.endsWith("login.html") || uri.endsWith("Login")) {
            chain.doFilter(request, response);
        } else {
            this.context.log("Unauthorized access request");
            req.getRequestDispatcher("Views/login.html").forward(req, res);
        }
    }

    public void destroy() {
        //close any resources here
    }

}