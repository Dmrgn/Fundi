package view.ui;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableModel;
import java.awt.*;

public final class TableFactory {

    public static final int ROW_HEIGHT = 28;
    public static final int ROW_MARGIN = 4;
    public static final Dimension INTERCELL_SPACING = new Dimension(8, 4);
    public static final MatteBorder MATTE_BORDER = BorderFactory.createMatteBorder(0, 0,
            1, 0, Color.GRAY);
    public static final Dimension PREFERRED_SIZE = new Dimension(600, 300);

    private TableFactory() {

    }

    /**
     * Create a styled table.
     * @param model The table model
     * @return The table
     */
    public static JScrollPane createStyledTable(TableModel model) {
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(ROW_HEIGHT);
        table.setFont(new Font(LabelFactory.FONT, Font.PLAIN, LabelFactory.FORM_SIZE));
        table.setGridColor(Color.GRAY);
        table.setShowGrid(true);
        table.setSelectionBackground(Color.WHITE);
        table.setSelectionForeground(Color.BLACK);
        table.setRowMargin(ROW_MARGIN);
        table.setIntercellSpacing(INTERCELL_SPACING);

        table.getTableHeader().setFont(new Font(LabelFactory.FONT, Font.BOLD, LabelFactory.FORM_SIZE));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setBorder(MATTE_BORDER);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setPreferredSize(PREFERRED_SIZE);
        scrollPane.setMaximumSize(PREFERRED_SIZE);

        return scrollPane;
    }
}
