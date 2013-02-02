package jp.co.excite_software.s_ikeda.reversi.simple;

import jp.co.excite_software.s_ikeda.reversi.Board;
import jp.co.excite_software.s_ikeda.reversi.Rule;
import jp.co.excite_software.s_ikeda.reversi.ViolationException;

public class SimpleRule implements Rule {

    /**  */
    private static final int SIZE = 8;
    /**  */
    private static final int MAX_PASS_TIME = 3;

    /**  */
    private static Disc[][] DEFAULT_STATUS = {
        { Disc.NULL, Disc.NULL, Disc.NULL, Disc.NULL,  Disc.NULL,  Disc.NULL, Disc.NULL, Disc.NULL },
        { Disc.NULL, Disc.NULL, Disc.NULL, Disc.NULL,  Disc.NULL,  Disc.NULL, Disc.NULL, Disc.NULL },
        { Disc.NULL, Disc.NULL, Disc.NULL, Disc.NULL,  Disc.NULL,  Disc.NULL, Disc.NULL, Disc.NULL },
        { Disc.NULL, Disc.NULL, Disc.NULL, Disc.WHITE, Disc.BLACK, Disc.NULL, Disc.NULL, Disc.NULL },
        { Disc.NULL, Disc.NULL, Disc.NULL, Disc.BLACK, Disc.WHITE, Disc.NULL, Disc.NULL, Disc.NULL },
        { Disc.NULL, Disc.NULL, Disc.NULL, Disc.NULL,  Disc.NULL,  Disc.NULL, Disc.NULL, Disc.NULL },
        { Disc.NULL, Disc.NULL, Disc.NULL, Disc.NULL,  Disc.NULL,  Disc.NULL, Disc.NULL, Disc.NULL },
        { Disc.NULL, Disc.NULL, Disc.NULL, Disc.NULL,  Disc.NULL,  Disc.NULL, Disc.NULL, Disc.NULL },
    };

    /**  */
    private Board board;
    /**  */
    private Disc thisTurn;

    public SimpleRule() {
        this.thisTurn = this.getInitialDisc();
    }

    @Override
    public void init(Board board) {
        this.board = board;
    }

    @Override
    public Disc getInitialDisc() {
        return Disc.BLACK;
    }
    @Override
    public Disc getTurn() {
        return this.thisTurn;
    }

    private int doublePass = 0;
    private int whiteLastPassTime = MAX_PASS_TIME;
    private int blackPass = whiteLastPassTime;
    @Override
    public void pass() {
        doublePass++;
        if (this.thisTurn == Disc.BLACK) {
            blackPass--;
        }
        if (this.thisTurn == Disc.WHITE) {
            whiteLastPassTime--;
        }
        changeTurn();
    }

    @Override
    public boolean isDoublePass() {
        return doublePass == 2;
    }
    public boolean isOverPassBlack() {
        return blackPass == 0;
    }
    public boolean isOverPassWhite() {
        return whiteLastPassTime == 0;
    }

    public void changeTurn() {
        this.thisTurn = (this.thisTurn == Disc.BLACK) ? Disc.WHITE : Disc.BLACK;
    }

    @Override
    public int getSize() {
        return SIZE;
    }
    @Override
    public Disc[][] getDefaultStatus() {

        Disc[][] deepCopy = new Disc[SIZE][SIZE];
        for (int y = 0; y < DEFAULT_STATUS.length; y++) {
            for (int x = 0; x < DEFAULT_STATUS[0].length; x++) {
                deepCopy[y][x] = DEFAULT_STATUS[y][x];
            }
        }
        return deepCopy;
    }

    @Override
    public Disc[][] setDisc(int x, int y, Disc disc) throws ViolationException {

        Disc[][] status = board.getStatus();

        if (y < 0 || SIZE <= y || x < 0 || SIZE <= x) {
            throw new IndexOutOfBoundsViolation();
        }
        if (status[y][x] != Disc.NULL) {
            throw new AlreadyExistsViolation();
        }

        if (!reverse(status, x, y, disc)) {
            throw new NothingCanTakeViolation();
        }

        doublePass = 0;

        changeTurn();

        status[y][x] = disc;
        return status;
    }

    protected boolean reverse(Disc[][] status, final int x, final int y, Disc disc) {

        boolean hasReverse = false;
        hasReverse = reverse(status, x, y, -1, -1, disc) | hasReverse;
        hasReverse = reverse(status, x, y,  0, -1, disc) | hasReverse;
        hasReverse = reverse(status, x, y, +1, -1, disc) | hasReverse;
        hasReverse = reverse(status, x, y, -1,  0, disc) | hasReverse;
        hasReverse = reverse(status, x, y, +1,  0, disc) | hasReverse;
        hasReverse = reverse(status, x, y, -1, +1, disc) | hasReverse;
        hasReverse = reverse(status, x, y,  0, +1, disc) | hasReverse;
        hasReverse = reverse(status, x, y, +1, +1, disc) | hasReverse;
        return hasReverse;
    }
    protected boolean reverse(Disc[][] status,
            final int x, final int y,
            final int dx, final int dy, Disc disc) {

        boolean negative = false;
        boolean reverse = false;
        for (int sx = x+dx, sy = y+dy
                ; 0 <= sx && sx < SIZE && 0 <= sy && sy < SIZE
                ; sx += dx, sy += dy) {

            Disc d = status[sy][sx];
            if (d == Disc.NULL) {
                break;
            }
            if (!negative && d != disc) {
                negative = true;
                continue;
            }
            if (negative && d == disc) {
                reverse = true;
                break;
            }
        }
        for (int sx = x+dx, sy = y+dy
                ; reverse && 0 <= sx && sx < SIZE && 0 <= sy && sy < SIZE
                ; sx += dx, sy += dy) {

            Disc d = status[sy][sx];
            if (negative && d == disc) {
                break;
            }
            status[sy][sx] = disc;
        }

        return reverse;
    }

    @Override
    public boolean isFinish() {

        if (isDoublePass() || isOverPassBlack() || isOverPassWhite()) {
            return true;
        }

        Disc[][] status = board.getStatus();
        for (int y = 0; y < status.length; y++) {
            for (int x = 0; x < status.length; x++) {
                if (status[y][x] == Disc.NULL) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Disc getWinner() {

        int black = board.getCountBlack();
        int white = board.getCountWhite();
        if (black < white) {
            return Disc.WHITE;
        }
        if (white < black) {
            return Disc.BLACK;
        }
        return Disc.NULL;
    }

    @Override
    public EndReason getEnrReason() {
        return new SimpleEndReason();
    }

    public class SimpleEndReason extends EndReason {
        public boolean isDoublePass() {
            return SimpleRule.this.isDoublePass();
        }
        public boolean isOverPassBlack() {
            return SimpleRule.this.isOverPassBlack();
        }
        public boolean isOverPassWhite() {
            return SimpleRule.this.isOverPassWhite();
        }
    }

    public static class IndexOutOfBoundsViolation extends ViolationException {
        private static final long serialVersionUID = 1L;
    }
    public static class AlreadyExistsViolation extends ViolationException {
        private static final long serialVersionUID = 1L;
    }
    public static class NothingCanTakeViolation extends ViolationException {
        private static final long serialVersionUID = 1L;
    }

}
