import java.util.ArrayList;

/**
 * Created by Jonas on 02-05-2016.
 */
//A class for containing the game board.
public class BoardClass {

    Integer Height;
    Integer Width;
    ArrayList<ArrayList> SpecialTiles;
    ArrayList IllegalTiles;
    public BoardClass(){
        this.Height = 1;
        this.Width = 1;

        SpecialTiles = new ArrayList<ArrayList>();
        IllegalTiles = new ArrayList();
        SpecialTiles.add(IllegalTiles);
    }
    public BoardClass(Integer InHeight, Integer InWidth){
        if (InHeight < 1){this.Height = 1;}
        else{this.Height = InHeight;}
        if (InWidth < 1){this.Width = 1;}
        else{this.Width = InWidth;}

        SpecialTiles = new ArrayList<ArrayList>();
        IllegalTiles = new ArrayList();
        SpecialTiles.add(IllegalTiles);
    }

    public Integer TileCount(){
        return Height * Width;
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
