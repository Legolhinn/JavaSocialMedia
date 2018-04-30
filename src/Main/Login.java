package Main;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Login extends HttpServlet
{
    protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
    }

}
