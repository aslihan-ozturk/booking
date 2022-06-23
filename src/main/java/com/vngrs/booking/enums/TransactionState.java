package com.vngrs.booking.enums;

public enum TransactionState {
    ACTIVE("A"), RETURNED("R");

    private final String name;

    private TransactionState(String s){
        name = s;
    }

    public String getValue(){
        return name;
    }
}
