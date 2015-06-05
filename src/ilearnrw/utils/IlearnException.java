package ilearnrw.utils;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
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
