import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "waitingRoomServlet" , urlPatterns = {"/waitingRoom"})
public class waitingRoomServlet extends HttpServlet {
    private int playerNumber = 1 ;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("wattingRoom" + playerNumber);
        boolean isFirstTime = false ;

        LobbyManager lobbyManager = (LobbyManager)getServletContext().getAttribute("lobbyManager");
        GameLobbyDetailes currentDetails = lobbyManager.getGameLobbyDetailsByName((String)req.getParameter("gameName"));
        String gameName = (String)req.getParameter("gameName");
        GameManager currentManager = lobbyManager.getGameManagerByName(gameName);
        req.setAttribute("gameName" , (String)req.getParameter("gameName") );
        HttpSession session = req.getSession(false);
        if (session == null){
            //TODO: handle witth error . go to log-in page
        }
        session.setAttribute("gameName", gameName);
        if(session.getAttribute("playerNumber") == null){
            System.out.println(playerNumber);
            session.setAttribute("playerNumber" , playerNumber++);

            currentDetails.addPlayerEntered();
            isFirstTime = true ;

        }
        System.out.println(session.getAttribute("playerNumber") + " - in watting room");

        if(currentDetails.amountOfPlayersInGame() <= 1) {
            if(isFirstTime) {
                req.getRequestDispatcher("/WEB-INF/waitingRoom.jsp").include(req, resp);
            }
        } else {
            int boardSize = currentManager.getBoardSize();
            req.setAttribute("boardSize", boardSize);
            System.out.println("Player" + session.getAttribute("playerNumber") + " is in game");

            if((int)session.getAttribute("playerNumber") == 1) {
                req.getRequestDispatcher("/WEB-INF/gameRoomPlayerOne.jsp").include(req, resp);
            } else {
                req.getRequestDispatcher("/WEB-INF/gameRoomPlayerTwo.jsp").include(req, resp);
            }
        }
/*

        if(currentDetails.amountOfPlayersInGame() > 2) {
            currentDetails.setActiveGame();
        }
        req.setAttribute("boardSize" , currentManager.getBoardSize());

        System.out.println("board size : " + req.getAttribute("boardSize"));
        req.getRequestDispatcher("/WEB-INF/waitingRoom.jsp").include(req, resp);*/
    }

}