package ilearnrw.utils;

public class IlearnException extends Exception{
    public IlearnException(String message) {
        super(message);
    }
    public IlearnException(String message, Throwable throwable) {
        super(message, throwable);
    }
    public String getMessage(){
        return super.getMessage();
    }
}
