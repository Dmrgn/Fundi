package view.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

public interface Theme {
    // Colors
    Color primary();
    Color secondary();
    Color surface();
    Color canvas();
    Color textPrimary();
    Color textMuted();
    Color success();
    Color danger();
    Color gridline();
    Color gridlineLight();

    // Typography
    Font title();
    Font heading();
    Font body();
    Font button();
    Font small();

    // Spacing
    Insets contentInsets();
    Insets sectionInsets();

    // Sizes
    Dimension windowDefault();
    Dimension card();
    Dimension chartPanel();
}
