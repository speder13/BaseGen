import com.sun.org.apache.xpath.internal.operations.And;
import com.sun.org.apache.xpath.internal.operations.Div;
import com.sun.org.apache.xpath.internal.operations.Mod;
import jdk.nashorn.internal.ir.UnaryNode;

import java.util.HashMap;
import java.util.MissingResourceException;

public class BuildSymbolTable implements VisitAST{
    HashMap<String, SymbolTableEntry> SymbolTable = new HashMap<String, SymbolTableEntry>();

    public HashMap<String, SymbolTableEntry> ReturnSymbolTable(PUTINNode AST){
        SymbolTableEntry entry = new SymbolTableEntry();

        SymbolTableEntry entry1 = new SymbolTableEntry();
        entry1.ID = "Height";
        entry1.type = SymbolTableEntry.Type.Number;
        SymbolTable.put(entry.ID, entry);

        SymbolTableEntry entry2 = new SymbolTableEntry();
        entry2.ID = "Width";
        entry2.type = SymbolTableEntry.Type.Number;
        SymbolTable.put(entry.ID, entry);

        SymbolTableEntry entry3 = new SymbolTableEntry();
        entry3.ID = "IllegalTiles";
        entry3.type = SymbolTableEntry.Type.SpecialTiles;
        SymbolTable.put(entry.ID, entry);

        SymbolTableEntry entry4 = new SymbolTableEntry();
        entry4.ID = "ThisBoard";
        entry4.type = SymbolTableEntry.Type.Board;
        SymbolTable.put(entry.ID, entry);

        SymbolTableEntry entry5 = new SymbolTableEntry();
        entry5.ID = "ThisBoard.Tiles()";
        entry5.type = SymbolTableEntry.Type.Coordinate;
        SymbolTable.put(entry.ID, entry);

        SymbolTableEntry entry6 = new SymbolTableEntry();
        entry6.ID = "ThisBoard.Tiles().Is()";
        entry6.type = SymbolTableEntry.Type.Boolean;
        SymbolTable.put(entry.ID, entry);

        SymbolTableEntry entry7 = new SymbolTableEntry();
        entry7.ID = "ThisBoard.Tiles().Count";
        entry7.type = SymbolTableEntry.Type.Number;
        SymbolTable.put(entry.ID, entry);

        SymbolTableEntry entry8 = new SymbolTableEntry();
        entry8.ID = "Input()";
        SymbolTable.put(entry.ID, entry);

        visitProgram((ProgramNode) AST);
        return SymbolTable;
    }

    @Override
    public void visitProgram(ProgramNode node){
        visitBoard(node.BoardN);
        visitPieces(node.PieceN);
        visitSetup(node.SetupN);
        visitRules(node.RulesN);
        visitWinCondition(node.WinConditionN);
        for (MethodNode M: node.methodNA) {
            visitMethod(M);
        }
    }

    @Override
    public void visitBoard(BoardNode node){
        visitSize(node.SizeN);
        for (BoardParamNode B : node.BoardParamNA) {
            visitBoardParam(B);
        }
    }

    @Override
    public void visitPieces(PiecesNode node){
        for (StmtNode S : node.StmtNA) {
            visitStmt(S);
        }
    }

    @Override
    public void visitSetup(SetupNode node){
        visitPlayer(node.PlayersN);
        for (StmtNode S : node.StmtNA) {
            visitStmt(S);
        }
    }

    @Override
    public void visitRules(RulesNode node){
        for (TurnNode T : node.TurnNA) {
            visitTurn(T);
        }
        visitTurnDistribution(node.TurnDistributionN);
    }

    @Override
    public void visitWinCondition(WinConditionNode node){
        for (WinCNDNode W : node.WinCNDNA) {
            visitWinCND(W);
        }
    }

