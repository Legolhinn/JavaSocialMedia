package Main;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post extends HttpServlet
{

    HttpSession session;
    protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String postContent = request.getParameter("content");
        session = request.getSession();
        User user = (User)session.getAttribute("User");
        try
        {
            doPosting(postContent, user, request);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/home.jsp");
            rd.forward(request, response);
        }catch (Exception e){System.out.print(e);}

    }

    void doPosting(String _post, User _user, HttpServletRequest request) throws  Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://127.0.0.1:3306/feysbuk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false";
        String user = "root";
        String password = "123123";

        try
        {
            Connection connection = DriverManager.getConnection(url, user, password);
            String query = " insert into posttable (PostContent, UserID, DateAndTime)" + " values (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString (1, _post);
            statement.setInt (2, _user.getUserID());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            statement.setString(3, dtf.format(now));
            statement.executeUpdate();
            _user.showPosts(request);
            connection.close();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
