package view.ui.icons;

import javax.swing.Icon;
import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * Lightweight, LAF-agnostic bell icon drawn with Java2D so it renders on all platforms
 * without relying on emoji fonts.
 */
public class BellIcon implements Icon {
    private final int size;
    private final Color color;

    public BellIcon(int size, Color color) {
        this.size = Math.max(12, size);
        this.color = color == null ? Color.BLACK : color;
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            float stroke = Math.max(1f, size * 0.09f);
            g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            int w = size;
            int h = size;
            int cx = x + w / 2;
            int topY = y + Math.round(h * 0.18f);
            int bottomY = y + Math.round(h * 0.75f);
            int halfW = Math.round(w * 0.34f);

            // Bell outline (symmetric path)
            GeneralPath p = new GeneralPath();
            p.moveTo(cx, topY);
            p.curveTo(cx - halfW, topY + h * 0.15, cx - halfW, topY + h * 0.45, cx - halfW, bottomY);
            p.lineTo(cx + halfW, bottomY);
            p.curveTo(cx + halfW, topY + h * 0.45, cx + halfW, topY + h * 0.15, cx, topY);
            g2.draw(p);

            // Base rim
            g2.drawLine(cx - halfW, bottomY, cx + halfW, bottomY);

            // Clapper
            int r = Math.max(2, Math.round(size * 0.12f));
            int cy = bottomY + Math.round(size * 0.10f);
            g2.fillOval(cx - r, cy - r, r * 2, r * 2);
        } finally {
            g2.dispose();
        }
    }
}
