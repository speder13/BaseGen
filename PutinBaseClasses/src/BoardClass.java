/**
 * Created by Jonas on 02-05-2016.
 */
//A class for containing the game board.
public class BoardClass {

    Integer Height;
    Integer Width;
    public BoardClass(){
        this.Height = 1;
        this.Width = 1;
    }
    public BoardClass(Integer InHeight, Integer InWidth){
        this.Height = InHeight;
        this.Width = InWidth;
    }

    //Getters and Setters
    public Integer getHeight() {
        return Height;
    }

    public void setHeight(Integer height) {
        Height = height;
    }

    public Integer getWidth() {
        return Width;
    }

    public void setWidth(Integer width) {
        Width = width;
    }
}
