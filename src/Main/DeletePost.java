package Main;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeletePost extends HttpServlet
{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("User");
        try {
            user.DeletePost(req, resp, Integer.parseInt(req.getParameter("postID")));
        }catch (Exception e) {System.out.print(e);}
    }
}