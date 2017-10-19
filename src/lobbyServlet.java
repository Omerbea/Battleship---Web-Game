import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.corba.se.spi.ior.ObjectKey;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "lobbyServlet" , urlPatterns = {"/lobby"})
public class lobbyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LobbyManager lobbyManager = (LobbyManager)getServletContext().getAttribute("lobbyManager");
        HttpSession session = req.getSession(false);
        if (session == null){
            req.getRequestDispatcher("/WEB-INF/logIn.jsp").forward(req , resp);
            return;
        }
        String userName = (String)session.getAttribute("userName");
        if (userName == null){
            req.getRequestDispatcher("/WEB-INF/logIn.jsp").forward(req , resp);
            return;
        }

        String status = (String) session.getAttribute("isFirstTime");
        if (status != null && status =="no"){

        }
        else{
            try {
                lobbyManager.addPlayerToList((String)session.getAttribute("userName"));
            } catch (Exception e) {
                session.invalidate();
                resp.setContentType("application/json");
                PrintWriter writer = resp.getWriter();
                // start building json
                Gson gson = new GsonBuilder().create();
                String errMsg = e.getMessage();
                gson.toJson("errMsg:"+errMsg , writer);
                req.getRequestDispatcher("/WEB-INF/logIn.jsp").forward(req , resp);
                return;
            }
            session.setAttribute("isFirstTime", "no");
        }

        req.getRequestDispatcher("/WEB-INF/lobby.jsp").forward(req , resp);
    }

}
