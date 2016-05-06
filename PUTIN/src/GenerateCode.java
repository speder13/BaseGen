import com.sun.javafx.collections.ArrayListenerHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class GenerateCode implements VisitAST{
    public ArrayList<String> results=new ArrayList<>();
    public ArrayList<String> getThings(ProgramNode node, HashMap<String, SymbolTableEntry> Sym){
        visitProgram(node);
        return results;
    }

    @Override
    public void visitProgram(ProgramNode node) {
        visitBoard(node.BoardN);
    }

    @Override
    public void visitBoard(BoardNode node) {
        visitSize(node.SizeN);
    }

    @Override
    public void visitPieces(PiecesNode node) {

    }

    @Override
    public void visitSetup(SetupNode node) {

    }

    @Override
    public void visitRules(RulesNode node) {

    }

    @Override
    public void visitWinCondition(WinConditionNode node) {

    }

    @Override
    public void visitMethod(MethodNode node) {

    }

    @Override
    public void visitSize(SizeNode node) {

    }

    @Override
    public void visitBoardParam(BoardParamNode node) {

    }

    @Override
    public void visitStmt(StmtNode node) {

    }

    @Override
    public void visitPlayer(PlayersNode node) {

    }

    @Override
    public void visitTurn(TurnNode node) {

    }

    @Override
    public void visitTurnDistribution(TurnDistributionNode node) {

    }

    @Override
    public void visitWinCND(WinCNDNode node) {

    }

    @Override
    public void visitType(TypeNode node) {

    }

    @Override
    public void visitHeight(HeightNode node) {

    }

    @Override
    public void visitWidth(WidthNode node) {

    }

    @Override
    public void visitMC(MCNode node) {

    }

    @Override
    public void visitPieceType(PieceTypeNode node) {

    }

    @Override
    public void visitExpression(ExpressionNode node) {

    }

    @Override
    public void visitElsIf(ElsIfNode node) {

    }

    @Override
    public void visitEls(ElsNode node) {

    }

    @Override
    public void visitOption(OptionNode node) {

    }

    @Override
    public void visitDef(DefNode node) {

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
}
