package com.example.demo.exception;

public class CustomExceptionMessage {
    private String AccessDeniedException;

    public CustomExceptionMessage(String accessDeniedException) {
        AccessDeniedException = accessDeniedException;
    }

    public String getAccessDeniedException() {
        return AccessDeniedException;
    }

    public void setAccessDeniedException(String accessDeniedException) {
        AccessDeniedException = accessDeniedException;
    }
}
