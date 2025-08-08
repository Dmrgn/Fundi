package view.ui;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableModel;
import java.awt.*;

public final class TableFactory {

    public static final int ROW_HEIGHT = UiConstants.ROW_HEIGHT;
    public static final int ROW_MARGIN = UiConstants.ROW_MARGIN;
    public static final Dimension INTERCELL_SPACING = UiConstants.INTERCELL_SPACING_DIM;
    public static final MatteBorder MATTE_BORDER = BorderFactory.createMatteBorder(0, 0,
            1, 0, UiConstants.Colors.BORDER_MUTED);
    public static final Dimension PREFERRED_SIZE = UiConstants.PREFERRED_TABLE_DIM;

    private TableFactory() {

    }

    /**
     * Create a styled table.
     * 
     * @param model The table model
     * @return The table
     */
    public static JScrollPane createStyledTable(TableModel model) {
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(ROW_HEIGHT);
        table.setFont(UiConstants.Fonts.FORM);
        table.setGridColor(UiConstants.Colors.BORDER_MUTED);
        table.setShowGrid(true);
        table.setSelectionBackground(Color.WHITE);
        table.setSelectionForeground(UiConstants.Colors.TEXT_PRIMARY);
        table.setRowMargin(ROW_MARGIN);
        table.setIntercellSpacing(INTERCELL_SPACING);

        table.getTableHeader().setFont(UiConstants.Fonts.FORM);
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setBorder(MATTE_BORDER);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setPreferredSize(PREFERRED_SIZE);
        scrollPane.setMaximumSize(PREFERRED_SIZE);

        return scrollPane;
    }
}
