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
import java.util.HashMap;
import java.util.LinkedList;

@WebServlet(name = "gameLobbyDetailesServlet" , urlPatterns = {"/activeGamesData"})
public class gameLobbyDetailesServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("in gameLobbyDetailesServlet");
        // Handle Session
        HttpSession session = req.getSession(false);
        if (session == null){
            System.out.println("session destroyed");
            req.getRequestDispatcher("/WEB-INF/logIn.jsp").forward(req , resp);
            return;
        }
        LobbyManager lobbyManager = (LobbyManager)getServletContext().getAttribute("lobbyManager");
        HashMap<String, GameLobbyDetailes> activeGame = lobbyManager.getGames();
        LinkedList<String> activePlayers = lobbyManager.getPlayers();

        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        // start building json
        Gson gson = new GsonBuilder().create();
        gson.toJson(activeGame , writer);

        return;
    }
}

