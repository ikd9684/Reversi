package jp.co.excite_software.s_ikeda.reversi;

import jp.co.excite_software.s_ikeda.reversi.Rule.Disc;

/**
 * @author ikd9684
 *
 */
public class Board {

    /**  */
    public static final String LN = System.getProperty("line.separator");

    protected Rule rule;

    protected Disc[][] status;

    public Board(Rule rule) {

        if (rule == null) {
            throw new NullPointerException("Rule は必ず設定してください。");
        }
        this.rule = rule;
        this.rule.setBoard(this);
        this.status = new Disc[rule.getSize()][rule.getSize()];
    }

    public Board(Rule rule, Disc[][] newStatus) {
        this(rule);
        this.status = newStatus;
    }

    public int getSize() {
        return this.rule.getSize();
    }

    public Disc getTurn() {
        return this.rule.getTurn();
    }

    public void pass() {
        this.rule.pass();
    }

    public boolean isDoublePass() {
        return this.rule.isDoublePass();
    }

    public boolean isOverPassBlack() {
        return this.rule.isOverPassBlack();
    }
    public boolean isOverPassWhite() {
        return this.rule.isOverPassWhite();
    }

    public void setDisc(int x, int y) throws ViolationException {
        this.status = this.rule.setDisc(x, y, getTurn());
        this.rule.changeTurn();
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

    public boolean isFinish() {
        return this.rule.isFinish();
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

    public Disc getWinner() {
        return this.rule.getWinner();
    }

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
