package view.ui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

/**
 * A centralized class for UI styling constants.
 */
public final class UiConstants {

    // Backward-compatible legacy constants (will be mapped to new nested classes)
    // ------------------ COLORS ------------------
    public static final Color PRIMARY_COLOUR = new Color(30, 60, 120);
    public static final Color SECONDARY_COLOUR = new Color(10, 30, 60);
    public static final Color PRESSED_COLOUR = new Color(40, 70, 130);
    public static final Color PRESSED_BACKGROUND_COLOUR = new Color(25, 50, 100);
    public static final Color TRANSLUCENT_BACKGROUND = new Color(255, 255, 255, 30);
    public static final Color TRANSLUCENT_BORDER = new Color(255, 255, 255, 100);

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
    public static final Dimension PREFERRED_BIG_CONTAINER_DIM = new Dimension(400, 200);
    public static final Dimension BUTTON_PANEL_DIM = new Dimension(400, 100);
    public static final Dimension PREFERRED_MINI_BUTTON_DIM = new Dimension(40, 40);
    public static final int ICON_DIM = 32;

    // ------------------ INSETS ------------------
    public static final Insets INSETS = new Insets(10, 10, 10, 10);
    public static final int INSET_SCALING = 3;

    // ------------------ FONTS ------------------
    private static final String FONT_FAMILY = "Sans Serif";
    private static final int SMALL_SIZE = 12;
    private static final int FORM_SIZE = 14;
    private static final int BUTTON_SIZE = 14;
    private static final int NORMAL_SIZE = 18;
    private static final int HEADING_SIZE = 22;
    private static final int TITLE_SIZE = 36;

    // ------------------ DIM VALS ------------------
    private static final int HEIGHT = 30;
    private static final int FORM_WIDTH = 100;
    private static final int SINGLE_WIDTH = 180;
    private static final int SINGLE_PANEL_WIDTH = 300;

    // ------------------ FONT TYPES ------------------
    public static final Font TITLE_FONT = new Font(FONT_FAMILY, Font.BOLD, TITLE_SIZE);
    public static final Font NORMAL_FONT = new Font(FONT_FAMILY, Font.PLAIN, NORMAL_SIZE);
    public static final Font SMALL_FONT = new Font(FONT_FAMILY, Font.PLAIN, SMALL_SIZE);
    public static final Font FORM_FONT = new Font(FONT_FAMILY, Font.PLAIN, FORM_SIZE);
    public static final Font BUTTON_FONT = new Font(FONT_FAMILY, Font.BOLD, BUTTON_SIZE);
    public static final Font LABEL_FONT = new Font(FONT_FAMILY, Font.PLAIN, NORMAL_SIZE);
    public static final Font HEADING_FONT = new Font(FONT_FAMILY, Font.BOLD, HEADING_SIZE);

    // ------------------ DIMENSIONS ------------------
    public static final Dimension FORM_DIM = new Dimension(FORM_WIDTH, HEIGHT);
    public static final Dimension SINGLE_DIM = new Dimension(SINGLE_WIDTH, HEIGHT);
    public static final Dimension SINGLE_PANEL_DIM = new Dimension(SINGLE_PANEL_WIDTH, HEIGHT);
    public static final Dimension PREFERRED_COMPONENT_DIM = new Dimension(SINGLE_WIDTH, HEIGHT);
    public static final Dimension PREFERRED_LABEL_DIM = new Dimension(100, HEIGHT);
    public static final Dimension PREFERRED_CONTAINER_DIM = new Dimension(300, HEIGHT);

    // ------------------ COMPONENT SPACING ------------------
    private static final int FORM_GAP = 5;
    private static final int BIG_HORIZONTAL_GAP = 15;
    private static final int SMALL_VERTICAL_GAP = 5;
    private static final int MEDIUM_VERTICAL_GAP = 10;
    private static final int BIG_VERTICAL_GAP = 20;

