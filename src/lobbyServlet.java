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
        if (lobbyManager == null){
             lobbyManager = new LobbyManager();
            getServletContext().setAttribute("lobbyManager",lobbyManager);
        }
        /* ---- dummy content for debbugging -----
         */
        HttpSession session = req.getSession(false);

        try {
            lobbyManager.addPlayerToList((String)session.getAttribute("userName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        lobbyManager.getGames().put("game  test",  new GameLobbyDetailes("omer" , "omer the king" , 5 , "advanced" , null));
        lobbyManager.getGames().put("game  test1",  new GameLobbyDetailes("omer1" , "omer the king1" , 20 , "advanced1" , null));



        req.getRequestDispatcher("/WEB-INF/lobby.jsp").forward(req , resp);
    }

}
