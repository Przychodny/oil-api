package com.example.oil_api.exception;

public class LackOfSufficientFunds extends RuntimeException {


    public LackOfSufficientFunds() {
        super("Lack of sufficient founds");
    }
}
