import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(name = "logoutServlet" , urlPatterns = "/logout")

public class logoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logOut(req,resp);
    }


    public void logOut (HttpServletRequest req, HttpServletResponse resp){
        HttpSession session = req.getSession(false);

        LobbyManager lobbyManager = (LobbyManager)getServletContext().getAttribute("lobbyManager");
        if (session != null) {
            lobbyManager.removePlayerFromList((String)req.getSession().getAttribute("userName"));
            session.invalidate();

        }
    }
}
