import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebServlet (name = "loginServlet" , urlPatterns = {"/logIn"})
public class loginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LobbyManager lobbyManager = (LobbyManager)getServletContext().getAttribute("lobbyManager");
        HttpSession session;
        if (lobbyManager == null){
            lobbyManager = new LobbyManager();
            getServletContext().setAttribute("lobbyManager",lobbyManager);
        }


        session = request.getSession(false);
        //String userName= (String) session.getAttribute("userName");
        if(session == null) {

            session = request.getSession(true);
            //session.setMaxInactiveInterval(20);
            System.out.println("new user");
            session.setAttribute("userName", getServletContext().getContext("userName"));
            request.getRequestDispatcher("/WEB-INF/logIn.jsp").include(request, response);
        } else{
            response.sendRedirect(request.getContextPath() + "/lobby");
            System.out.println("user already in the system redirecting to lobby");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        System.out.println(userName +" " + password);
        HttpSession session = req.getSession(false);
        if (session == null){
            //TODO: handle with bug
            System.out.println("ERROR!");
            return;
        }
        session.setAttribute("userName" , userName);
        resp.sendRedirect(req.getContextPath() + "/lobby");
    }
}
