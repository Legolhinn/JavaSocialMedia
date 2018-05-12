package Main;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class DeletePost extends HttpServlet
{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("User");
        try {
            if (user.getUserID() == 1)
                user.DeletePost(req, resp, Integer.parseInt(req.getParameter("postID")));
            else
            {
                PrintWriter out = resp.getWriter();
                resp.setContentType("text/html");
                out.println("<script type=\"text/javascript\">");
                out.println("alert('You do not have permission to do that');");
                out.println("</script>");
            }
        }catch (Exception e) {System.out.print(e);}
    }
}