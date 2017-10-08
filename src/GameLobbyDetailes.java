public class GameLobbyDetailes {
    String name;
    String playerNameThatLoadedCurrentGame;
    int boardSize;
    String typeGame;
    boolean isHaveRegisterPlayer;
    transient GameManager gameManager ;
    public GameLobbyDetailes (String i_name, String i_playerNameThatLoadedCurrentGame, int i_boardSize, String i_typeGame, GameManager i_gameManager){
        this.name =i_name;
        this.playerNameThatLoadedCurrentGame = i_playerNameThatLoadedCurrentGame;
        this.boardSize = i_boardSize;
        this.typeGame = i_typeGame;
        this.isHaveRegisterPlayer = false;
        this.gameManager = i_gameManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
