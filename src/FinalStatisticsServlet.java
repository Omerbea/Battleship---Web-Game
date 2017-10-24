import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "finalStatisticsServlet" , urlPatterns = {"/finalStatistics"})
public class FinalStatisticsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        String gameName = (String) session.getAttribute("gameName");
        GameManager gameManager = lobbyManager.getGameManagerByName(gameName);
        Statistics statisticsPlayer1 = gameManager.getGameStatisticByPlayer(0);
        Statistics statisticsPlayer2 = gameManager.getGameStatisticByPlayer(1);
        resp.sendRedirect(req.getContextPath() + "/lobby");

    }
}
