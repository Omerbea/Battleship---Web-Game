import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "waitingRoomServlet" , urlPatterns = {"/waitingRoom"})
public class waitingRoomServlet extends HttpServlet {
    //private int playerNumber = 1 ;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("in wattingRoom");
        boolean isFirstTime = false ;

        LobbyManager lobbyManager = (LobbyManager)getServletContext().getAttribute("lobbyManager");
        GameLobbyDetailes currentDetails = lobbyManager.getGameLobbyDetailsByName((String)req.getParameter("gameName"));
        if (currentDetails.isActiveGame == "yes"){
            System.out.println("warnign : active game already");
            //req.getRequestDispatcher("/jsp/lobby.jsp").forward(req , resp);
            //return;
        }
        String gameName = (String)req.getParameter("gameName");
        GameManager currentManager = lobbyManager.getGameManagerByName(gameName);
        req.setAttribute("gameName" , (String)req.getParameter("gameName") );
        HttpSession session = req.getSession(false);
        if (session == null){
            //TODO: handle witth error . go to log-in page
        }
        session.setAttribute("gameName", gameName);
        if(session.getAttribute("playerNumber") == null){
            if (currentDetails.amountOfPlayersInGame() > 1){
                req.getRequestDispatcher("/jsp/lobby.jsp").forward(req , resp);
                return;
            }
            int playerNumber = 1 + currentDetails.amountOfPlayersInGame() ;
            System.out.println(playerNumber);
            session.setAttribute("playerNumber" , playerNumber);
            String playerName = (String)session.getAttribute("userName");
            currentDetails.addPlayerEntered(playerName);
            isFirstTime = true ;

        }
        System.out.println(session.getAttribute("playerNumber") + " - in watting room");

        if(currentDetails.amountOfPlayersInGame() <= 1) {
            if(isFirstTime) {
                req.getRequestDispatcher("/WEB-INF/waitingRoom.jsp").include(req, resp);
            }
            else{
                System.out.println("warring!!");
            }
        } else {
            int boardSize = currentManager.getBoardSize();
            req.setAttribute("boardSize", boardSize);
            System.out.println("Player" + session.getAttribute("playerNumber") + " is in game");
            if((int)session.getAttribute("playerNumber") == 1) {
                System.out.println("getRequestDispatcher player 1");
                try {
                    currentManager.gameStart();
                }
                catch (Exception e){
                    System.out.println("Expetion: "  + e.getMessage());
                }
                resp.sendRedirect(req.getContextPath() +"/jsp/gameRoomPlayerOne.jsp?boardSize=" + boardSize);
            } else {
                lobbyManager.setGameIsActive(gameName);
                System.out.println("getRequestDispatcher player 2");
                resp.sendRedirect(req.getContextPath()+ "/jsp/gameRoomPlayerTwo.jsp?boardSize=" +boardSize);

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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LobbyManager lobbyManager = (LobbyManager)getServletContext().getAttribute("lobbyManager");
        req.removeAttribute("gameName");

        //session
        HttpSession session = req.getSession(false);
        if (session == null){
            System.out.println("TODO: handle witth error . go to log-in page");
            //TODO: handle with error . go to log-in page
        }
        GameLobbyDetailes currentDetails = lobbyManager.getGameLobbyDetailsByName((String) session.getAttribute("gameName"));
        String playerName = (String)session.getAttribute("userName");

        currentDetails.removePlayerFromRoom(playerName);
        session.removeAttribute("gameName");
        session.removeAttribute("playerNumber" );

        //redirect
        resp.sendRedirect(req.getContextPath() + "/lobby");
    }
}