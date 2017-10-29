import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet(name = "removeGameByPlayerServlet" , urlPatterns = {"/removeGameByPlayer"})
public class removeGameByPlayerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("removeGameByPlayerServler");
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


        String namePlayer = (String)session.getAttribute("userName");
        //TODO: change the game name get from the req
        String gameName =  req.getParameter("gameName");
        lobbyManager.removeGameManagerByplayer(gameName, namePlayer);
        String fileName = "gamesXmls/SaveFileUploaded" + gameName + ".xml";
        File file =new File(fileName);

        Files.deleteIfExists(file.toPath());

    }
}