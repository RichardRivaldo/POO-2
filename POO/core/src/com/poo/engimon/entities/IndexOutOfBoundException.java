package com.poo.engimon.entities;

public class IndexOutOfBoundException extends Exception {
    private String errorMessage;

    IndexOutOfBoundException() {
        this.errorMessage = "Index Out of Bound!";
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}

