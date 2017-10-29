import GameParser.BattleShipGame;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


@MultipartConfig
@WebServlet(name = "loadGameServlet" , urlPatterns = {"/loadGame"})
public class loadGameServlet extends HttpServlet {
    int uniqNumber = 4;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //handle session
        HttpSession session = req.getSession(false);
        if (session == null ||session.isNew()){
            System.out.println("the user new in load game. need to log out the user");
            //TODO: log out the user
            //logoutServlet logOut= new logoutServlet();
            //logOut.logOut(req,resp);
            //return;
            logoutServlet logOutServlet = new logoutServlet();
            //logOutServlet.logOut(req,resp);
            resp.sendRedirect(req.getContextPath() + "/logIn");
            return;
        }

        session.removeAttribute("errorLoadfile");
    //get params
        String userName = (String)session.getAttribute("userName");
        String gameName = req.getParameter("nameGame");
    //handle file xml and save it
        Part filePart = req.getPart("gameFile");
        InputStream fileContent = filePart.getInputStream();
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(BattleShipGame.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            BattleShipGame ParsedGame = (BattleShipGame) jaxbUnmarshaller.unmarshal(filePart.getInputStream());
        }
        catch (Exception e){
            System.out.println("ERROR!");
            System.out.println("not xml file");
            session.setAttribute("errorLoadfile", "Please choose xml file");
            //req.setAttribute("errorLoadfile", e.getMessage());
            //resp.sendRedirect(req.getContextPath()+ "/lobby");
            resp.setStatus(2);
            resp.sendRedirect(req.getContextPath() + "/lobby");
            return;
        }
        System.out.println(fileContent);
        //TODO: change the path to be relative path
        File theDir = new File("gamesXmls");
        // if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("creating directory: " + theDir.getName());
            boolean result = false;
            try{
                theDir.mkdir();
                result = true;
            }
            catch(SecurityException se){
                //handle it
                System.out.println("Exception!: " + se.getMessage());
                return;
            }
            if(result) {
                System.out.println("DIR created");
            }
        }
        String uniqFileName = "gamesXmls/SaveFileUploaded" + gameName + ".xml";
        System.out.println("save the file : " + uniqFileName);
        File file =new File(uniqFileName);
        try (InputStream input = filePart.getInputStream()) {

            if (file.exists()){
                session.setAttribute("errorLoadfile", "Name exist in the lobby game already... choose anther name");
                resp.setStatus(2);
                resp.sendRedirect(req.getContextPath() + "/lobby");
                return;
            }
            System.out.println(file.toPath());
            Files.copy(input, file.toPath());
        }
    // do vaildation to file and if the file is valid add to the system
        try {

            String nameFileForDebug = filePart.getName();

            LobbyManager lobbyManager = (LobbyManager) getServletContext().getAttribute("lobbyManager");
            lobbyManager.setNewGame(file.toPath().toString(), gameName, userName);
            //gameManager.loadFile(file.getAbsolutePath());
            //Files.deleteIfExists(file.toPath());
        }
        catch (Exception e){
            Files.deleteIfExists(file.toPath());
            System.out.println("ERROR!");
            System.out.println(e.getMessage());
            session.setAttribute("errorLoadfile", e.getMessage());
            //req.setAttribute("errorLoadfile", e.getMessage());
            //resp.sendRedirect(req.getContextPath()+ "/lobby");
            resp.setStatus(2);
            resp.sendRedirect(req.getContextPath() + "/lobby");
            return;

        }
        session.setAttribute("errorLoadfile", "upload file successfully...");
        resp.setStatus(0);
        resp.sendRedirect(req.getContextPath() + "/lobby");
        //req.getRequestDispatcher("/WEB-INF/lobby.jsp").forward(req , resp);

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
