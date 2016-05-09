//import com.sun.javafx.collections.ArrayListenerHelper;


import java.util.ArrayList;
import java.util.HashMap;

public class GenerateCode implements VisitAST{
    public ArrayList<String> results=new ArrayList<String>();
    public ArrayList<String> getThings(ProgramNode node, HashMap<String, SymbolTableEntry> Sym){
        visitProgram(node);
        return results;
    }

    @Override
    public void visitProgram(ProgramNode node) {
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
    public void visitBoard(BoardNode node) {
        visitSize(node.SizeN);
        for (BoardParamNode B : node.BoardParamNA) {
            visitBoardParam(B);
        }
    }
    @Override
    public void visitPieces(PiecesNode node) {
        for (StmtNode S : node.StmtNA) {
            visitStmt(S);
        }
    }

    @Override
    public void visitSetup(SetupNode node) {
        visitPlayer(node.PlayersN);
        for (StmtNode S : node.StmtNA) {
            visitStmt(S);
        }
    }

    @Override
    public void visitRules(RulesNode node) {
        for (TurnNode T : node.TurnNA) {
            visitTurn(T);
        }
        visitTurnDistribution(node.TurnDistributionN);
    }

    @Override
    public void visitWinCondition(WinConditionNode node) {
        for (WinCNDNode W : node.WinCNDNA) {
            visitWinCND(W);
        }
    }

    @Override
    public void visitMethod(MethodNode node) {
        //TERMINAL: NEEDS CODE
    }

    @Override
    public void visitSize(SizeNode node) {
        visitHeight(node.HeightN);
        visitWidth(node.WidthN);
    }

    @Override
    public void visitBoardParam(BoardParamNode node) {
        //TERMINAL: NEEDS CODE
    }

    @Override
    public void visitStmt(StmtNode node) {
        //TERMINAL: NEEDS CODE
    }

    @Override
    public void visitPlayer(PlayersNode node) {
        //TERMINAL: NEEDS CODE
    }

    @Override
    public void visitTurn(TurnNode node) {
        for (StmtNode S : node.StmtNA) {
            visitStmt(S);
        }

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
        //TERMINAL: NEEDS CODE
    }

    @Override
    public void visitHeight(HeightNode node) {
        //TERMINAL: NEEDS CODE
    }

    @Override
    public void visitWidth(WidthNode node) {
        //TERMINAL: NEEDS CODE
    }

    @Override
    public void visitMC(MCNode node) { //First part of a statement. . . ?
        //TERMINAL: NEEDS CODE
    }

    @Override
    public void visitPieceType(PieceTypeNode node) {
        for (StmtNode S : node.StmtNA) {
            visitStmt(S);
        }
    }

    @Override
    public void visitExpression(ExpressionNode node) {
        //TERMINAL: NEEDS CODE
    }

    @Override
    public void visitElsIf(ElsIfNode node) {
        //NONTERMiNAL BUT NEEDS CODE
        for (StmtNode S : node.StmtNA) {
            visitStmt(S);
        }
    }

    @Override
    public void visitEls(ElsNode node) {
        //NONTERMiNAL BUT NEEDS CODE
        for (StmtNode S : node.StmtNA) {
            visitStmt(S);
        }
    }

    @Override
    public void visitOption(OptionNode node) {
        //NONTERMiNAL BUT NEEDS CODE
        for (StmtNode S : node.StmtNA) {
            visitStmt(S);
        }
    }

    @Override
    public void visitDef(DefNode node) {
        //NONTERMiNAL BUT NEEDS CODE
        for (StmtNode S : node.StmtNA) {
            visitStmt(S);
        }
    }

    @Override
    public void visitCoordinate(CoordinateNode node) {
        //TERMINAL: NEEDS CODE
    }

    @Override
    public void visitValue(ValueNode node) {
        //TERMINAL: NEEDS CODE
    }

    @Override
    public void visitStmtMethod(StmtMethodNode node) {
        //TERMINAL: NEEDS CODE
    }

    @Override
    public void visitDirection(DirectionNode node) {
        //TERMINAL: NEEDS CODE
    }

    @Override
    public void visitId(IdNode node) {
        //TERMINAL: NEEDS CODE
    }
}
