import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
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
        Part filePart = req.getPart("gameFile");
        InputStream fileContent = filePart.getInputStream();
        System.out.println(fileContent);
        //TODO: change the path to be relative path
        File file =new File("C:\\jonathan benedek\\computer science\\java\\BattleShipEx3\\Battleship---Web-Game\\Battleship---Web-Game\\SaveFileUploaded.xml");
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath());
        }
        GameManager gameManager = new GameManager();
        try {
            gameManager.loadFile(file.getAbsolutePath());

        }
        catch (Exception e){
            
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
