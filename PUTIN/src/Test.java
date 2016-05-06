import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.TokenStream;
import org.antlr.v4.runtime.ANTLRInputStream;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class Test {
    public static void main(String[] args){
        /*File file = new File("C:\\Users\\jacobholm\\Desktop\\TestCode.txt");
        FileInputStream fis = new FileInputStream(file);

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        while (br.readLine() != null) {
            line += br.readLine();
        }
        br.close();
        */
        ANTLRInputStream inputStream = new ANTLRInputStream("/*  COMMENT\n" +
                "    COMMENT  */\n" +
                "//COMMENT\n" +
                "Board[\n" +
                "\tHeight = 5;\n" +
                "\tWidth = 5;\n" +
                "\tIllegalTiles = ((1,1),(3,3),(5,5));\n" +
                "\tSpecialTiles TallGrassTiles = ((2,2),(4,4));\n" +
                "]\n" +
                "\n" +
                "Pieces[\n" +
                "\tPiece King = {\n" +
                "\t    Moves = (H(0,1), V(0,1));\n" +
                "\t}\n" +
                "\tPiece Warrior = {\n" +
                "\t    Moves = (R(0,1), (H(2,2), V(1,1)), (V(2,2), H(1,1)));\n" +
                "\t}\n" +
                "]\n" +
                "\n" +
                "Setup[\n" +
                "    \tPlayers = 2;\n" +
                "\n" +
                "\tPiece Rudolf = King;\n" +
                "\tPiece Khan = King;\n" +
                "\tPiece W1 = Warrior;\n" +
                "\tPiece W2 = Warrior;\n" +
                "\tPiece W3 = Warrior;\n" +
                "\tPiece W4 = Warrior;\n" +
                "\t\n" +
                "\tRudolf.PlaceAt(1,5);\n" +
                "\tKhan.PlaceAt(5,1);\n" +
                "\tW1.PlaceAt(4,1);\n" +
                "\tW2.PlaceAt(5,2);\n" +
                "\tW3.PlaceAt(1,4);\n" +
                "\tW4.PlaceAt(2,5);\n" +
                "\t\n" +
                "\tPlayer0.PieceList = (Khan, W1, W2);\n" +
                "\tPlayer1.PieceList = (Rudolf, W3, W4);\n" +
                "]\n" +
                "\n" +
                "Rules[\n" +
                "\tTurn Start{\n" +
                "\t    Options(Input()){\n" +
                "\t        Option 1: {PlayerAll.CurrentTurn = Action1;}\n" +
                "\t        Option 2: {PlayerAll.CurrentTurn = Action2;}\n" +
                "\t    }\n" +
                "\t}\n" +
                "\t\n" +
                "\tTurn Action1{\n" +
                "\t    ForEach(Piece In PlayerPieceList){\n" +
                "\t        PlayerAll.PlayerList.Add(1);\n" +
                "\t    }\n" +
                "\t}\n" +
                "\t\n" +
                "\tTurn Action2{\n" +
                "\t    Coordinate X = input();\n" +
                "\t    Options(input()){\n" +
                "\t        Option 1: {If(PlayerAll.Piece1.CanMoveTo(X)){\n" +
                "\t                      AttackWith(Piece1, X);\n" +
                "\t                  }}\n" +
                "\t        Option 2: {If(PlayerAll.Piece2.CanMoveTo(X)){\n" +
                "\t                      AttackWith(Piece2, X);\n" +
                "\t                  }}\n" +
                "\t        Option 3: {If(PlayerAll.Piece3.CanMoveTo(X)){\n" +
                "\t                      AttackWith(Piece3, X);\n" +
                "\t                  }}     \n" +
                "\t    }\n" +
                "\t}\n" +
                "\t\n" +
                "\tTurnDistribution{\n" +
                "\t    Player0.CurrentTurn = Start;\n" +
                "\t    Player1.CurrentTurn = Start;\n" +
                "\t}\n" +
                "]\n" +
                "\n" +
                "WinConditions[\n" +
                "\tIf(!Player1.PieceList.Contains(King)){\n" +
                "\t\tPlayer0.Win();\n" +
                "\t}\n" +
                "\tIf(!Player0.PieceList.Contains(King)){\n" +
                "\t\tPlayer1.Win();\n" +
                "\t}\n" +
                "\tIf(Player0.PlayerList.ItemCount Equals (2 * 10) + -2){\n" +
                "\t\tPlayer0.Win();\n" +
                "\t}\n" +
                "\tIf(Player1.PlayerList.ItemCount Equals (2 + 10 + 10) * -2){\n" +
                "\t\tPlayer1.Win();\n" +
                "\t}\n" +
                "]\n" +
                "\n" +
                "AttackWith(Piece P, Coordinate X){\n" +
                "    If(ThisBoard.Tiles(X).Count Equals 1){\n" +
                "        If(ThisBoard.Tiles(X).Is(TallGrassTiles)){\n" +
                "            ThisBoard.Tiles(X).Piece2.Remove();\n" +
                "        }\n" +
                "        Else{\n" +
                "            ThisBoard.Tiles(X).Piece1.Remove();\n" +
                "        }\n" +
                "    }\n" +
                "    P.MoveTo(X);\n" +
                "}");
        PUTINLexer lexer = new PUTINLexer(inputStream);
        org.antlr.v4.runtime.TokenStream tokenStream = new org.antlr.v4.runtime.CommonTokenStream(lexer);
        PUTINParser Parser = new PUTINParser(tokenStream);
        BuildAstVisitor BuildAST = new BuildAstVisitor();
        PUTINNode node = BuildAST.visitProgram(Parser.program());
        HashMap<String, SymbolTableEntry> SymbolTable = new HashMap<String, SymbolTableEntry>();
        BuildSymbolTable BuildSym = new BuildSymbolTable();
        SymbolTable = BuildSym.ReturnSymbolTable(node);

        for (String key : SymbolTable.keySet()) {
            System.out.println("\n" + key);
            for (String key2 : SymbolTable.get(key).SymbolTable.keySet()) {
                System.out.print("  " + key2 + "  |");
            }
            if (!SymbolTable.get(key).SymbolTable.isEmpty()){
                System.out.print("\n");
            }
        }
        //System.out.print(node.toString());
    }
}
