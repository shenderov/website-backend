package me.shenderov.website.exceptions;

public class RecaptchaValidationException extends RuntimeException {

    public RecaptchaValidationException(String message) {
        super(message);
    }

}
