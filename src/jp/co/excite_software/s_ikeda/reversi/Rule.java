package jp.co.excite_software.s_ikeda.reversi;

public interface Rule {

    public enum Disc {
        BLACK {
            @Override
            public String toString() {
                return "●";
            }
        },
        WHITE {
            @Override
            public String toString() {
                return "○";
            }
        },
        NULL {
            @Override
            public String toString() {
                return "・";
            }
        }
    }

    public void init(Board board);

    public Disc[][] getDefaultStatus();

    public Disc getInitialDisc();

    public Disc getTurn();

    public void pass();

    public boolean isDoublePass();

    public int getSize();

    public Disc[][] setDisc(int x, int y, Disc disc) throws ViolationException;

    public boolean isFinish();

    public Disc getWinner();

    public EndReason getEnrReason();

    abstract public class EndReason {
    }

}
