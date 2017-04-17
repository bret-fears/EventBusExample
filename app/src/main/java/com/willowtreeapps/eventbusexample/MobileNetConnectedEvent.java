package com.willowtreeapps.eventbusexample;

public class MobileNetConnectedEvent {

    public final String detailedState;

    public MobileNetConnectedEvent(String detailedState) {
        this.detailedState = detailedState;
    }

    public String getDetailedState() {
        return detailedState;
    }
}