    @Override
    public void visitMethod(MethodNode node) {
        SymbolTableEntry entry = new SymbolTableEntry();

        for (StmtNode S : node.StmtNA) {
            if (getEntry(S, entry.SymbolTable).ID != ""){
                entry.SymbolTable.put(getEntry(S, entry.SymbolTable).ID, getEntry(S, entry.SymbolTable));
            }
        }

        if(node.TypeNA1.size() == 1 && node.ReturnStmtNA.size() == 1) {
            //Check expression
            ExpressionNode temp = node.ReturnStmtNA.get(0).ExpressionN;
            TypeNode typeNode = node.TypeNA1.get(0);
            if (typeNode.toString() == "Number"){
                if (temp instanceof AddNode || temp instanceof SubtractNode ||
                        temp instanceof multiplyNode || temp instanceof DivideNode ||
                        temp instanceof ModNode || temp instanceof MinusNode ||
                        (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof NumberValueNode)){
                    entry.type = SymbolTableEntry.Type.Number;
                }
            }
            else if (typeNode.toString() == "Boolean"){
                if (temp instanceof AndNode || temp instanceof OrNode ||
                        temp instanceof EqualsNode || temp instanceof NotEqualsNode ||
                        temp instanceof LessThanNode || temp instanceof LargerThanNode ||
                        temp instanceof EqualOrLessThanNode || temp instanceof EqualOrLargerThanNode ||
                        temp instanceof NegationNode || (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof BoolValueNode)){
                    entry.type = SymbolTableEntry.Type.Boolean;
                }
            }
            else if (typeNode.toString() == "Text"){
                if (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof TextValueNode){
                    entry.type = SymbolTableEntry.Type.Text;
                }
            }
            else if (typeNode.toString() == "Player"){
                if (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof StmtMethodValueNode){
                    if (SymbolTable.containsKey(((StmtMethodValueNode) ((ValueTermNode) temp).ValueN).StmtMethodNA.get(0).toString())){
                        entry.type = SymbolTableEntry.Type.Player;
                    }
                    else {
                        System.out.print("Non defined ID for Player\n");
                    }
                }
            }
            else if (typeNode.toString() == "List"){
                if (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof ListValueNode){
                    boolean containId = true;
                    for (ValueNode V : ((ListValueNode) ((ValueTermNode) temp).ValueN).ValueNA) {
                        if (V instanceof StmtMethodValueNode){
                            if (!SymbolTable.containsKey(V.toString())){
                                containId = false;
                            }
                        }
                    }
                    if (!containId){
                        System.out.print("Non defined ID for List\n");
                    }
                    entry.type = SymbolTableEntry.Type.List;
                }
            }
            else if (typeNode.toString() == "Coordinate"){
                if (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof CoordinateValueNode){
                    entry.type = SymbolTableEntry.Type.Coordinate;
                }
            }
            else {
                System.out.print("Wrong type in return statement\n");
            }
        }
        else if (node.TypeNA1.size() == 0 && node.ReturnStmtNA.size() == 1){
            System.out.print("No return type found\n");
        }
        else if (node.TypeNA1.size() == 1 && node.ReturnStmtNA.size() == 0){
            System.out.print("No return statement found\n");
        }
        entry.ID = node.IdN.toString();
        SymbolTable.put(node.IdN.toString(),entry);
    }

    @Override
    public void visitSize(SizeNode node) {
        visitHeight(node.HeightN);
        visitWidth(node.WidthN);
    }

    @Override
    public void visitBoardParam(BoardParamNode node) {
        SymbolTableEntry entry = new SymbolTableEntry();
        if(node instanceof SpecialTilesNode){
            entry.type = SymbolTableEntry.Type.SpecialTiles;
            entry.ID = ((SpecialTilesNode) node).IdN.toString();
            if (SymbolTable.get(entry.ID) == null){
                SymbolTable.put(((SpecialTilesNode) node).IdN.toString(),entry);
            }
        }
    }

