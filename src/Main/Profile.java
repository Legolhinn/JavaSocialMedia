package Main;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Profile extends HttpServlet
{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        HttpSession session = req.getSession();
        try {
            session.setAttribute("userID", Integer.parseInt(req.getParameter("userID")));
            RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/profile.jsp");
            rd.forward(req, resp);
        }catch (Exception e) {System.out.print(e);}
    }
}