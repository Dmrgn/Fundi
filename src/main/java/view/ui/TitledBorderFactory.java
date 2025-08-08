package view.ui;

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
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED), title);
        border.setTitleFont(UiConstants.Fonts.SMALL);
        border.setTitleColor(UiConstants.Colors.ON_PRIMARY);
        return border;
    }
}
