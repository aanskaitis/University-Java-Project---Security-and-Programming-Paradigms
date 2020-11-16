import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/AddUserNumbers")
public class AddUserNumbers extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String numbers = "";
        int i;
        for (i=1;i<=6;i++){
            numbers = String.join(",",numbers, request.getParameter("number" + Integer.toString(i)));
        }
        numbers = numbers.substring(1);
        System.out.println(numbers);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}