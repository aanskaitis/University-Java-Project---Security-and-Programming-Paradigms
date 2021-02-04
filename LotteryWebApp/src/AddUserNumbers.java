import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/AddUserNumbers")
public class AddUserNumbers extends HttpServlet {

    EncryptedStorage es = new EncryptedStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String numbers = "";
            int i;
            for (i=1;i<=6;i++){
                numbers = String.join(",",numbers, request.getParameter("number" + i));
            }

            numbers = numbers.substring(1); // removes the first comma
            HttpSession session = request.getSession();

            // First 20 characters of hashed password taken as file name
            String first20 = String.valueOf(session.getAttribute("password")).substring(0, 20);
            String filename = first20 + ".txt";

            // encrypting draw and create text file with encrypted draws
            es.bytesFileWriter(filename, es.encryptData(numbers));
            session.setAttribute("pair", es.pair);

            // display message in account page if draws were added successfully
            RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
            request.setAttribute("message", "Draws added successfully.");
            dispatcher.forward(request, response);

        } catch(Exception se) {
            se.printStackTrace();
            // display error.jsp page with given message if unsuccessful
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("message", "Error");
            dispatcher.forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
