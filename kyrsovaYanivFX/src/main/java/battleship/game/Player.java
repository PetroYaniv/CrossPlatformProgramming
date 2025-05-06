package battleship.game;


public class Player {
    //private int ID;
    private String Name;
    public int Victory;
    // private int Draw;
    public int Defeat;

    //public void setID(int ID) {this.ID = ID;};
    public void setName(String Name) {this.Name = Name;};
    //public int getID() {return this.ID;}
    public String getName() {return this.Name;}
    public int getVictory() {return this.Victory;}

    public int getDefeat() {return this.Defeat;}

    public Player(String name) {
        Repository _repos;
        _repos = new DataBaseRepository(
                new DataBaseConnector("BattleShipDB"));

            setName(name);
            this.Victory = 0;
            this.Defeat = 0;
            _repos.addPlayer(this);


    }
    public Player(String name, int victory, int defeat) {

        setName(name);
        this.Victory = victory;
        this.Defeat = defeat;

    }


    public void increaseVictory() {
        Repository _repos;
        _repos = new DataBaseRepository(
                new DataBaseConnector("BattleShipDB"));

        this.Victory++;
        _repos.increasePlayerWins(this);
    }

    public void increaseDefeat() {
        Repository _repos;
        _repos = new DataBaseRepository(
                new DataBaseConnector("BattleShipDB"));

        this.Defeat++;
        _repos.increasePlayerDefeat(this);
    }

    @Override
    public String toString() {
        return "Name: " + getName() +
                ",  Victory count: "+ getVictory()+",  Defeat count: "+ getDefeat()+".";
    }

}
