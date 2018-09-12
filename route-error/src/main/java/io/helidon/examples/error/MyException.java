package io.helidon.examples.error;

/**
 * MyException
 *
 * @author biezhi
 * @date 2018/9/12
 */
public class MyException extends Exception {

    private int code;
    private String message;

    public MyException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
