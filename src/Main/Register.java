package Main;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class Register extends HttpServlet
{
    protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        String email = request.getParameter("email");
        try
        {
            String url = registerUser(user,pass,email);
            if (url != null)
            {
                RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
                rd.forward(request, response);
            }
        }catch (Exception e){System.out.print(e);}

    }

    String registerUser(String _user, String _pass, String _email) throws  Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://127.0.0.1:3306/feysbuk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false";
        String user = "root";
        String password = "123123";

        String filePath = "D:/Photos/Tom.jpg";

        try
        {
            Connection connection = DriverManager.getConnection(url, user, password);
            String query = " insert into users (Username, Email, Password)"
                         + " values (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString (1, _user);
            statement.setString (2, _email);
            statement.setString (3, _pass);

            statement.executeUpdate();
            connection.close();
        } catch (SQLException ex) { ex.printStackTrace(); }

        return null;
    }
}

