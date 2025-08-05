package view.ui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

/**
 * A centralized class for UI styling constants.
 */
public final class UiConstants {

    // ------------------ COLORS ------------------
    public static final Color PRIMARY_COLOUR = new Color(30, 60, 120);
    public static final Color SECONDARY_COLOUR = new Color(10, 30, 60);
    public static final Color PRESSED_COLOUR = new Color(40, 70, 130);
    public static final Color PRESSED_BACKGROUND_COLOUR = new Color(25, 50, 100);

    // ------------------ BORDERS ------------------
    public static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(20, 20, 20, 20);
    public static final Border BUTTON_BORDER = new EmptyBorder(10, 20, 10, 20);
    public static final Border PANEL_BORDER = new EmptyBorder(10, 0, 10, 0);
    public static final MatteBorder BOTTOM_MATTE_BORDER = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY);
    public static final Border EMPTY_BUTTON_BORDER = BorderFactory.createEmptyBorder(8, 15, 8, 15);
    public static final Border LINE_BUTTON_BORDER = BorderFactory.createLineBorder(new Color(40, 70, 130), 1);
    public static final Border BUTTON_CONTAINER_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    public static final Border BADGE_BORDER = BorderFactory.createEmptyBorder(2, 6, 2, 6);

    // ------------------ LAYOUTS ------------------
    public static final BorderLayout BORDER_LAYOUT = new BorderLayout(10, 10);
    public static final FlowLayout PANEL_FLOW_LAYOUT = new FlowLayout(FlowLayout.CENTER, 5, 0);
    public static final FlowLayout BUTTON_LAYOUT = new FlowLayout(FlowLayout.LEFT, 15, 10);
    public static final GridLayout GRID_LAYOUT = new GridLayout(2, 2, 10, 10);

    // ------------------ TABLE FORMATTING ------------------
    public static final int NUM_COLUMNS = 30;
    public static final int ROW_HEIGHT = 28;
    public static final int ROW_MARGIN = 4;

    // ------------------ SCROLL SPEED -----------------
    public static final int SCROLL_UNIT_INCREMENT = 16;

    // ------------------ DIMENSIONS ------------------
    public static final Dimension DEFAULT_WINDOW_DIM = new Dimension(900, 600);
    public static final Dimension PREFERRED_TABLE_DIM = new Dimension(600, 300);
    public static final Dimension INTERCELL_SPACING_DIM = new Dimension(8, 4);
    public static final Dimension BUTTON_PANEL_DIM = new Dimension(400, 100);
    public static final Dimension PREFERRED_MINI_BUTTON_DIM = new Dimension(40, 40);
    public static final int ICON_DIM = 32;

    // ------------------ INSETS ------------------
    public static final Insets INSETS = new Insets(10, 10, 10, 10);
    public static final int INSET_SCALING = 3;

    // ------------------ FONTS ------------------
    private static final String FONT_FAMILY = "Sans Serif";
    private static final int TITLE_SIZE = 36;
    private static final int NORMAL_SIZE = 18;
    private static final int LABEL_SIZE = 12;
    private static final int FORM_SIZE = 14;
    private static final int STAT_SIZE = 16;

    // ------------------ DIM VALS ------------------
    private static final int HEIGHT = 30;
    private static final int FORM_WIDTH = 100;
    private static final int SINGLE_WIDTH = 180;
    private static final int SINGLE_PANEL_WIDTH = 300;

    // ------------------ FONT TYPES ------------------
    public static final Font TITLE_FONT = new Font(FONT_FAMILY, Font.BOLD, TITLE_SIZE);
    public static final Font NORMAL_FONT = new Font(FONT_FAMILY, Font.PLAIN, NORMAL_SIZE);
    public static final Font LABEL_FONT = new Font(FONT_FAMILY, Font.PLAIN, LABEL_SIZE);
    public static final Font FORM_FONT = new Font(FONT_FAMILY, Font.PLAIN, FORM_SIZE);
    public static final Font STAT_FONT = new Font(FONT_FAMILY, Font.BOLD, STAT_SIZE);

    // ------------------ DIMENSIONS ------------------
    public static final Dimension FORM_DIM = new Dimension(FORM_WIDTH, HEIGHT);
    public static final Dimension SINGLE_DIM = new Dimension(SINGLE_WIDTH, HEIGHT);
    public static final Dimension SINGLE_PANEL_DIM = new Dimension(SINGLE_PANEL_WIDTH, HEIGHT);

    // ------------------ COMPONENT SPACING ------------------
    private static final int FORM_GAP = 5;
    private static final int SMALL_VERTICAL_GAP = 5;
    private static final int MEDIUM_VERTICAL_GAP = 10;
    private static final int BIG_VERTICAL_GAP = 20;
    public static final Dimension PREFERRED_BUTTON_SIZE = new Dimension(180, 30);

    private UiConstants() {

    }

    /**
     * Create form gap.
     * @return Component
     */
    public static Component formGap() {
        return Box.createHorizontalStrut(FORM_GAP);
    }

    /**
     * Create small vertical strut.
     * @return Component
     */
    public static Component smallVerticalGap() {
        return Box.createHorizontalStrut(SMALL_VERTICAL_GAP);
    }

    /**
     * Create medium vertical strut.
     * @return Component
     */
    public static Component mediumVerticalGap() {
        return Box.createHorizontalStrut(MEDIUM_VERTICAL_GAP);
    }

    /**
     * Create big vertical strut.
     * @return Component
     */
    public static Component bigVerticalGap() {
        return Box.createHorizontalStrut(BIG_VERTICAL_GAP);
    }
}
