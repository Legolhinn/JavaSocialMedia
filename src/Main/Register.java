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
            registerUser(user,pass,email);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }catch (Exception e){System.out.print(e);}

    }

    void registerUser(String _user, String _pass, String _email) throws  Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://127.0.0.1:3306/feysbuk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false";
        String user = "root";
        String password = "123123";

        try
        {
            Connection connection = DriverManager.getConnection(url, user, password);
            String query = " insert into users (Username, Email, Password,ImgPath)" + " values (?, ?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString (1, _user);
            statement.setString (2, _email);
            statement.setString (3, _pass);
            statement.setString (4, "img/"+(int)(Math.random()*3)+".jpg");
            statement.executeUpdate();
            connection.close();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }
}

