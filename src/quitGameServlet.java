import javax.persistence.Lob;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "quitGameServlet" , urlPatterns = {"/quitGame"})
public class quitGameServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("in quitGame Servlet");
        //Handle Session
        HttpSession session = req.getSession(false);
        if (session== null){
            System.out.println("Warning..");
            //TODO ridirect to log in
        }

        //game manager
        LobbyManager lobbyManager = (LobbyManager) getServletContext().getAttribute("lobbyManager");
        GameManager gameManager = lobbyManager.getGameManagerByName((String)session.getAttribute("gameName"));
        lobbyManager.setGameIsActive((String)session.getAttribute("gameName"));
        String namePlayer = (String) session.getAttribute("userName");
        gameManager.setQuitGame(true,namePlayer);
        gameManager.finishTheGame();
        //session.removeAttribute("gameName");
        String addr = req.getContextPath() +"/jsp/finishGameStatistics.jsp";
        System.out.println(addr);
        resp.sendRedirect(addr);
        //req.getRequestDispatcher("/WEB-INF/lobby.jsp").forward(req , resp);
        return;

    }
}
