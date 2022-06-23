package com.vngrs.booking.enums;

public enum AppointmentState{
 ACTIVE("A"), CANCELLED("C");

    private final String name;

    private AppointmentState(String s){
        name = s;
    }

    public String getValue(){
        return name;
    }
}