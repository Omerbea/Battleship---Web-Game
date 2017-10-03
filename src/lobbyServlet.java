import com.sun.corba.se.spi.ior.ObjectKey;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "lobbyServlet" , urlPatterns = {"/lobby"})
public class lobbyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Object obj = getServletContext().getAttribute("lobbyManager");
        if (obj == null){
            LobbyManager lobbyManager = new LobbyManager();
            getServletContext().setAttribute("lobbyManager",lobbyManager);
        }
        req.getRequestDispatcher("/WEB-INF/lobby.jsp").forward(req , resp);
    }

}
