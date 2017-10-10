import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet (name = "gameRoomServlet" , urlPatterns = {"/gameRoom"})
public class gameRoomServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LobbyManager lobbyManager = (LobbyManager)getServletContext().getAttribute("lobbyManager");
        GameLobbyDetailes currentDetails = lobbyManager.getGameLobbyDetailsByName((String)req.getParameter("gameName"));
        GameManager currentManager = lobbyManager.getGameManagerByName((String)req.getParameter("gameName"));

        currentDetails.addPlayerEntered();
        if(currentDetails.amountOfPlayersInGame() > 2) {
            currentDetails.setActiveGame();
        }
        req.setAttribute("boardSize" , currentManager.getBoardSize());

        System.out.println("board size : " + req.getAttribute("boardSize"));
        req.getRequestDispatcher("/WEB-INF/gameRoom.jsp").include(req, resp);
    }

}
