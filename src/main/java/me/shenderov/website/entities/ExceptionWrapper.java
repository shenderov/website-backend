package me.shenderov.website.entities;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ExceptionWrapper {

    private Date timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
    private String path;

    public ExceptionWrapper() {
        this.timestamp = new Date();
    }

    public ExceptionWrapper(HttpStatus status, Exception exception, String message, String path) {
        this.timestamp = new Date();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.exception = exception.getClass().getSimpleName();
        this.message = message;
        this.path = path;
    }

    public ExceptionWrapper(HttpStatus status, Exception e, String path) {
        this.timestamp = new Date();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.exception = e.getClass().getSimpleName();
        this.message = e.getMessage();
        this.path = path;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ExceptionWrapper{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", exception='" + exception + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
