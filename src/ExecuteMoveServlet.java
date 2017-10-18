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

@WebServlet(name = "ExecuteMoveServlet" , urlPatterns = {"/ExecuteMoveServlet"})

public class ExecuteMoveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LobbyManager lobbyManager = (LobbyManager) getServletContext().getAttribute("lobbyManager");
        GameManager gameManager = lobbyManager.getGameManagerByName((String)req.getParameter("gameName"));
        //TODO - start
        int row = 0;
        int column = 0;
        HttpSession session = req.getSession(false);
        if (session == null){
            req.getRequestDispatcher("/WEB-INF/logIn.jsp").forward(req , resp);
            return;
        }
        int player =  (int) session.getAttribute("playerNumber");

        //execute logic move
        gameManager.executeMove(row,column);

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
        PrintWriter writer = resp.getWriter();
        Gson gson = new GsonBuilder().create();
        gson.toJson(statistics , writer);
        gson.toJson(isMyTurn,writer);


    }

}

