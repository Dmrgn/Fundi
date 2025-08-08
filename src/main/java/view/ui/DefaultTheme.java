package view.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

public class DefaultTheme implements Theme {
    @Override
    public Color primary() {
        return UiConstants.Colors.PRIMARY;
    }

    @Override
    public Color secondary() {
        return UiConstants.Colors.SECONDARY;
    }

    @Override
    public Color surface() {
        return UiConstants.Colors.SURFACE_BG;
    }

    @Override
    public Color canvas() {
        return UiConstants.Colors.CANVAS_BG;
    }

    @Override
    public Color textPrimary() {
        return UiConstants.Colors.TEXT_PRIMARY;
    }

    @Override
    public Color textMuted() {
        return UiConstants.Colors.TEXT_MUTED;
    }

    @Override
    public Color success() {
        return UiConstants.Colors.SUCCESS;
    }

    @Override
    public Color danger() {
        return UiConstants.Colors.DANGER;
    }

    @Override
    public Color gridline() {
        return UiConstants.Colors.GRIDLINE_LIGHT;
    }

    @Override
    public Color gridlineLight() {
        return UiConstants.Colors.GRIDLINE_LIGHTER;
    }

    @Override
    public Font title() {
        return UiConstants.Fonts.TITLE;
    }

    @Override
    public Font heading() {
        return UiConstants.Fonts.HEADING;
    }

    @Override
    public Font body() {
        return UiConstants.Fonts.BODY;
    }

    @Override
    public Font button() {
        return UiConstants.Fonts.BUTTON;
    }

    @Override
    public Font small() {
        return UiConstants.Fonts.SMALL;
    }

    @Override
    public Insets contentInsets() {
        return UiConstants.Spacing.CONTENT_INSETS;
    }

    @Override
    public Insets sectionInsets() {
        return UiConstants.Spacing.SECTION_INSETS;
    }

    @Override
    public Dimension windowDefault() {
        return UiConstants.Sizes.WINDOW_DEFAULT;
    }

    @Override
    public Dimension card() {
        return UiConstants.Sizes.CARD;
    }

    @Override
    public Dimension chartPanel() {
        return UiConstants.Sizes.CHART_PANEL;
    }
}
