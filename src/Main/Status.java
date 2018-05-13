package Main;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Status extends HttpServlet
{
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String status = req.getParameter("status");
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("User");
        try {
            user.UpdateStatus(req, resp, status, user.getUserID());
        }catch (Exception e) {System.out.print(e);}
    }
}