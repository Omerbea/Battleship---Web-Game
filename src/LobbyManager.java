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

    public GameLobbyDetailes getGameLobbyDetailsByName(String name) {
        return games.get(name);
    }

    public GameManager getGameManagerByName(String name){
        return games.get(name).getGameManager();
    }

    private GameLobbyDetailes createGameDetailes ( GameManager gameManager, String gameName, String playerName){
        int boardSize = gameManager.getBoardSize();
        String typeGame = gameManager.getGameType();
        GameLobbyDetailes gameDetailes = new GameLobbyDetailes(gameName, playerName, boardSize, typeGame, gameManager);
        return  gameDetailes;
    }

    public HashMap<String, GameLobbyDetailes> getGames() {
        return games;
    }

    public void addPlayerToList(String name) throws Exception {
        if(isPlayerExist(name)){
            throw new Exception("Player already exists");
        }
        players.add(name);
    }

    public void removePlayerFromList(String name) {
        players.remove(name);

    }

    private boolean isPlayerExist(String name) {
        if (this.players.contains(name)){
            return true;
        }
        else{
            return  false;
        }
        /*
        if(players.stream().filter(playerName -> playerName.equals(name)).count() > 0) {
            return true;
        }
        return false;*/
    }

    public LinkedList<String> getPlayers() {
        return players;
    }

    /*
    public LinkedList<String> getPlayesList

            public logOut*/
}
