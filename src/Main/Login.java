package Main;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class Login extends HttpServlet
{
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

        String filePath = "D:/Photos/Tom.jpg";

        try
        {
            Connection connection = DriverManager.getConnection(url, user, password);

            String sql = "SELECT * FROM users";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet result = statement.executeQuery();
            if (result.next())
            {
                System.out.println(_user + " = " + result.getString(2)+ " | "+_pass+" = "+result.getString(4));
                if (result.getString(2).equals(_user))
                {
                    if (result.getString(4).equals(_pass))
                    {
                        System.out.print("4n4n");
                        return "/home.jsp";
                    }
                }
            }
            connection.close();
        } catch (SQLException ex) { ex.printStackTrace(); }

        return null;
    }
}

