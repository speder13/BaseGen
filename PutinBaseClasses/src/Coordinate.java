/**
 * Created by Jonas on 02-05-2016.
 */
//Simple class for the storage of coordinates
public class Coordinate {
    Integer x;
    Integer y;

    public Coordinate() {
    }
    public Coordinate(Integer InX, Integer InY){
        this.x = InX;
        this.y = InY;
    }

    //Getters and setters
    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

}
