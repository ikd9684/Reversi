package jp.co.excite_software.s_ikeda.reversi.simple;

import jp.co.excite_software.s_ikeda.reversi.Board;
import jp.co.excite_software.s_ikeda.reversi.Processor;

public class SimpleReversi {

    public static void main(String[] args) {

        Processor procA = new jp.co.excite_software.s_ikeda.reversi.simple.processor.SimpleScoredProcessor();
        Processor procB = new jp.co.excite_software.s_ikeda.reversi.simple.processor.SimpleRandomProcessor();

        Board board = new SimpleBoard(procA, procB);
        board.start();
    }
}
