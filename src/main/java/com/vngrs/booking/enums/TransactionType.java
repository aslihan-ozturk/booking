package com.vngrs.booking.enums;

public enum TransactionType {
    SAVE_APPOINTMENT("S"), CANCEL_APPOINTMENT("C");

    private final String name;

    private TransactionType(String s){
        name = s;
    }

    public String getValue(){
        return name;
    }
}
