import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.corba.se.spi.ior.ObjectKey;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

@WebServlet(name = "lobbyServlet" , urlPatterns = {"/lobby"})
public class lobbyServlet extends HttpServlet {
    int countUsersEntered = 0;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (countUsersEntered == 0 ) {
            //delete older files from the past instance
            countUsersEntered++;
            File theDir = new File("gamesXmls");
            if (theDir.exists()) {
                Path dirPath = Paths.get("./gamesXmls");
                Files.walk(dirPath)
                        .map(Path::toFile)
                        .sorted(Comparator.comparing(File::isDirectory))
                        .forEach(File::delete);


            }
        }
        System.out.println("someone in lobbyServlet doGet");
        LobbyManager lobbyManager = (LobbyManager)getServletContext().getAttribute("lobbyManager");
        HttpSession session = req.getSession(false);
        if (session == null){
            req.getRequestDispatcher("/WEB-INF/logIn.jsp").forward(req , resp);
            return;
        }
        String userName = (String)session.getAttribute("userName");
        if (userName == null){
            req.getRequestDispatcher("/WEB-INF/logIn.jsp").forward(req , resp);
            return;
        }

        String status = (String) session.getAttribute("isFirstTime");
        if (status != null && status =="no"){
            System.out.println("is not my first time in lobby servlet");
        }
        else{
            try {
                lobbyManager.addPlayerToList((String)session.getAttribute("userName"));
            } catch (Exception e) {
                session.invalidate();
                resp.setContentType("application/json");
                PrintWriter writer = resp.getWriter();
                // start building json
                String errMsg = e.getMessage();
                req.setAttribute("errMsg", errMsg);
                System.out.println("ErrorMsg:"+ errMsg);

                req.getRequestDispatcher("/WEB-INF/logIn.jsp").forward(req, resp);
                return;
            }
            session.setAttribute("isFirstTime", "no");
        }
        System.out.println("send lobby.jsp");
        req.getRequestDispatcher("/WEB-INF/lobby.jsp").forward(req , resp);
    }

}
