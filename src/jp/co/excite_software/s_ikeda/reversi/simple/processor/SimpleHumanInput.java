package jp.co.excite_software.s_ikeda.reversi.simple.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jp.co.excite_software.s_ikeda.reversi.Processor;
import jp.co.excite_software.s_ikeda.reversi.Rule.Disc;

public class SimpleHumanInput extends Processor {

    @Override
    public String getProcessorName() {
        return "ヒト";
    }

    @Override
    public String getAuthor() {
        return "s-ikeda@excite-software.co.jp";
    }

    @Override
    public String getDescription() {
        return "標準入力からの入力です。";
    }

    @Override
    public Move doMove(Disc[][] status, Disc myDisc) {
        int x = getXY("x=");
        int y = getXY("y=");
        return new Move(x, y);
    }

    private int getXY(String message) {
        do {
            System.out.print(message);
            try {
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                return Integer.parseInt(br.readLine());
            }
            catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
            catch (NumberFormatException e) {
                continue;
            }
        }
        while (true);
    }

}
