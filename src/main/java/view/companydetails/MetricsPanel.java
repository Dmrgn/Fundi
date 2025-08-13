package view.companydetails;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entity.CompanyDetails;
import view.ui.UiConstants;

public class MetricsPanel extends JPanel {
    private static final int FIFTEEN = 15;

    public MetricsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public void setData(CompanyDetails details) {
        removeAll();
        if (details.getPeRatio() > 0)
            add(row("P/E Ratio:", String.format("%.2f", details.getPeRatio())));
        if (details.getWeek52High() > 0)
            add(row("52-Week High:", String.format("$%.2f", details.getWeek52High())));
        if (details.getWeek52Low() > 0)
            add(row("52-Week Low:", String.format("$%.2f", details.getWeek52Low())));
        if (details.getBeta() > 0)
            add(row("Beta:", String.format("%.2f", details.getBeta())));
        add(Box.createVerticalStrut(FIFTEEN));
        revalidate();
        repaint();
    }

    private JPanel row(String label, String value) {
        JPanel r = new JPanel();
        r.setLayout(new BoxLayout(r, BoxLayout.X_AXIS));
        r.setOpaque(false);
        JLabel l = new JLabel(label);
        l.setFont(UiConstants.Fonts.SMALL);
        l.setForeground(UiConstants.Colors.TEXT_MUTED);
        JLabel v = new JLabel(value);
        v.setFont(UiConstants.Fonts.BODY);
        v.setForeground(UiConstants.Colors.SURFACE_BG);
        r.add(Box.createHorizontalGlue());
        r.add(l);
        r.add(Box.createHorizontalStrut(10));
        r.add(v);
        r.add(Box.createHorizontalGlue());
        return r;
    }
}
