package jp.co.excite_software.s_ikeda.reversi;

import jp.co.excite_software.s_ikeda.reversi.Rule.Disc;

abstract public class Processor {

    abstract public String getProcessorName();

    abstract public String getAuthor();

    abstract public String getDescription();

    abstract public Move doMove(Disc[][] status, Disc myDisc);

    public static class Move {
        public static final Move PASS = new Move(-1, -1);

        private int x;
        private int y;
        public Move(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public boolean isPass() {
            return this.x == PASS.x && this.y == PASS.y;
        }
    }

}
