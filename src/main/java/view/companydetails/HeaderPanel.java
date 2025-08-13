package view.companydetails;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entity.CompanyDetails;
import view.ui.UiConstants;

public class HeaderPanel extends JPanel {
    private static final int HEADER_MAX_HEIGHT = 140;
    private static final int TWO = 2;
    private static final int TWENTY = 20;
    private static final int FIVE = 5;
    private static final int THIRTY_TWO = 32;

    public HeaderPanel() {
        super(new BorderLayout());
        setOpaque(false);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, HEADER_MAX_HEIGHT));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.PRIMARY, 2, true),
                BorderFactory.createEmptyBorder(TWENTY, TWENTY, TWENTY, TWENTY)));
    }

    public void setData(CompanyDetails details) {
        removeAll();

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(details.getName());
        nameLabel.setFont(UiConstants.Fonts.TITLE);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel symbolLabel = new JLabel("(" + details.getSymbol() + ")");
        symbolLabel.setFont(UiConstants.Fonts.BODY);
        symbolLabel.setForeground(UiConstants.Colors.SURFACE_BG);
        symbolLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel exchangeLabel = new JLabel("📈 " + details.getExchange());
        exchangeLabel.setFont(UiConstants.Fonts.SMALL);
        exchangeLabel.setForeground(UiConstants.Colors.SURFACE_BG);
        exchangeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(FIVE));
        infoPanel.add(symbolLabel);
        infoPanel.add(Box.createVerticalStrut(FIVE));
        infoPanel.add(exchangeLabel);

        add(infoPanel, BorderLayout.WEST);

        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));
        pricePanel.setOpaque(false);

        JLabel priceTitle = new JLabel("Current Price");
        priceTitle.setFont(UiConstants.Fonts.FORM);
        priceTitle.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        priceTitle.setAlignmentX(Component.RIGHT_ALIGNMENT);

        String priceText = details.getCurrentPrice() > 0 ? String.format("$%.2f", details.getCurrentPrice()) : "N/A";
        JLabel priceLabel = new JLabel(priceText);
        priceLabel.setFont(new Font("Sans Serif", Font.BOLD, THIRTY_TWO));
        priceLabel.setForeground(UiConstants.Colors.SUCCESS);
        priceLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel currencyLabel = new JLabel(details.getCurrency());
        currencyLabel.setFont(UiConstants.Fonts.SMALL);
        currencyLabel.setForeground(UiConstants.Colors.TEXT_MUTED);
        currencyLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        pricePanel.add(priceTitle);
        pricePanel.add(Box.createVerticalStrut(TWO));
        pricePanel.add(priceLabel);
        pricePanel.add(Box.createVerticalStrut(TWO));
        pricePanel.add(currencyLabel);

        add(pricePanel, BorderLayout.EAST);
        revalidate();
        repaint();
    }
}