    public SymbolTableEntry getEntry(StmtNode node, HashMap<String, SymbolTableEntry> Sym){
        SymbolTableEntry entry = new SymbolTableEntry();

        if (node instanceof AssignmentStmtNode2){
            switch (((AssignmentStmtNode2) node).TypeN.toString()) {
                case "Piece": entry.type = SymbolTableEntry.Type.Piece; break;
                case "Number": entry.type = SymbolTableEntry.Type.Number; break;
                case "Text": entry.type = SymbolTableEntry.Type.Text; break;
                case "Boolean": entry.type = SymbolTableEntry.Type.Boolean; break;
                case "Player": entry.type = SymbolTableEntry.Type.Player; break;
                case "List": entry.type = SymbolTableEntry.Type.List; break;
                case "Coordinate": entry.type = SymbolTableEntry.Type.Coordinate; break;
            }
            entry.ID = ((AssignmentStmtNode2) node).IdN.toString();
            //Check expression
        }
        else if (node instanceof AssignmentStmtNode3){
            if (Sym.containsKey(((AssignmentStmtNode3) node).IdNA.toString())){
                SymbolTableEntry.Type type = Sym.get(((AssignmentStmtNode3) node).IdNA.toString()).type;
                //Check expression
                ExpressionNode temp = ((AssignmentStmtNode3) node).ExpressionN;
                if (temp instanceof AddNode || temp instanceof SubtractNode ||
                        temp instanceof multiplyNode || temp instanceof DivideNode ||
                        temp instanceof ModNode || temp instanceof MinusNode ||
                        (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof NumberValueNode)){
                    if (type != SymbolTableEntry.Type.Number){
                        System.out.print("Types dont match\n");
                    }
                }
                if (temp instanceof AndNode || temp instanceof OrNode ||
                        temp instanceof EqualsNode || temp instanceof NotEqualsNode ||
                        temp instanceof LessThanNode || temp instanceof LargerThanNode ||
                        temp instanceof EqualOrLessThanNode || temp instanceof EqualOrLargerThanNode ||
                        temp instanceof NegationNode || (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof BoolValueNode)){
                    if (type != SymbolTableEntry.Type.Boolean){
                        System.out.print("Types dont match\n");
                    }
                }
                if (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof TextValueNode){
                    if (type != SymbolTableEntry.Type.Text){
                        System.out.print("Types dont match\n");
                    }
                }
                if (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof StmtMethodValueNode){
                    if (Sym.containsKey(((StmtMethodValueNode) ((ValueTermNode) temp).ValueN).StmtMethodNA.toString())){
                        if (type != Sym.get(((StmtMethodValueNode) ((ValueTermNode) temp).ValueN).StmtMethodNA.toString()).type){
                            System.out.print("Types dont match\n");
                        }
                    }
                    else {
                        System.out.print("Non defined ID for StmtMethod\n");
                    }
                }
                if (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof ListValueNode){
                    boolean containId = true;
                    boolean containPieces = true;
                    for (ValueNode V : ((ListValueNode) ((ValueTermNode) temp).ValueN).ValueNA) {
                        if (V instanceof StmtMethodValueNode){
                            if (!Sym.containsKey(V.toString())){
                                containId = false;
                            }
                        }
                    }
                    if (!containId){
                        System.out.print("Non defined ID for ListValueNode\n");
                    }
                    entry.type = SymbolTableEntry.Type.List;
                }
                if (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof CoordinateValueNode){
                    entry.type = SymbolTableEntry.Type.Coordinate;
                }
                if (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof MoveValueNode){
                    entry.type = SymbolTableEntry.Type.Move;
                }
            }
        }
        else if (node instanceof IfStmtNode){
            ExpressionNode temp = ((IfStmtNode) node).ExpressionN;
            if (!(temp instanceof AndNode || temp instanceof OrNode ||
                    temp instanceof EqualsNode || temp instanceof NotEqualsNode ||
                    temp instanceof LessThanNode || temp instanceof LargerThanNode ||
                    temp instanceof EqualOrLessThanNode || temp instanceof EqualOrLargerThanNode ||
                    temp instanceof NegationNode || (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof BoolValueNode))){
                System.out.print("Not bool in If statement\n");
            }
            for (StmtNode S : ((IfStmtNode) node).StmtNA) {
                visitStmt(S);
            }
            for (ElsIfNode S : ((IfStmtNode) node).ElsIfNA) {
                visitElsIf(S);
            }
            if(((IfStmtNode) node).ElsNA.size() == 1) {
                visitEls(((IfStmtNode) node).ElsNA.get(0));
            }
        }
        else if (node instanceof RepeatWhileStmtNode){
            ExpressionNode temp = ((RepeatWhileStmtNode) node).ExpressionN;
            if (!(temp instanceof AndNode || temp instanceof OrNode ||
                    temp instanceof EqualsNode || temp instanceof NotEqualsNode ||
                    temp instanceof LessThanNode || temp instanceof LargerThanNode ||
                    temp instanceof EqualOrLessThanNode || temp instanceof EqualOrLargerThanNode ||
                    temp instanceof NegationNode || (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof BoolValueNode))){
                System.out.print("Not bool in RepeatWhile statement\n");
            }
            for (StmtNode S : ((RepeatWhileStmtNode) node).StmtNA) {
                visitStmt(S);
            }
        }
        else if (node instanceof RepeatStmtNode){
            for (StmtNode S : ((RepeatStmtNode) node).StmtNA) {
                visitStmt(S);
            }
        }
        else if (node instanceof OptionsStmtNode){
            ExpressionNode temp = ((OptionsStmtNode) node).ExpressionN;
            if(!(temp instanceof AddNode || temp instanceof SubtractNode ||
                    temp instanceof multiplyNode || temp instanceof DivideNode ||
                    temp instanceof ModNode || temp instanceof MinusNode ||
                    (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof NumberValueNode))){
                System.out.print("Not Number in Options statement\n");
            }
            for (OptionNode O : ((OptionsStmtNode) node).OptionNA) {
                visitOption(O);
            }
            if(((OptionsStmtNode) node).DefNA.size() == 1) {
                visitDef(((OptionsStmtNode) node).DefNA.get(0));
            }
        }
        else if (node instanceof ForeachStmtNode){
            if (Sym.containsKey(((ForeachStmtNode) node).IdN.toString())){
                if(Sym.get(((ForeachStmtNode) node).IdN.toString()).type != SymbolTableEntry.Type.List){
                    System.out.print("Not a List in ForEach\n");
                }
            }
            else {
                System.out.print("Non defined ID in Foreach\n");
            }
            for (StmtNode S : ((ForeachStmtNode) node).StmtNA) {
                visitStmt(S);
            }
        }

        return entry;
    }

