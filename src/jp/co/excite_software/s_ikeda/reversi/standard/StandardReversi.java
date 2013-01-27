package jp.co.excite_software.s_ikeda.reversi.standard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jp.co.excite_software.s_ikeda.reversi.Board;
import jp.co.excite_software.s_ikeda.reversi.Processor;
import jp.co.excite_software.s_ikeda.reversi.Processor.Move;
import jp.co.excite_software.s_ikeda.reversi.Rule.Disc;
import jp.co.excite_software.s_ikeda.reversi.ViolationException;

public class StandardReversi {

    /**  */
    private Board board;

    public StandardReversi() {
    }

    public void start() {
        board = new StandardBoard();

        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(System.in);
            br = new BufferedReader(isr);

            Processor procA = new RandomProcessor();
            Processor procB = new RandomProcessor();
            int passBlack, passWhite;
            passBlack = passWhite = 3;

            final int sleep = 100;
            do {
                System.out.println(board);
                Disc turn = board.getTurn();
                System.out.println(turn + "の番です。");

                Move move = null;
                int x, y;
                if (turn == Disc.BLACK) {
                    if (procA == null) {
                        x = getX(br);
                        y = getY(br);
                        move = new Move(x, y);
                    }
                    else {
                        move = procA.doMove(board);
                        sleep(sleep);
                    }

                    if (move.isPass()) {
                        passBlack--;
                    }
                }
                else {
                    move = procB.doMove(board);
                    sleep(sleep);

                    if (move.isPass()) {
                        passWhite--;
                    }
                }

                if (move.isPass()) {
                    System.out.println(board.getTurn() + " は パス しました。");
                    board.pass();
                }
                else {
                    x = move.getX();
                    y = move.getY();
                    System.out.println(board.getTurn() + ": " + x + ", " + y);

                    try {
                        board.setDisc(x - 1, y - 1);
                    }
                    catch (ViolationException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            while (!board.isFinish());

            System.out.println();
            System.out.println();
            System.out.println(board);

            if (board.isDoublePass()) {
                System.out.println("両者続けてパスしたため、試合続行不可と判断しました。");
            }

            System.out.println(Disc.BLACK + "=" + board.getCountBlack()
                    + " 対 " + board.getCountWhite() + "=" + Disc.WHITE);

            if (!board.isDoublePass() && passBlack == 0) {
                System.out.println("“" + Disc.BLACK + "”が３回パスしたため“" + Disc.WHITE + "”の勝ちです。");
            }
            else if (!board.isDoublePass() && passWhite == 0) {
                System.out.println("“" + Disc.WHITE + "”が３回パスしたため“" + Disc.BLACK + "”の勝ちです。");
            }
            else {
                Disc winner = board.getWinner();
                if (winner == null) {
                    System.out.println("引き分けです。");
                }
                else {
                    System.out.println("“" + board.getWinner() + "”の勝ちです。");
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (br != null) br.close();
                if (isr != null) isr.close(); 
            }
            catch (IOException e) {
                // through
            }
        }

    }

    private void sleep(int milliSecond) {

        try {
            Thread.sleep(milliSecond);
        }
        catch (InterruptedException e) {
            // through
        }
    }

    private int getX(BufferedReader br) throws IOException {
        do {
            System.out.println("X=");
            String strX = getStandardInput(br);
            try {
                return Integer.parseInt(strX);
            }
            catch (NumberFormatException e) {
                continue;
            }
        }
        while (true);
    }
    private int getY(BufferedReader br) throws IOException {
        do {
            System.out.println("Y=");
            String strY = getStandardInput(br);
            try {
                return Integer.parseInt(strY);
            }
            catch (NumberFormatException e) {
                continue;
            }
        }
        while (true);
    }

    private String getStandardInput(BufferedReader br) throws IOException {

        while (!br.ready()) {
            // wait
        }
        return br.readLine();
    }

    public static void main(String[] args) {

        StandardReversi reversi = new StandardReversi();
        reversi.start();
    }

}
