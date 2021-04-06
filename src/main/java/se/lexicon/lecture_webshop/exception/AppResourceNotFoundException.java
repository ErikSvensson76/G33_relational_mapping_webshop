package se.lexicon.lecture_webshop.exception;

public class AppResourceNotFoundException extends RuntimeException{
    public AppResourceNotFoundException() {
    }

    public AppResourceNotFoundException(String message) {
        super(message);
    }
}
