import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "finalStatisticsServlet" , urlPatterns = {"/finalStatistics"})
public class FinalStatisticsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //lobby manager handle
        LobbyManager lobbyManager = (LobbyManager) getServletContext().getAttribute("lobbyManager");
        if (lobbyManager == null){
            System.out.println("Error lobby manager is null");
            return;
        }

        // Handle Session
        HttpSession session = req.getSession(false);
        if (session == null){
            req.getRequestDispatcher("/WEB-INF/logIn.jsp").forward(req , resp);
            return;
        }
        //get statistics
        String gameName = (String) session.getAttribute("gameName");
        GameManager gameManager = lobbyManager.getGameManagerByName(gameName);
        Statistics statisticsPlayer1 = gameManager.getGameStatisticByPlayer(0);
        Statistics statisticsPlayer2 = gameManager.getGameStatisticByPlayer(1);

        //create response
        ArrayList<Object> array4Response = new ArrayList<Object>();
        array4Response.add(statisticsPlayer1);
        array4Response.add(statisticsPlayer2);
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        Gson gson = new GsonBuilder().create();
        gson.toJson(array4Response , writer);

        //do finish work
        session.removeAttribute("gameName");
        session.removeAttribute("playerNumber");

        String statusIsITheFirst2BackLobby = (String) getServletContext().getAttribute("finish"+gameName);
        if (statusIsITheFirst2BackLobby == null){
            //I the First
            getServletContext().setAttribute("finish"+gameName, "yes");
            System.out.println("im the first back to lobby");
        }

        else{
            //I the second I can Delete gameManager
            getServletContext().removeAttribute("finish"+gameName);
            String userName=(String) session.getAttribute("userName");
            restartGameManager(gameName,gameManager, lobbyManager, userName);
            System.out.println("im the second back to lobby");

        }

    }

    public void restartGameManager (String gameName , GameManager gameManager, LobbyManager lobbyManager , String userName){
        GameLobbyDetailes gameLobbyDetailes = lobbyManager.getGameLobbyDetailsByName(gameName);
        gameLobbyDetailes.restartGameDetailes();
        /*String fileName = gameManager.getFileName();
        lobbyManager.removeGameManager(gameName);

        try {
            lobbyManager.setNewGame(fileName, gameName, userName);
        }
        catch (Exception e){
            System.out.println("Error! exeption: " + e.getMessage());
        }*/
    }
}
