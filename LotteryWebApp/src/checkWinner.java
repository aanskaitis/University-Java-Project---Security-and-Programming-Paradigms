import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@WebServlet("/checkWinner")
public class checkWinner extends HttpServlet {

    Connection conn;
    Statement stmt;
    ResultSet rs;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://db:3306/lottery";
        String USER = "root";
        String PASS = "sesame";

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT Draw FROM winningDraw");

            HttpSession session = request.getSession();

            // retrieve and store draws
            List<String> draws = (List<String>) session.getAttribute("draws");

            // continue if retrieved a winning draw from the database
            if (rs.next()){
                Boolean anyWinner = false;

                for (String draw : draws){

                    if (draw.equals(rs.getString(1))){
                        // if there is a winning draw, delete draws and display message in account page
                        anyWinner = true;

                        // delete files containing encrypted draws
                        File dir = new File("EncryptedFiles");
                        if(dir.exists()) {
                            for (File file : dir.listFiles()){
                                if (!file.isDirectory()){
                                    file.delete();
                                }
                            }
                        }
                        // display the winning message and remove draws
                        request.setAttribute("message", "Draw: " + draw + " is a match! Congratulations!");
                        session.setAttribute("draws", null);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
                        dispatcher.forward(request, response);
                    }
                }
                // if no winning draws are found, display message in account page
                if (!anyWinner){
                    request.setAttribute("message", "There is no winning draw...");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
                    dispatcher.forward(request, response);
                }
            }

            // close the connection
            conn.close();

        } catch (Exception e){
            e.printStackTrace();
            request.setAttribute("message", "Couldn't retrieve information about the winner.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
            dispatcher.forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
