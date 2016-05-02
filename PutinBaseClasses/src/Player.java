import java.util.ArrayList;

/**
 * Created by Jonas on 02-05-2016.
 */
//Class representing a player
public class Player {
    String Name;
    ArrayList<Piece> PieceList = new ArrayList<Piece>();
    ArrayList PlayerList = new ArrayList();
    public Player(String Input){
        Name = Input;
    }

    //Getters and Setters
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public ArrayList<Piece> getPieceList() {
        return PieceList;
    }
    public void setPieceList(ArrayList<Piece> pieceList) {
        PieceList = pieceList;
    }
}
