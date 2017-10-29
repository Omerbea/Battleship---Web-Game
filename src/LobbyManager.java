import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class LobbyManager {
    private HashMap<String, GameLobbyDetailes> games = new HashMap<String,GameLobbyDetailes>();
    private LinkedList <String> players = new LinkedList<>();

    public void setNewGame (String pathFileXml , String gameName,String playerName)throws Exception {
        GameManager gameManager = new GameManager();
        if (gameName ==null || gameName.equals("")){
            throw new Exception("you must insert name for the game");
        }
        if (games.get(gameName) != null){
            throw new Exception("Game name already exists in the Lobby ");
        }
        if (gameManager.loadFile(pathFileXml)){
            GameLobbyDetailes gameDetailes = createGameDetailes ( gameManager, gameName,playerName, pathFileXml);
            this.games.put(gameName,gameDetailes);
            System.out.println("Lobby: Done set new game ");
        }
        else{
            System.out.println("Warning: game already run");
            throw new Exception("game already run");
        }
    }

    public void restartGameAndDetails (String gameName){
        GameManager gameManager = getGameManagerByName(gameName);
        GameLobbyDetailes gameLobbyDetailes = getGameLobbyDetailsByName(gameName);

        gameLobbyDetailes.restartGameDetailes();

    }

    public void setGameIsActive (String gameName){
        if (gameName == null){
            System.out.println("warring23");
        }
        else{
            games.get(gameName).setActiveGame();
        }
    }


    public GameLobbyDetailes getGameLobbyDetailsByName(String name) {
        return games.get(name);
    }

    public GameManager getGameManagerByName(String name){
        return games.get(name).getGameManager();
    }

    private GameLobbyDetailes createGameDetailes ( GameManager gameManager, String gameName, String playerName, String pathFile){
        int boardSize = gameManager.getBoardSize();
        String typeGame = gameManager.getGameType();
        GameLobbyDetailes gameDetailes = new GameLobbyDetailes(gameName, playerName, boardSize, typeGame, gameManager, pathFile);
        return  gameDetailes;
    }

    public HashMap<String, GameLobbyDetailes> getGames() {
        return games;
    }

    public void addPlayerToList(String name) throws Exception {
        if (name == null){
            throw new Exception("no user name");
        }
        if (name.equals("")){
            throw  new Exception("you must insert user name");
        }
        if(isPlayerExist(name)){

            throw new Exception("Player already exists");
        }
        players.add(name);
    }

    public void removePlayerFromList(String name) {
        players.remove(name);

    }

    public boolean removeGameManagerByplayer (String gameName, String  playerName){
        boolean res =false;
        GameLobbyDetailes gameLobbyDetailes = this.games.get(gameName);
        if (gameLobbyDetailes.getPlayerNameThatLoadedCurrentGame().equals(playerName)){
            this.games.remove(gameName);
            res = true;
        }
        return res;
    }



    public boolean isPlayerExist(String name) {
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
