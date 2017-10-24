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
            resp.sendRedirect(req.getContextPath() + "/lobby");
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
        //verify the game is still running
        if (gameManager.getIsGameOver()){
            //the game not running
            System.out.println("Rival quit");
            //req.getRequestDispatcher("/WEB-INF/lobby.jsp").include(req , resp);
            resp.sendRedirect(req.getContextPath() + "/lobby");
            session.removeAttribute("gameName");
            System.out.println( "I back to lobby");
            return;
        }
        //get ready board
        char [][] playerBoard = gameManager.getBoardByPlayerNumber(player);
        char [][] rivalBoard = gameManager.getRivalBoardByPlayerNumber(player);

        // get ready statistics
        Statistics statistics = gameManager.getGameStatisticByPlayer(player);

        //is your turn or not
        boolean isMyTurn = false;
        if ( player == gameManager.getWhoPlay()){
            isMyTurn = true;
        }

        // prepare response
        ArrayList <Object> array4Response = new ArrayList<Object>();
        array4Response.add(statistics);
        array4Response.add(isMyTurn);
        array4Response.add(playerBoard);
        array4Response.add(rivalBoard);
        array4Response.add(resExecuteMove);
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        Gson gson = new GsonBuilder().create();
        gson.toJson(array4Response , writer);
    }

}

