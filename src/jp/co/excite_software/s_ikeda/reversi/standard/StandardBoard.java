package jp.co.excite_software.s_ikeda.reversi.standard;

import jp.co.excite_software.s_ikeda.reversi.Board;
import jp.co.excite_software.s_ikeda.reversi.Processor;

public class StandardBoard extends Board {

    /**  */
    private Processor procA;
    /**  */
    private Processor procB;

    public StandardBoard(Processor procA, Processor procB) {
        super(new StandardRule());

        if (procA == null || procB == null) {
            throw new NullPointerException("Processor は必ず設定してください。");
        }
        this.procA = procA;
        this.procB = procB;
    }

    @Override
    public void start() {
        // TODO 自動生成されたメソッド・スタブ
        
    }

}
