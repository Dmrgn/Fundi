package view.ui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public final class TitledBorderFactory {
    private TitledBorderFactory() {

    }

    /**
     * Create a titled border.
     * @param title The text
     * @return The border
     */
    public static TitledBorder createLightTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), title);
        border.setTitleFont(UiConstants.SMALL_FONT);
        border.setTitleColor(Color.WHITE);
        return border;
    }
}
