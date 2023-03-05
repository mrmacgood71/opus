package it.macgood.opus.exception;

public enum HttpCodes {

    OK(200),
    FORBIDDEN(403);

    private int code;

    HttpCodes(int code) {
        this.code = code;
    }

    public int getCode(){ return code;}


}
