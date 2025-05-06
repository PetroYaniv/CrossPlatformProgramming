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
        //setID(id);
        setName(name);
        this.Victory = 0;
        this.Defeat = 0;

    }

    public void increaseVictory() {this.Victory++;}

    public void increaseDefeat() {this.Defeat++;}

}
