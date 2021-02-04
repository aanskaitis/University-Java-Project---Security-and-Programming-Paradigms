import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.security.KeyPair;
import java.util.List;

@WebServlet("/GetUserNumbers")
public class GetUserNumbers extends HttpServlet {

    EncryptedStorage es = new EncryptedStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            HttpSession session = request.getSession();

            // using the same keyPair object
            es.pair = (KeyPair) session.getAttribute("pair");

            // use first 20 characters of hashed password as a file name
            String first20 = String.valueOf(session.getAttribute("password")).substring(0, 20);
            String filename = first20 + ".txt";

            // store decrypted draws
            List<String> stringNumbers = es.decryptData(es.bytesFileReader(filename));

            RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
            session.setAttribute("draws", stringNumbers);
            dispatcher.forward(request, response);

        } catch(Exception se) {
            se.printStackTrace();
            // display a message in account page if unsuccessful
            RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
            request.setAttribute("draws", null);
            request.setAttribute("message", "There are no draws");
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
