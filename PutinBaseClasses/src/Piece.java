/**
 * Created by Søren on 29-04-2016.
 */
/*The SuperClass for Pieces*/

public class Piece {

    public Boolean Move(){
        return false;
    }
    public Boolean PlaceAt(Coordinate Input){
        return false;
    }
    public boolean CanMoveTo(Coordinate Input){
        return false;
    }
}
