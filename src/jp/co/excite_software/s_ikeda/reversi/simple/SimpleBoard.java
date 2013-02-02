package jp.co.excite_software.s_ikeda.reversi.simple;

import jp.co.excite_software.s_ikeda.reversi.Board;
import jp.co.excite_software.s_ikeda.reversi.Processor;
import jp.co.excite_software.s_ikeda.reversi.Processor.Move;
import jp.co.excite_software.s_ikeda.reversi.Rule.Disc;
import jp.co.excite_software.s_ikeda.reversi.ViolationException;
import jp.co.excite_software.s_ikeda.reversi.simple.SimpleRule.AlreadyExistsViolation;
import jp.co.excite_software.s_ikeda.reversi.simple.SimpleRule.IndexOutOfBoundsViolation;
import jp.co.excite_software.s_ikeda.reversi.simple.SimpleRule.NothingCanTakeViolation;
import jp.co.excite_software.s_ikeda.reversi.simple.SimpleRule.SimpleEndReason;

public class SimpleBoard extends Board {

    /**  */
    private Processor procA;
    /**  */
    private Processor procB;

    public SimpleBoard(Processor procA, Processor procB) {
        super(new SimpleRule());

        if (procA == null || procB == null) {
            throw new NullPointerException("Processor は必ず設定してください。");
        }
        this.procA = procA;
        this.procB = procB;
    }

    @Override
    public void start() {

        final int sleep = 100;
        do {
            System.out.println(this.toString());
            Disc turn = getTurn();
            System.out.println(turn + "の番です。");

            Move move = null;
            int x, y;
            if (turn == Disc.BLACK) {
                move = procA.doMove(this.getStatus(), turn);
            }
            else {
                move = procB.doMove(this.getStatus(), turn);
            }
            sleep(sleep);

            if (move.isPass()) {
                System.out.println(turn + " は パス しました。");
                pass();
            }
            else {
                x = move.getX();
                y = move.getY();
                System.out.println(turn + ": " + x + ", " + y);

                try {
                    setDisc(x - 1, y - 1, turn);
                }
                catch (ViolationException e) {
                    if (e instanceof IndexOutOfBoundsViolation) {
                        System.out.println("番の外へは置けません。");
                    }
                    else if (e instanceof AlreadyExistsViolation) {
                        System.out.println("指定した位置にはすでに石があります。");
                    }
                    else if (e instanceof NothingCanTakeViolation) {
                        System.out.println("ひとつも石を取れないところには置けません。");
                    }
                    else {
                        e.printStackTrace();
                    }
                }
            }
        }
        while (!isFinish());

        System.out.println();
        System.out.println();
        System.out.println(this.toString());

        SimpleEndReason reason = (SimpleEndReason) getendEndReason();

        if (reason.isDoublePass()) {
            System.out.println("両者続けてパスしたため、試合続行不可と判断しました。");
        }

        System.out.println(Disc.BLACK + "=" + this.getCountBlack()
                + " 対 " + this.getCountWhite() + "=" + Disc.WHITE);

        if (!reason.isDoublePass() && reason.isOverPassBlack()) {
            System.out.println("“" + Disc.BLACK + "”が３回パスしたため“" + Disc.WHITE + "”の勝ちです。");
        }
        else if (!reason.isDoublePass() && reason.isOverPassWhite()) {
            System.out.println("“" + Disc.WHITE + "”が３回パスしたため“" + Disc.BLACK + "”の勝ちです。");
        }
        else {
            Disc winner = getWinner();
            if (winner == null) {
                System.out.println("引き分けです。");
            }
            else {
                System.out.println("“" + winner + "”の勝ちです。");
            }
        }
    }

    private void sleep(long sleep) {
        try {
            Thread.sleep(sleep);
        }
        catch (InterruptedException e) {
            // through
        }
    }

}
