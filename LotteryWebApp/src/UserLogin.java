import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Enumeration;


@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {

    public Integer attempts = 0;
    private Connection conn;
    private Statement stmt;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "user";
        String PASS = "password";

        // URLs to connect to database depending on your development approach
        // (NOTE: please change to option 1 when submitting)

        // 1. use this when running everything in Docker using docker-compose
        String DB_URL = "jdbc:mysql://db:3306/lottery";

        // 2. use this when running tomcat server locally on your machine and mysql database server in Docker
        //String DB_URL = "jdbc:mysql://localhost:33333/lottery";

        // 3. use this when running tomcat and mysql database servers on your machine
        //String DB_URL = "jdbc:mysql://localhost:3306/lottery";


        try {
            Enumeration<String> attributes = request.getSession().getAttributeNames();
            while (attributes.hasMoreElements()){
                String attribute = attributes.nextElement();
                if (!attribute.equals("pair")){
                    request.getSession().removeAttribute(attribute);
                }
            }

            // create database connection and statement
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String user = request.getParameter("username");
            String pwd = request.getParameter("password");
            String role = request.getParameter("role");

            // query database and get results
            ResultSet rs = stmt.executeQuery("SELECT * FROM userAccounts WHERE (Username='" + user +
                    "' AND Pwd='" + HashPassword(pwd) + "' AND Userrole='" + role + "')");
            if (rs.next()){
                request.getSession().setAttribute("username", rs.getString(rs.findColumn("Username")));
                request.getSession().setAttribute("firstname", rs.getString(rs.findColumn("Firstname")));
                request.getSession().setAttribute("lastname", rs.getString(rs.findColumn("Lastname")));
                request.getSession().setAttribute("password", rs.getString(rs.findColumn("Pwd")));
                request.getSession().setAttribute("email", rs.getString(rs.findColumn("Email")));
                request.getSession().setAttribute("phone", rs.getString(rs.findColumn("Phone")));
                request.getSession().setAttribute("role", rs.getString(rs.findColumn("Userrole")));

                // display page depending on account role
                if (role.equals("admin")){
                    RequestDispatcher dispatcher = request.getRequestDispatcher("admin/admin_home.jsp");
                    request.getSession().setAttribute("login-role", "admin");
                    dispatcher.forward(request, response);
                } else {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
                    request.getSession().setAttribute("login-role", "user");
                    request.setAttribute("message",    "You have successfully logged into your account");
                    dispatcher.forward(request, response);
                }
            } else {
                attempts++;
                request.getSession().setAttribute("attempts", attempts);
                throw new Exception();
            }
            conn.close();

        } catch (Exception se) {
            se.printStackTrace();
            // display error.jsp page with given message if unsuccessful
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("message", "Login unsuccessful, you have " + (3 - attempts) + " tries left.");
            dispatcher.forward(request, response);
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private static String HashPassword(String given_password){

        String generatedPassword = null;
        try {
            // using md5 algorithm
            MessageDigest md = MessageDigest.getInstance("MD5");

            // converting password to bytes
            md.update(given_password.getBytes());
            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
