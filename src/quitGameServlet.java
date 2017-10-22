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
        HttpSession session = req.getSession(false);
        if (session== null){
            //TODO ridirect to log in
        }

        session.removeAttribute("gameName");
        req.getRequestDispatcher("/WEB-INF/lobby.jsp").forward(req , resp);

    }
}
