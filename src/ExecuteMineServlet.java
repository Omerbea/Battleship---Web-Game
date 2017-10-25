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

@WebServlet(name = "ExecuteMineServlet" , urlPatterns = {"/ExecuteMine"})

public class ExecuteMineServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LobbyManager lobbyManager = (LobbyManager) getServletContext().getAttribute("lobbyManager");
        String resExecuteMine ="";
        boolean canMineBePlaced = false;

        if (lobbyManager == null){
            System.out.println("Error lobby manager is null");
            return;
        }
        HttpSession session = req.getSession(false);
        if (session == null){
            req.getRequestDispatcher("/WEB-INF/logIn.jsp").forward(req , resp);
            return;
        }

        String gameName = (String) session.getAttribute("gameName");
        if (gameName == null){
            System.out.println("Warnnig: need to handle this issue better");
            System.out.println("we delete gameName from session already.. ");
            //req.getRequestDispatcher("/WEB-INF/lobby.jsp").forward(req , resp);
//            resp.sendRedirect(req.getContextPath() + "/lobby");
            resp.sendRedirect(req.getContextPath()+ "/jsp/lobby.jsp" );

            return;
        }
        GameManager gameManager = lobbyManager.getGameManagerByName(gameName);

        String rowS = (String) req.getParameter("row") ;
        String columnS = (String) req.getParameter("col") ;

        if(rowS == null ||
                columnS == null) {
            System.out.println("rowS or columnS == null");

        } else {
            int column = Integer.parseInt(columnS);
            int row = Integer.parseInt(rowS);
            //execute logic move
            try {
                canMineBePlaced = gameManager.addMine(row, column);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(canMineBePlaced == false) {
            resExecuteMine = "bad";
            System.out.println("Problem adding mine.");
        } else if(canMineBePlaced == true) {
            System.out.println("Mine has added .");
            resExecuteMine = "good";
        }

        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        Gson gson = new GsonBuilder().create();
        gson.toJson(resExecuteMine , writer);

    }
}
