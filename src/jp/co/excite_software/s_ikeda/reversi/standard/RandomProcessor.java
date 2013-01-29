package jp.co.excite_software.s_ikeda.reversi.standard;

import java.util.ArrayList;
import java.util.Random;

import jp.co.excite_software.s_ikeda.reversi.Board;
import jp.co.excite_software.s_ikeda.reversi.Processor;
import jp.co.excite_software.s_ikeda.reversi.Rule.Disc;

public class RandomProcessor extends Processor {

    @Override
    public String getProcessorName() {
        return "RandomProcessor";
    }

    @Override
    public String getAuthor() {
        return "ikd9684@gmail.com";
    }

    @Override
    public String getDescription() {
        return "It only merely puts on the place which can place a stone at random.";
    }

    @Override
    public Move doMove(Board board) {

        Disc[][] status = board.getStatus();
        int size = board.getSize();
        Disc myDisc = board.getTurn();

        ArrayList<Move> moveList = new ArrayList<Move>();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (isValid(status, x, y, myDisc)) {
                    moveList.add(new Move(x + 1, y + 1));
                }
            }
        }
        int s = moveList.size();
        if (s < 1) {
            // pass
            return Move.PASS;
        }
        Random rnd = new Random(System.currentTimeMillis());
        int ix = rnd.nextInt(s);
        return moveList.get(ix);
    }

    private boolean isValid(Disc[][] status, int x, int y, Disc myDisc) {

        int size = status.length;

        if (y < 0 || size <= y || x < 0 || size <= x) {
            return false;
        }
        if (status[y][x] != Disc.NULL) {
            return false;
        }

        boolean hasReverse = false;
        hasReverse = hasReverse(status, x, y, -1, -1, myDisc) | hasReverse;
        hasReverse = hasReverse(status, x, y,  0, -1, myDisc) | hasReverse;
        hasReverse = hasReverse(status, x, y, +1, -1, myDisc) | hasReverse;
        hasReverse = hasReverse(status, x, y, -1,  0, myDisc) | hasReverse;
        hasReverse = hasReverse(status, x, y, +1,  0, myDisc) | hasReverse;
        hasReverse = hasReverse(status, x, y, -1, +1, myDisc) | hasReverse;
        hasReverse = hasReverse(status, x, y,  0, +1, myDisc) | hasReverse;
        hasReverse = hasReverse(status, x, y, +1, +1, myDisc) | hasReverse;
        return hasReverse;
    }
    protected boolean hasReverse(Disc[][] status,
            final int x, final int y,
            final int dx, final int dy, Disc myDisc) {

        int size = status.length;
        boolean negative = false;
        boolean hasReverse = false;
        for (int sx = x+dx, sy = y+dy
                ; 0 <= sx && sx < size && 0 <= sy && sy < size
                ; sx += dx, sy += dy) {

            Disc d = status[sy][sx];
            if (d == Disc.NULL) {
                break;
            }
            if (!negative && d != myDisc) {
                negative = true;
                continue;
            }
            if (negative && d == myDisc) {
                hasReverse = true;
                break;
            }
        }
        for (int sx = x+dx, sy = y+dy
                ; hasReverse && 0 <= sx && sx < size && 0 <= sy && sy < size
                ; sx += dx, sy += dy) {

            Disc d = status[sy][sx];
            if (negative && d == myDisc) {
                break;
            }
        }

        return hasReverse;
    }

}
