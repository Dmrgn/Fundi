package view.util;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.ui.UiConstants;

/** Utility helpers for common panel constructions. */
public final class PanelFactory {
    private PanelFactory() {
    }

    public static JPanel createCanvasPanelWithInsets() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(UiConstants.Colors.CANVAS_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL));
        return mainPanel;
    }

    public static GridBagConstraints defaultGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.SM, UiConstants.Spacing.SM,
                UiConstants.Spacing.SM);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        return gbc;
    }

    public static JPanel createHeader(String titleText) {
        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setFont(UiConstants.Fonts.TITLE);
        titleLabel.setForeground(UiConstants.Colors.ON_PRIMARY);
        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConstants.Spacing.LG, UiConstants.Spacing.SM));
        headerLeft.setOpaque(false);
        headerLeft.add(titleLabel);
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(headerLeft, BorderLayout.WEST);
        return header;
    }
}
