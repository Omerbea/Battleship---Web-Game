import com.sun.corba.se.spi.ior.ObjectKey;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "lobbyServlet" , urlPatterns = {"/lobby"})
public class lobbyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LobbyManager lobbyManager = (LobbyManager)getServletContext().getAttribute("lobbyManager");
        HttpSession session = req.getSession();

        try {
            lobbyManager.addPlayerToList((String)session.getAttribute("userName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/lobby.jsp").forward(req , resp);
    }

}
