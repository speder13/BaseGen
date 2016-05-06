import java.util.HashMap;

public class SymbolTableEntry {
    enum Type {Number, Text, SpecialTiles, Boolean, Player, Piece, List, Coordinate, Turn, Move, Board}

    public String ID = "";
    public Type type = null;
    public String Value = "";
    public HashMap<String, SymbolTableEntry> SymbolTable = new HashMap<String, SymbolTableEntry>();
}