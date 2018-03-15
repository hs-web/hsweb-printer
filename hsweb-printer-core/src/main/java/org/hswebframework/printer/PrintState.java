package org.hswebframework.printer;

/**
 * @author zhouhao
 * @since 1.0
 */
public enum PrintState {
    ok(0, "正常"),
    paperError(40, "纸张问题"),
    busy(200, "打印机忙");

    private final int code;

    private final String text;

    PrintState(int code, String text) {
        this.code = code;
        this.text = text;
    }
}
