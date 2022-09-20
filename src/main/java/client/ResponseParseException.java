package client;

public class ResponseParseException extends RuntimeException{
    ResponseParseException(Throwable ex) {
        super(ex);
    }
}
