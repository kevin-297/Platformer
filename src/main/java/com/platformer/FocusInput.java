package com.platformer;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FocusInput extends FocusAdapter {
    @Override public void focusLost(FocusEvent e) {
        KeyInput.clearKeys();
    }
}
