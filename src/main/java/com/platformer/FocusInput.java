package com.platformer;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FocusInput extends FocusAdapter {

    private final KeyInput input;

    public FocusInput(KeyInput input){
        this.input=input;
    }

    @Override public void focusLost(FocusEvent e) {
        input.clearKeys();
    }
}
