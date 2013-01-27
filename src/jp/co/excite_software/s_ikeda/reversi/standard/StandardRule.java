package jp.co.excite_software.s_ikeda.reversi.standard;

import jp.co.excite_software.s_ikeda.reversi.Board;
import jp.co.excite_software.s_ikeda.reversi.Rule;
import jp.co.excite_software.s_ikeda.reversi.ViolationException;

public class StandardRule implements Rule {

    /**  */
    private static final int SIZE = 8;

    /**  */
    private static Disc[][] DEFAULT_STATUS = {
        { null, null, null, null,       null,       null, null, null },
        { null, null, null, null,       null,       null, null, null },
        { null, null, null, null,       null,       null, null, null },
        { null, null, null, Disc.WHITE, Disc.BLACK, null, null, null },
        { null, null, null, Disc.BLACK, Disc.WHITE, null, null, null },
        { null, null, null, null,       null,       null, null, null },
        { null, null, null, null,       null,       null, null, null },
        { null, null, null, null,       null,       null, null, null },
    };

    /**  */
    private Board board;
    /**  */
    private Disc thisTurn;

    public StandardRule() {
        this.thisTurn = this.getInitialDisc();
    }

    @Override
    public void setBoard(Board board) {
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
    private int whitePass = 3;
    private int blackPass = whitePass;
    @Override
    public void pass() {
        doublePass++;
        if (this.thisTurn == Disc.BLACK) {
            blackPass--;
        }
        if (this.thisTurn == Disc.WHITE) {
            whitePass--;
        }
        changeTurn();
    }

    @Override
    public boolean isDoublePass() {
        return doublePass == 2;
    }
    @Override
    public boolean isOverPassBlack() {
        return blackPass == 0;
    }
    @Override
    public boolean isOverPassWhite() {
        return whitePass == 0;
    }

    @Override
    public void changeTurn() {
        this.thisTurn = (this.thisTurn == Disc.BLACK) ? Disc.WHITE : Disc.BLACK;
    }

    @Override
    public int getSize() {
        return SIZE;
    }
    public static Disc[][] getDefaultStatus() {

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
            throw new ViolationException("盤の外へは置けません。");
        }
        if (status[y][x] != null) {
            throw new ViolationException("指定した位置にはすでに石があります。");
        }

        if (!reverse(status, x, y, disc)) {
            throw new ViolationException("ひとつも石を取れないところには置けません。");
        }

        doublePass = 0;

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
            if (d == null) {
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

        if (isDoublePass()) {
            return true;
        }
        if (isOverPassBlack()) {
            return true;
        }
        if (isOverPassWhite()) {
            return true;
        }

        Disc[][] status = board.getStatus();
        for (int y = 0; y < status.length; y++) {
            for (int x = 0; x < status.length; x++) {
                if (status[y][x] == null) {
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
        return null;
    }

}
