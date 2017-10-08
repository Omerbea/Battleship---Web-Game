import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.*;
import java.util.Iterator;


@MultipartConfig
@WebServlet(name = "loadGameServlet" , urlPatterns = {"/loadGame"})
public class loadGameServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //handle session
        HttpSession session = req.getSession(true);
        if (session.isNew()){
            System.out.println("the user new in load game. need to log out the user");
            //TODO: log out the user
        }
    //get params
        String userName = (String)session.getAttribute("userName");
        String gameName = req.getParameter("nameGame");
    //handle file xml and save it
        Part filePart = req.getPart("gameFile");
        InputStream fileContent = filePart.getInputStream();
        System.out.println(fileContent);
        //TODO: change the path to be relative path
        File file =new File("C:\\BattleShip - Tomcat\\SaveFileUploaded.xml");
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath());
        }
    // do vaildation to file and if the file is valid add to the system
        try {
            LobbyManager lobbyManager = (LobbyManager) getServletContext().getAttribute("lobbyManager");

            lobbyManager.setNewGame("C:\\BattleShip - Tomcat\\SaveFileUploaded.xml", gameName, userName);
            //gameManager.loadFile(file.getAbsolutePath());
            resp.sendRedirect(req.getContextPath() + "/lobby");
        }
        catch (Exception e){
            System.out.println("ERROR!");
            System.out.println(e.getMessage());
        }
        /*Part filePart = req.getPart("gameFile");
        InputStream fileContent = filePart.getInputStream();
        OutputStream out = null;
        int read = 0;
        final byte[] bytes = new byte[1024];

        while ((read = fileContent.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }closed = false
        System.out.println(out);
     */}
}
