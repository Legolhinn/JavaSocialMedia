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
    int userID = -1;
    User userObj;

    protected void doPost( HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException
    {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        UserLogin(user, pass, request, response);
    }

    void UserLogin(String _user, String _pass, HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException
    {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }catch (Exception e){ System.out.print(e); }
        String url = "jdbc:mysql://127.0.0.1:3306/feysbuk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false";
        String user = "root";
        String password = "123123";
        String dispatcherURL = "";
        try
        {
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM users";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next())
            {
                if (result.getString(2).equals(_user))
                {
                    if (result.getString(4).equals(_pass))
                    {
                        userID = Integer.parseInt(result.getString(1));
                        userObj = new User(userID, _user, _pass,null,null);
                        dispatcherURL =  "/home.jsp";
                        HttpSession session = request.getSession(true);
                        session.setAttribute("User", userObj );
                    }
                }
                else
                {
                    msg = "Wrong username or password";
                    request.setAttribute("Message", msg);
                    dispatcherURL = "/index.jsp";
                }
            }
            connection.close();

        } catch (SQLException ex) { ex.printStackTrace(); }

        RequestDispatcher rd = getServletContext().getRequestDispatcher(dispatcherURL);
        rd.forward(request, response);
    }
}

