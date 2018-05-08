package Main;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

public class Login extends HttpServlet
{
    String msg = "";

    protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        try
        {
            String url = checkUser(user,pass);
            if (url != null)
            {
                RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
                if (!msg.equals(""))
                {
                    request.setAttribute("Message",msg);
                }
                response.setContentType("text/html");
                HttpSession session = request.getSession(true);
                session.setAttribute("User", new User(user, pass));
                rd.forward(request, response);
            }
        }catch (Exception e){System.out.print(e);}
    }

    String checkUser(String _user, String _pass) throws  Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://127.0.0.1:3306/feysbuk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false";
        String user = "root";
        String password = "123123";

        try
        {
            Connection connection = DriverManager.getConnection(url, user, password);

            String sql = "SELECT * FROM users";
            PreparedStatement statement = connection.prepareStatement(sql);
            boolean tf = true;
            ResultSet result = statement.executeQuery();
            while (result.next())
            {
                System.out.println(result.getString(2) + " " + _user + " | " + result.getString(4) + " " + _pass);
                if (result.getString(2).equals(_user))
                {
                    if (result.getString(4).equals(_pass))
                    {
                        tf = true;
                    }
                }
                else
                {
                    tf = false;
                }
            }
            connection.close();
            if (tf)
                return "/home.jsp";
            else
            {
                msg = "Wrong username or password";
                return  "/index.jsp";
            }

        } catch (SQLException ex) { ex.printStackTrace(); }

        return null;
    }
}

