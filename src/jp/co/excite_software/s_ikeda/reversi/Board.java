package jp.co.excite_software.s_ikeda.reversi;

import jp.co.excite_software.s_ikeda.reversi.Rule.Disc;
import jp.co.excite_software.s_ikeda.reversi.Rule.EndReason;

/**
 * @author ikd9684
 *
 */
abstract public class Board {

    /**  */
    public static final String LN = System.getProperty("line.separator");

    protected Rule rule;

    protected Disc[][] status;

    public Board(Rule rule) {

        if (rule == null) {
            throw new NullPointerException("Rule は必ず設定してください。");
        }
        this.rule = rule;
        this.rule.init(this);
        this.status = this.rule.getDefaultStatus();
    }

    public Disc[][] getStatus() {
        Disc[][] retStatus = new Disc[status.length][status[0].length];
        for (int y = 0; y < status.length; y++) {
            for (int x = 0; x < status[0].length; x++) {
                retStatus[y][x] = status[y][x];
            }
        }
        return retStatus;
    }

    protected Disc getTurn() {
        return this.rule.getTurn();
    }

    protected void pass() {
        this.rule.pass();
    }

    protected void setDisc(int x, int y, Disc disc) throws ViolationException {
        this.status = this.rule.setDisc(x, y, disc);
    }

    protected boolean isFinish() {
        return this.rule.isFinish();
    }

    protected Disc getWinner() {
        return this.rule.getWinner();
    }

    protected EndReason getendEndReason() {
        return this.rule.getEnrReason();
    }

    public int getCountBlack() {
        int black = 0;
        for (int y = 0; y < status.length; y++) {
            for (int x = 0; x < status[0].length; x++) {
                if (status[y][x] == Disc.BLACK) {
                    black++;
                }
            }
        }
        return black;
    }
    public int getCountWhite() {
        int white = 0;
        for (int y = 0; y < status.length; y++) {
            for (int x = 0; x < status[0].length; x++) {
                if (status[y][x] == Disc.WHITE) {
                    white++;
                }
            }
        }
        return white;
    }

    abstract public void start();

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("  1 2 3 4 5 6 7 8" + LN);
        for (int y = 0; y < status.length; y++) {
            sb.append((y + 1) + " ");
            for (int x = 0; x < status[0].length; x++) {
                sb.append(status[y][x]);
            }
            sb.append(LN);
        }
        return sb.toString();
    }

}
