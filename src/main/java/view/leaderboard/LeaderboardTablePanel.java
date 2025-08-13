package view.leaderboard;

import entity.LeaderboardEntry;
import view.ui.UiConstants;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LeaderboardTablePanel extends JPanel {
    private final DefaultTableModel tableModel;
    private final JTable table;

    public LeaderboardTablePanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
        String[] columnNames = { "Rank", "Username", "Portfolio", "Total Value" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(UiConstants.Fonts.BODY);
        table.setRowHeight(32);
        table.setBackground(UiConstants.Colors.CANVAS_BG);
        table.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        table.setSelectionBackground(new Color(230, 240, 250));
        table.setSelectionForeground(UiConstants.Colors.TEXT_PRIMARY);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setFillsViewportHeight(true);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                if (c instanceof JLabel label)
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                if (!isSelected) {
                    Color bg;
                    if (row == 0)
                        bg = new Color(255, 249, 196);
                    else if (row == 1)
                        bg = new Color(236, 239, 241);
                    else if (row == 2)
                        bg = new Color(255, 243, 224);
                    else
                        bg = (row % 2 == 0) ? new Color(250, 250, 250) : new Color(242, 246, 252);
                    c.setBackground(bg);
                }
                return c;
            }
        });
        table.getTableHeader().setFont(UiConstants.Fonts.BUTTON);
        table.getTableHeader().setBackground(UiConstants.Colors.PRIMARY);
        table.getTableHeader().setForeground(UiConstants.Colors.ON_PRIMARY);
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBackground(UiConstants.Colors.CANVAS_BG);
        tableScrollPane.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1));
        add(tableScrollPane, BorderLayout.CENTER);
    }

    public void setEntries(List<LeaderboardEntry> entries, java.util.function.Function<Double, String> valueFormatter) {
        tableModel.setRowCount(0);
        if (entries != null && !entries.isEmpty()) {
            for (LeaderboardEntry entry : entries) {
                Object[] rowData = {
                        entry.getRank(),
                        entry.getUsername(),
                        entry.getPortfolioName(),
                        valueFormatter.apply(entry.getTotalValue())
                };
                tableModel.addRow(rowData);
            }
        }
    }
}
