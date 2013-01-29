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

    public Disc getInitialDisc();

    public Disc getTurn();

    public void pass();

    public boolean isDoublePass();

    public boolean isOverPassBlack();

    public boolean isOverPassWhite();

    public void changeTurn();

    public int getSize();

    public void setBoard(Board board);

    public Disc[][] setDisc(int x, int y, Disc disc) throws ViolationException;

    public boolean isFinish();

    public Disc getWinner();

}
