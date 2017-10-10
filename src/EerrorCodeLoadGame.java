import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EerrorCodeLoadGame" , urlPatterns = "/errorCodeLoadGame")
public class EerrorCodeLoadGame extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        if (session.isNew()){
            System.out.println("the user new in load game. need to log out the user");
            //TODO: log out the user
        }
        String errMsg = (String) session.getAttribute("errorLoadfile");
        if (errMsg != null && errMsg !=""){
            PrintWriter writer = resp.getWriter();
            // start building json
            Gson gson = new GsonBuilder().create();
            gson.toJson(errMsg , writer);
        }

    }



}
