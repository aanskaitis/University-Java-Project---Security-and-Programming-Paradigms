import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@WebFilter(filterName = "InputFilter")
public class InputFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        boolean invalid = false;
        Map params = req.getParameterMap();
        if(params != null){
            Iterator iter = params.keySet().iterator();
            while(iter.hasNext()){
                String key = (String) iter.next();
                String[] values = (String[]) params.get(key);

                for(int i=0; i < values.length; i++){
                    if(checkChars(values[i])){
                        invalid = true;
                        break;
                    }
                }
                if (invalid) {break;}
            }
        }
        if(invalid){
            try{
                req.setAttribute("message", "error");
                req.getRequestDispatcher("/error.jsp").forward(req, resp);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else{
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public static boolean checkChars(String value) {
        boolean invalid = false;
        String[] badChars = { "<", ">", "script", "delete",
                "insert", "where", "input", "!", "{", "}",
                "into" };

        for(int i = 0; i < badChars.length; i++){
            if(value.indexOf(badChars[i]) >=0){
                invalid = true;
                break;
            }
        }
        return invalid;
    }

}
