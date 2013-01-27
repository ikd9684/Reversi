package jp.co.excite_software.s_ikeda.reversi.standard;

import jp.co.excite_software.s_ikeda.reversi.Board;

public class StandardBoard extends Board {

  public StandardBoard() {
    super(new StandardRule(), StandardRule.getDefaultStatus());

  }
}
