package org.plukh.fluffymeow;

import java.io.Serializable;

public class Test implements Serializable {
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