    // New centralized nested classes for modularity & no magic numbers
    public static final class Colors {
        public static final Color CANVAS_BG = new Color(245, 245, 245);
        public static final Color SURFACE_BG = new Color(250, 250, 250);
        public static final Color GRIDLINE_LIGHT = new Color(230, 230, 230);
        public static final Color GRIDLINE_LIGHTER = new Color(240, 240, 240);
        public static final Color TEXT_PRIMARY = Color.DARK_GRAY;
        public static final Color TEXT_MUTED = new Color(120, 120, 120);
        public static final Color SUCCESS = new Color(34, 139, 34);
        public static final Color DANGER = new Color(200, 60, 60);
        public static final Color PRIMARY = PRIMARY_COLOUR;
        public static final Color SECONDARY = SECONDARY_COLOUR;
        // Text color to use on primary/secondary dark areas
        public static final Color ON_PRIMARY = Color.WHITE;
        // Common muted border color
        public static final Color BORDER_MUTED = new Color(220, 220, 220);
        // Badge background (defaults to DANGER)
        public static final Color BADGE_BG = DANGER;
    }

    // Section and feature-specific palette
    public static final class Palette {
        public static final Color SECTION_INFO = new Color(138, 43, 226); // BlueViolet
        public static final Color SECTION_METRICS = new Color(255, 140, 0); // DarkOrange
        public static final Color SECTION_PEERS = new Color(30, 144, 255); // DodgerBlue
        public static final Color SECTION_DEFAULT = new Color(100, 149, 237); // CornflowerBlue
    }

    public static final class Fonts {
        public static final Font TITLE = TITLE_FONT;
        public static final Font HEADING = HEADING_FONT;
        public static final Font BODY = NORMAL_FONT;
        public static final Font SMALL = SMALL_FONT;
        public static final Font BUTTON = BUTTON_FONT;
        public static final Font FORM = FORM_FONT;
        public static final Font LABEL = LABEL_FONT;
    }

    public static final class Spacing {
        public static final int XS = 4;
        public static final int SM = 8;
        public static final int MD = 12;
        public static final int LG = 16;
        public static final int XL = 24;
        public static final int XXL = 32;

        public static final Insets CONTENT_INSETS = new Insets(MD, MD, MD, MD);
        public static final Insets SECTION_INSETS = new Insets(LG, LG, LG, LG);
    }

    public static final class Sizes {
        public static final Dimension WINDOW_DEFAULT = DEFAULT_WINDOW_DIM;
        public static final Dimension TABLE_PREFERRED = PREFERRED_TABLE_DIM;
        public static final Dimension CHART_PANEL = new Dimension(520, 160);
        public static final Dimension CHART_PANEL_WIDE = new Dimension(520, 180);
        public static final Dimension CARD = new Dimension(520, 180);
        public static final int ICON = ICON_DIM;
        public static final int ROW_HEIGHT_DEFAULT = ROW_HEIGHT;
    }

    private UiConstants() {
    }

    /**
     * Create form gap.
     * 
     * @return Component
     */
    public static Component formGap() {
        return Box.createHorizontalStrut(FORM_GAP);
    }

    /**
     * Create big horizontal gap.
     * 
     * @return Component
     */
    public static Component bigHorizontalGap() {
        return Box.createHorizontalStrut(BIG_HORIZONTAL_GAP);
    }

    /**
     * Create small vertical strut.
     * 
     * @return Component
     */
    public static Component smallVerticalGap() {
        return Box.createHorizontalStrut(SMALL_VERTICAL_GAP);
    }

    /**
     * Create medium vertical strut.
     * 
     * @return Component
     */
    public static Component mediumVerticalGap() {
        return Box.createHorizontalStrut(MEDIUM_VERTICAL_GAP);
    }

    /**
     * Create big vertical strut.
     * 
     * @return Component
     */
    public static Component bigVerticalGap() {
        return Box.createHorizontalStrut(BIG_VERTICAL_GAP);
    }
}
