package jp.co.excite_software.s_ikeda.reversi.simple.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jp.co.excite_software.s_ikeda.reversi.Rule.Disc;

public class SimpleScoredProcessor extends SimpleRandomProcessor {

    /**  */
    private static final int[][] SCORE = {
        { 9, 3, 4, 4, 4, 4, 3, 9 },
        { 3, 0, 5, 5, 5, 5, 0, 3 },
        { 4, 5, 8, 7, 7, 8, 5, 4 },
        { 4, 5, 7, 0, 0, 7, 5, 4 },
        { 4, 5, 7, 0, 0, 7, 5, 4 },
        { 4, 5, 8, 7, 7, 8, 5, 4 },
        { 3, 0, 5, 5, 5, 5, 0, 3 },
        { 9, 3, 4, 4, 4, 4, 3, 9 },
    };

    @Override
    public String getProcessorName() {
        return "SimpleScoredProcessor";
    }
    @Override
    public String getDescription() {
        return "各盤目に重み付けをして、より有効な位置に置こうとします。";
    }

    @Override
    public Move doMove(Disc[][] status, Disc myDisc) {

        ArrayList<Move> moveList = getMoveList(status, myDisc);
        if (moveList.isEmpty()) {
            // pass
            return Move.PASS;
        }

        Collections.sort(moveList, new Comparator<Move>() {
            public int compare(Move m1, Move m2) {
                int thisScore = SCORE[m1.getY() - 1][m1.getX() - 1];
                int trgtScore = SCORE[m2.getY() - 1][m2.getX() - 1];
                return trgtScore - thisScore;
            };
        });

        return moveList.get(0);
    }

}