    @Override
    public void visitStmt(StmtNode node) {
        SymbolTableEntry entry = new SymbolTableEntry();
        if (node instanceof AssignmentStmtNode1){
            SymbolTableEntry entry1 = new SymbolTableEntry();
            entry1.ID = "Moves";
            entry1.type = SymbolTableEntry.Type.Move;
            entry.SymbolTable.put(entry1.ID, entry1);
            for (StmtNode S : ((AssignmentStmtNode1) node).PieceTypeN.StmtNA) {
                if (getEntry(S, entry.SymbolTable).ID != ""){
                    entry.SymbolTable.put(getEntry(S, entry.SymbolTable).ID, getEntry(S, entry.SymbolTable));
                }
            }
            entry.type = SymbolTableEntry.Type.Piece;
            entry.ID = ((AssignmentStmtNode1) node).IdN.toString();
            SymbolTable.put(((AssignmentStmtNode1) node).IdN.toString(),entry);
        }
        else {
            if (getEntry(node, SymbolTable).ID != ""){
                SymbolTable.put(getEntry(node, SymbolTable).ID, getEntry(node, SymbolTable));
            }
        }
        if (node instanceof AssignmentStmtNode3){
            if (SymbolTable.containsKey(((AssignmentStmtNode3) node).IdNA.toString())){
                SymbolTableEntry.Type type = SymbolTable.get(((AssignmentStmtNode3) node).IdNA.toString()).type;
                //Check expression
                ExpressionNode temp = ((AssignmentStmtNode3) node).ExpressionN;
                if (temp instanceof ValueTermNode && ((ValueTermNode) temp).ValueN instanceof ListValueNode){
                    boolean containId = true;
                    boolean containPieces = true;
                    for (ValueNode V : ((ListValueNode) ((ValueTermNode) temp).ValueN).ValueNA) {
                        if (V instanceof StmtMethodValueNode){
                            if (SymbolTable.containsKey(V.toString())){
                                if (SymbolTable.get(V.toString()).type == SymbolTableEntry.Type.Piece){
                                    containPieces = false;
                                }
                            }
                        }
                    }
                    if (containPieces){
                        for (int i = 0; i < ((ListValueNode) ((ValueTermNode) temp).ValueN).ValueNA.size(); i++) {
                            SymbolTableEntry entry1 = new SymbolTableEntry();
                            entry1.ID = ((AssignmentStmtNode3) node).IdNA.toString() + ".Piece" + i;
                            entry1.type = SymbolTableEntry.Type.Piece;
                            SymbolTable.put(entry1.ID, entry1);

                            SymbolTableEntry entry2 = new SymbolTableEntry();
                            entry2.ID = ((AssignmentStmtNode3) node).IdNA.toString() + ".Piece" + i + ".CanMoveTo()";
                            entry2.type = SymbolTableEntry.Type.Boolean;
                            SymbolTable.put(entry2.ID, entry2);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void visitPlayer(PlayersNode node) {
        if (node instanceof PlayersNode1){

            for (int i = 0; i < ((PlayersNode1) node).NumberValueN.Value; i++){
                String temp = "Player" + i;

                SymbolTableEntry entry1 = new SymbolTableEntry();
                entry1.ID = temp;
                entry1.type = SymbolTableEntry.Type.Player;
                SymbolTable.put(entry1.ID, entry1);

                SymbolTableEntry entry2 = new SymbolTableEntry();
                entry2.ID = temp + ".PieceList";
                entry2.type = SymbolTableEntry.Type.List;
                SymbolTable.put(entry2.ID, entry2);

                SymbolTableEntry entry3 = new SymbolTableEntry();
                entry3.ID = temp + ".PieceList.Contains()";
                entry3.type = SymbolTableEntry.Type.Boolean;
                SymbolTable.put(entry3.ID, entry3);

                SymbolTableEntry entry4 = new SymbolTableEntry();
                entry4.ID = temp + ".CurrentTurn";
                entry4.type = SymbolTableEntry.Type.Turn;
                SymbolTable.put(entry4.ID, entry4);

                SymbolTableEntry entry5 = new SymbolTableEntry();
                entry5.ID = temp + ".PlayerList";
                entry5.type = SymbolTableEntry.Type.List;
                SymbolTable.put(entry5.ID, entry5);

                SymbolTableEntry entry6 = new SymbolTableEntry();
                entry6.ID = temp + ".PlayerList.ItemCount";
                entry6.type = SymbolTableEntry.Type.Number;
                SymbolTable.put(entry6.ID, entry6);
            }
        }
        else if (node instanceof PlayersNode2){
            SymbolTableEntry entry = new SymbolTableEntry();

            for (IdNode I : ((PlayersNode2) node).IdNA) {
                String temp = I.Value;

                SymbolTableEntry entry1 = new SymbolTableEntry();
                entry1.ID = temp;
                entry1.type = SymbolTableEntry.Type.Player;
                SymbolTable.put(entry1.ID, entry1);

                SymbolTableEntry entry2 = new SymbolTableEntry();
                entry2.ID = temp + ".PieceList";
                entry2.type = SymbolTableEntry.Type.List;
                SymbolTable.put(entry2.ID, entry2);

                SymbolTableEntry entry3 = new SymbolTableEntry();
                entry3.ID = temp + ".PieceList.Contains()";
                entry3.type = SymbolTableEntry.Type.Boolean;
                SymbolTable.put(entry3.ID, entry3);

                SymbolTableEntry entry4 = new SymbolTableEntry();
                entry4.ID = temp + ".CurrentTurn";
                entry4.type = SymbolTableEntry.Type.Turn;
                SymbolTable.put(entry4.ID, entry4);

                SymbolTableEntry entry5 = new SymbolTableEntry();
                entry5.ID = temp + ".PlayerList";
                entry5.type = SymbolTableEntry.Type.List;
                SymbolTable.put(entry5.ID, entry5);

                SymbolTableEntry entry6 = new SymbolTableEntry();
                entry6.ID = temp + ".PlayerList.ItemCount";
                entry6.type = SymbolTableEntry.Type.Number;
                SymbolTable.put(entry6.ID, entry6);
            }
        }
    }

    @Override
    public void visitTurn(TurnNode node) {
        SymbolTableEntry entry = new SymbolTableEntry();
        SymbolTableEntry entry1 = new SymbolTableEntry();

        entry1.ID = "PlayerPieceList";
        entry1.type = SymbolTableEntry.Type.List;
        entry.SymbolTable.put(entry1.ID, entry1);

        SymbolTableEntry entry2 = new SymbolTableEntry();
        entry2.ID = "PlayerAll";
        entry2.type = SymbolTableEntry.Type.Player;
        entry.SymbolTable.put(entry2.ID, entry2);

        SymbolTableEntry entry3 = new SymbolTableEntry();
        entry3.ID = "PlayerAll.PieceList";
        entry3.type = SymbolTableEntry.Type.List;
        entry.SymbolTable.put(entry3.ID, entry3);

        SymbolTableEntry entry4 = new SymbolTableEntry();
        entry4.ID = "PlayerAll.PieceList.Contains()";
        entry4.type = SymbolTableEntry.Type.Boolean;
        entry.SymbolTable.put(entry4.ID, entry4);

        SymbolTableEntry entry5 = new SymbolTableEntry();
        entry5.ID = "PlayerAll.CurrentTurn";
        entry5.type = SymbolTableEntry.Type.Turn;
        entry.SymbolTable.put(entry5.ID, entry5);

        SymbolTableEntry entry6 = new SymbolTableEntry();
        entry6.ID = "PlayerAll.PlayerList";
        entry6.type = SymbolTableEntry.Type.List;
        entry.SymbolTable.put(entry6.ID, entry6);

        SymbolTableEntry entry7 = new SymbolTableEntry();
        entry7.ID = "PlayerAll.PlayerList.ItemCount";
        entry7.type = SymbolTableEntry.Type.Number;
        entry.SymbolTable.put(entry7.ID, entry7);

        SymbolTableEntry entry8 = new SymbolTableEntry();
        entry8.ID = "PlayerAll.PieceX.CanMoveTo()";
        entry8.type = SymbolTableEntry.Type.Boolean;
        entry.SymbolTable.put(entry8.ID, entry8);

        for (StmtNode S : node.StmtNA) {
            if (getEntry(S, entry.SymbolTable).ID != ""){
                entry.SymbolTable.put(getEntry(S, entry.SymbolTable).ID, getEntry(S, entry.SymbolTable));
            }
        }

        entry.ID = node.IdN.toString();
        entry.type = SymbolTableEntry.Type.Turn;
        SymbolTable.put(node.IdN.toString(),entry);
    }

    @Override
    public void visitTurnDistribution(TurnDistributionNode node) {
        for (StmtNode S : node.StmtNA) {
            visitStmt(S);
        }
    }

    @Override
    public void visitWinCND(WinCNDNode node) {
        if(node instanceof WinCNDNode1){
            visitStmt(((WinCNDNode1) node).IfStmtN);
        }
        else if (node instanceof WinCNDNode2){
            visitStmt(((WinCNDNode2) node).OptionsStmtN);
        }
    }

    @Override
    public void visitType(TypeNode node) {

    }

    @Override
    public void visitHeight(HeightNode node) {
        SymbolTableEntry entry = new SymbolTableEntry();
        entry.ID = "Height";
        entry.type = SymbolTableEntry.Type.Number;
        SymbolTable.put(entry.ID,entry);
    }

    @Override
    public void visitWidth(WidthNode node) {
        SymbolTableEntry entry = new SymbolTableEntry();
        entry.ID = "Width";
        entry.type = SymbolTableEntry.Type.Number;
        SymbolTable.put(entry.ID,entry);
    }

    @Override
    public void visitMC(MCNode node) {

    }

    @Override
    public void visitPieceType(PieceTypeNode node) {
        for (StmtNode S : node.StmtNA) {
            visitStmt(S);
        }
    }

    @Override
    public void visitExpression(ExpressionNode node) {

    }

    @Override
    public void visitElsIf(ElsIfNode node) {
        for (StmtNode S : node.StmtNA) {
            visitStmt(S);
        }
    }

    @Override
    public void visitEls(ElsNode node) {
        for (StmtNode S : node.StmtNA) {
            visitStmt(S);
        }
    }

    @Override
    public void visitOption(OptionNode node) {
        for (StmtNode S : node.StmtNA) {
            visitStmt(S);
        }
    }

    @Override
    public void visitDef(DefNode node) {
        for (StmtNode S : node.StmtNA) {
            visitStmt(S);
        }
    }

    @Override
    public void visitCoordinate(CoordinateNode node) {

    }

    @Override
    public void visitValue(ValueNode node) {

    }

    @Override
    public void visitStmtMethod(StmtMethodNode node) {

    }

    @Override
    public void visitDirection(DirectionNode node) {

    }

    @Override
    public void visitId(IdNode node) {

    }

    public boolean checkExpression(ExpressionNode node){
        Boolean B = true;

        if (node instanceof BinaryExpressionNode){
            if ()
        }
        else if (node instanceof ValueTermNode){

        }
        else if (node instanceof NegationNode){

        }
        else if (node instanceof MinusNode){

        }

        return B;
    }
}
