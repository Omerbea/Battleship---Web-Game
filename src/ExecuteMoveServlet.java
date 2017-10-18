import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "waitingRoomServlet" , urlPatterns = {"/waitingRoom"})

public class ExecuteMoveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LobbyManager lobbyManager = (LobbyManager) getServletContext().getAttribute("lobbyManager");
        GameManager gameManager = lobbyManager.getGameManagerByName((String)req.getParameter("gameName"));
        //TODO - start
        int row = 0;
        int column = 0;
        int player =0;
        //TODO - end
        gameManager.executeMove(row,column);
        char [][] playerBoard = gameManager.getBoardByPlayerNumber(player);
        char [][] rivalBoard = gameManager.getRivalBoardByPlayerNumber(player);
    }

}

