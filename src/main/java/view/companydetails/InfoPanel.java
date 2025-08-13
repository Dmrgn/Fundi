package view.companydetails;

import java.awt.Component;
import java.awt.Dimension;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entity.CompanyDetails;
import view.ui.UiConstants;

public class InfoPanel extends JPanel {
    private static final int TWENTY = 20;
    private static final int TEN = 10;
    private static final int MILLION = 1_000_000;

    public InfoPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public void setData(CompanyDetails details) {
        removeAll();

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        add(row("Industry:", details.getFinnhubIndustry()));
        add(row("Country:", details.getCountry()));
        add(row("Currency:", details.getCurrency()));
        add(row("IPO Date:", details.getIpoDate()));

        if (details.getMarketCapitalization() > 0) {
            add(row("Market Cap:", currencyFormat.format(details.getMarketCapitalization() * MILLION)));
        }
        if (details.getShareOutstanding() > 0) {
            add(row("Shares Outstanding:", numberFormat.format(details.getShareOutstanding() * MILLION)));
        }
        if (!details.getPhone().equals("N/A") && !details.getPhone().isEmpty()) {
            add(row("Phone:", details.getPhone()));
        }
        if (!details.getWebUrl().equals("N/A") && !details.getWebUrl().isEmpty()) {
            add(row("Website:", details.getWebUrl()));
        }
        add(Box.createVerticalStrut(TWENTY));
        revalidate();
        repaint();
    }

    private JPanel row(String label, String value) {
        if (value == null || value.trim().isEmpty() || value.equals("N/A")) {
            // Return a fully transparent, zero-size panel so BoxLayout doesn't paint a
            // white bar
            JPanel empty = new JPanel();
            empty.setOpaque(false);
            empty.setMaximumSize(new Dimension(0, 0));
            empty.setPreferredSize(new Dimension(0, 0));
            empty.setMinimumSize(new Dimension(0, 0));
            return empty;
        }
        JPanel r = new JPanel();
        r.setLayout(new BoxLayout(r, BoxLayout.X_AXIS));
        r.setOpaque(false);
        r.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(UiConstants.Fonts.SMALL);
        labelComponent.setForeground(UiConstants.Colors.TEXT_MUTED);
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(UiConstants.Fonts.BODY);
        valueComponent.setForeground(UiConstants.Colors.SURFACE_BG);

        r.add(Box.createHorizontalGlue());
        r.add(labelComponent);
        r.add(Box.createHorizontalStrut(TEN));
        r.add(valueComponent);
        r.add(Box.createHorizontalGlue());
        return r;
    }
}
