package com.willowtreeapps.eventbusexample.models;

public class RetrieveProductEvent {

    final long identifier;

    public RetrieveProductEvent(long identifier) {
        this.identifier = identifier;
    }

    public long getIdentifier() {
        return identifier;
    }
}
