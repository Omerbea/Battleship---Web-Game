import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;

@WebServlet(name = "activePlayersServlet" , urlPatterns = "/activePlayersData")

public class activePlayersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LobbyManager lobbyManager = (LobbyManager)getServletContext().getAttribute("lobbyManager");
        LinkedList<String> activePlayers = lobbyManager.getPlayers();

        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        // start building json
        Gson gson = new GsonBuilder().create();
        gson.toJson(activePlayers , writer);

    }
}
