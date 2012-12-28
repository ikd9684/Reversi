package jp.co.excite_software.s_ikeda.reversi;

public class ViolationException extends Exception {

    private static final long serialVersionUID = 1L;

    public ViolationException() {
    }

    public ViolationException(String message) {
        super(message);
    }

}
