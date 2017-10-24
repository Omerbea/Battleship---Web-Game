public class GameLobbyDetailes {
    String name;
    String playerNameThatLoadedCurrentGame;
    int boardSize;
    String typeGame;
    String isActiveGame;
    transient GameManager gameManager ;
    private int playersEnteredGame = 0 ;
    public GameLobbyDetailes (String i_name, String i_playerNameThatLoadedCurrentGame, int i_boardSize, String i_typeGame, GameManager i_gameManager){
        this.name =i_name;
        this.playerNameThatLoadedCurrentGame = i_playerNameThatLoadedCurrentGame;
        this.boardSize = i_boardSize;
        this.typeGame = i_typeGame;
        this.isActiveGame = "No";
        this.gameManager = i_gameManager;

    }

    public void setActiveGame() {
        isActiveGame = "yes";
    }

    public void setanctiveGame (){
        isActiveGame="false";
    }

    public void addPlayerEntered() {
        playersEnteredGame++;
    }

    public void removePlayerFromRoom() {
        playersEnteredGame--;
    }

    public int getNumberOfPlayersInGame() {
        return playersEnteredGame;
    }

    public int amountOfPlayersInGame() {
        return playersEnteredGame;
    }
    public GameManager getGameManager() {
        return gameManager;
    }
}
