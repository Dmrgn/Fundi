package view.companydetails;

import java.awt.Component;
import java.awt.Cursor;

import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import interfaceadapter.company_details.CompanyDetailsController;
import view.ui.UiConstants;

public class PeersPanel extends JPanel {
    private static final int EIGHT = 8;
    private static final int TWELVE = 12;
    private static final int TWO = 2;

    private CompanyDetailsController controller;

    public PeersPanel(CompanyDetailsController controller) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        this.controller = controller;
        add(Box.createHorizontalGlue());
    }

    public void setPeers(List<String> peers) {
        removeAll();
        add(Box.createHorizontalGlue());
        for (int i = 0; i < peers.size(); i++) {
            String ticker = peers.get(i);
            JLabel peerLabel = new JLabel(ticker + " →");
            peerLabel.setFont(UiConstants.Fonts.BODY);
            peerLabel.setForeground(java.awt.Color.WHITE);
            peerLabel.setBackground(UiConstants.Colors.PRIMARY);
            peerLabel.setOpaque(true);
            peerLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UiConstants.Colors.SECONDARY.brighter(), TWO, true),
                    BorderFactory.createEmptyBorder(EIGHT, TWELVE, EIGHT, TWELVE)));
            peerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            peerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            peerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // Immediately navigate with fresh fetch
                    controller.execute(ticker, "company_details");
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    peerLabel.setBackground(UiConstants.PRESSED_COLOUR);
                    peerLabel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(UiConstants.Colors.PRIMARY.brighter(), TWO, true),
                            BorderFactory.createEmptyBorder(EIGHT, TWELVE, EIGHT, TWELVE)));
                    peerLabel.repaint();
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    peerLabel.setBackground(UiConstants.Colors.PRIMARY);
                    peerLabel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(UiConstants.Colors.SECONDARY.brighter(), TWO, true),
                            BorderFactory.createEmptyBorder(EIGHT, TWELVE, EIGHT, TWELVE)));
                    peerLabel.repaint();
                }
            });

            add(peerLabel);
            if (i < peers.size() - 1)
                add(Box.createHorizontalStrut(10));
        }
        add(Box.createHorizontalGlue());
        revalidate();
        repaint();
    }
}
