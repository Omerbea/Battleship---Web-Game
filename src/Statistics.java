public class Statistics {
    private int score = 0;
    private int missNum = 0;
    private String avargeTimeTurn ;
    private int hits =0;
    private int numofMines =0;
    private int numOfTurns =0;
    public int getHits() {
        return hits;
    }
    private String playerName ;
    private String playerNameQuit ;
    public String getAvargeTimeTurn() {
        return avargeTimeTurn;
    }

    public String getPlayerNameQuit() {
        return playerNameQuit;
    }

    public void setPlayerNameQuit(String playerNameQuit) {
        this.playerNameQuit = playerNameQuit;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getNumOfTurns() {
        return numOfTurns;
    }

    public void setNumOfTurns(int numOfTurns) {
        this.numOfTurns = numOfTurns;
    }

    public int getNumofMines() {
        return numofMines;
    }

    public int getMissNum() {
        return missNum;
    }

    public int getScore() {
        return score;
    }



    public void setScore(int score) {
        this.score = score;
    }

    public void setMissNum(int missNum) {
        this.missNum = missNum;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public void setAvargeTimeTurn(String avargeTimeTurn) {
        this.avargeTimeTurn = avargeTimeTurn;
    }



    public void setNumofMines(int numofMines) {
        this.numofMines = numofMines;
    }
}
