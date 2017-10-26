public class GameLobbyDetailes {
    String name;
    String playerNameThatLoadedCurrentGame;
    int boardSize;
    String typeGame;
    String isActiveGame;
    transient GameManager gameManager ;
    private int playersEnteredGame = 0 ;
    private String pathFile;
    public GameLobbyDetailes (String i_name, String i_playerNameThatLoadedCurrentGame, int i_boardSize, String i_typeGame, GameManager i_gameManager, String i_pathFile){
        this.name =i_name;
        this.playerNameThatLoadedCurrentGame = i_playerNameThatLoadedCurrentGame;
        this.boardSize = i_boardSize;
        this.typeGame = i_typeGame;
        this.isActiveGame = "No";
        this.gameManager = i_gameManager;
        this.pathFile = i_pathFile;
    }

    public void restartGameDetailes (/*GameManager gameManag*/){
        this.isActiveGame = "No";
        GameManager gameManager = new GameManager();
        try {
            gameManager.loadFile(this.pathFile);

        }catch (Exception e){
            System.out.println("Exeption!! " + " " + e.getMessage());
            return;
        }
        playersEnteredGame =0;
        this.gameManager = gameManager;

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
