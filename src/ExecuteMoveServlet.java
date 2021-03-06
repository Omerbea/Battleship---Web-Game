import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "ExecuteMoveServlet" , urlPatterns = {"/ExecuteMove"})

public class ExecuteMoveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //get lobby manager
        String resExecuteMove ="";
        System.out.println("executeMoveServlet"  );
        LobbyManager lobbyManager = (LobbyManager) getServletContext().getAttribute("lobbyManager");
        if (lobbyManager == null){
            System.out.println("Error lobby manager is null");
            return;
        }
        // get row and column
        String rowS = (String) req.getParameter("row") ;

        String columnS = (String) req.getParameter("col") ;


        // Handle Session
        HttpSession session = req.getSession(false);
        if (session == null){
            System.out.println("session destroyed");
            req.getRequestDispatcher("/WEB-INF/logIn.jsp").forward(req , resp);
            return;
        }
        System.out.println(session.getAttribute("playerNumber") + "in execute move");
        //get gameManager
        String gameName = (String) session.getAttribute("gameName");
        if (gameName == null){
            System.out.println("Warnnig: need to handle this issue better");
            System.out.println("we delete gameName from session already.. ");
            //req.getRequestDispatcher("/WEB-INF/lobby.jsp").forward(req , resp);
//            resp.sendRedirect(req.getContextPath() + "/lobby");
            resp.sendRedirect(req.getContextPath()+ "/jsp/lobby.jsp" );

            return;
        }
        GameManager gameManager = lobbyManager.getGameManagerByName(gameName);
        int player = (int) session.getAttribute("playerNumber");
        player--;

        if(rowS == null ||
           columnS == null) {
            System.out.println("rowS or columnS == null");

        } else {
            int column = Integer.parseInt(columnS);
            int row = Integer.parseInt(rowS);
            //execute logic move
            resExecuteMove = gameManager.executeMove(row, column);
        }
        System.out.println(resExecuteMove);
        //check if the player (I) win
        if (resExecuteMove == "Win"){
            System.out.println("I Win");
            resExecuteMove = "Win";

        }
        //verify the game is still running
        else if (gameManager.getIsGameOver()){
            //the game not running
            if (gameManager.getQuitGame()){
                System.out.println("im "+ player + " and my rival quit" );
                //System.out.println("Rival quit");
                resExecuteMove = "rivalQuit";
            }
            else{
                resExecuteMove="rivalWin";
                System.out.println("rivalWin");
            }
            //session.removeAttribute("gameName");
        }
        //get ready board
        char [][] playerBoard = gameManager.getBoardByPlayerNumber(player);
        System.out.println("get my board");
        char [][] rivalBoard = gameManager.getRivalBoardByPlayerNumber(player);
        System.out.println("get rival board");
        // get ready statistics
        Statistics statistics = gameManager.getGameStatisticByPlayer(player);

        //is your turn or not
        boolean isMyTurn = false;
        if ( player == gameManager.getWhoPlay()){
            isMyTurn = true;
        }

        //get list of possible position for mine
        ArrayList <Position> position = gameManager.getPossibolePositionsSetMine(player);

        System.out.println(statistics.getAvargeTimeTurn());

        System.out.println("im "+ player + "my res = " + resExecuteMove);


        //Type game
        GameLobbyDetailes gameLobbyDetailes = lobbyManager.getGameLobbyDetailsByName(gameName);
        String typeGame = gameLobbyDetailes.getTypeGame();


        // prepare response
        ArrayList <Object> array4Response = new ArrayList<Object>();
        array4Response.add(statistics); //0
        array4Response.add(isMyTurn); //1
        array4Response.add(playerBoard); //2
        array4Response.add(rivalBoard); //3
        array4Response.add(resExecuteMove); //4
        array4Response.add(session.getAttribute("userName")); //5
        array4Response.add(position); //6
        array4Response.add(typeGame);//7
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        Gson gson = new GsonBuilder().create();
        gson.toJson(array4Response , writer);
    }

}

