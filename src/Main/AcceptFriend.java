package Main;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AcceptFriend extends HttpServlet
{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("User");
        try {
            user.AcceptFriend(req, resp, user.getUserID(), Integer.parseInt(req.getParameter("userID")));
        }catch (Exception e) {System.out.print(e);}
    }
}
