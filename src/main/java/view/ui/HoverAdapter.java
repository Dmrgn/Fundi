package view.ui;

import java.awt.*;
import java.awt.event.MouseAdapter;

import javax.swing.*;

public class HoverAdapter extends MouseAdapter {
    private final JButton backButton;

    public HoverAdapter(JButton backButton) {
        this.backButton = backButton;
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        backButton.setBackground(UiConstants.PRESSED_COLOUR);
        backButton.setForeground(Color.WHITE);
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent evt) {
        backButton.setBackground(UiConstants.PRIMARY_COLOUR);
        backButton.setForeground(Color.WHITE);
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent evt) {
        backButton.setBackground(UiConstants.PRESSED_BACKGROUND_COLOUR);
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent evt) {
        backButton.setBackground(UiConstants.PRESSED_COLOUR);
    }
}
