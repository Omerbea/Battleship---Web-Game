import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class LobbyManager {
    private HashMap<String, GameLobbyDetailes> games = new HashMap<String,GameLobbyDetailes>();
    private LinkedList <String> players = new LinkedList<>();

    public void setNewGame (String pathFileXml , String gameName,String playerName)throws Exception {
        GameManager gameManager = new GameManager();
        if (games.get(gameName) != null){
            throw new Exception("Game name already exists in the Lobby ");
        }
        if (gameManager.loadFile(pathFileXml)){
            GameLobbyDetailes gameDetailes = createGameDetailes ( gameManager, gameName,playerName);
            this.games.put(gameName,gameDetailes);
            System.out.println("Lobby: Done set new game ");
        }
        else{
            System.out.println("Warning: game already run");
            throw new Exception("game already run");
        }
    }

    public GameManager getGameManagerByName(String name){
        return games.get(name).getGameManager();
    }

    private GameLobbyDetailes createGameDetailes ( GameManager gameManager, String gameName, String playerName){
        int boardSize = gameManager.getBoardSize();
        String typeGame = gameManager.getTypeGame;
        GameLobbyDetailes gameDetailes = new GameLobbyDetailes(gameName, playerName, boardSize, typeGame, gameManager);
        return  gameDetailes;
    }

    public HashMap<String, GameLobbyDetailes> getGames() {
        return games;
    }

    /*
    public LinkedList<String> getPlayesList

            public logOut*/
}
