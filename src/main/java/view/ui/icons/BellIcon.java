package view.ui.icons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;

import javax.swing.Icon;

/**
 * Lightweight, LAF-agnostic bell icon drawn with Java2D so it renders on all
 * platforms
 * without relying on emoji fonts.
 */
public class BellIcon implements Icon {
    private static final float MIN_STROKE = 1f;
    private static final float STROKE_FACTOR = 0.09f;
    private static final float TOP_Y_FACTOR = 0.18f;
    private static final float BOTTOM_Y_FACTOR = 0.75f;
    private static final float HALF_WIDTH_FACTOR = 0.34f;
    private static final double CURVE_CTRL_FACTOR_1 = 0.15;
    private static final double CURVE_CTRL_FACTOR_2 = 0.45;
    private static final float CLAPPER_RADIUS_FACTOR = 0.12f;
    private static final float CLAPPER_OFFSET_FACTOR = 0.10f;
    private static final int NUMBER_TWO = 2;
    private static final int NUMBER_TWELVE = 12;

    private final int size;
    private final Color color;

    public BellIcon(int size, Color color) {
        this.size = Math.max(NUMBER_TWELVE, size);
        if (color == null) {
            this.color = Color.BLACK;
        }
        else {
            this.color = color;
        }
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
            float stroke = Math.max(MIN_STROKE, size * STROKE_FACTOR);
            g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            int w = size;
            int h = size;
            int cx = x + w / NUMBER_TWO;
            int topY = y + Math.round(h * TOP_Y_FACTOR);
            int bottomY = y + Math.round(h * BOTTOM_Y_FACTOR);
            int halfW = Math.round(w * HALF_WIDTH_FACTOR);

            // Bell outline (symmetric path)
            GeneralPath p = new GeneralPath();
            p.moveTo(cx, topY);
            p.curveTo(cx - halfW, topY + h * CURVE_CTRL_FACTOR_1, cx - halfW,
                    topY + h * CURVE_CTRL_FACTOR_2, cx - halfW, bottomY);
            p.lineTo(cx + halfW, bottomY);
            p.curveTo(cx + halfW, topY + h * CURVE_CTRL_FACTOR_2, cx + halfW,
                    topY + h * CURVE_CTRL_FACTOR_1, cx, topY);
            g2.draw(p);

            // Base rim
            g2.drawLine(cx - halfW, bottomY, cx + halfW, bottomY);

            // Clapper
            int r = Math.max(NUMBER_TWO, Math.round(size * CLAPPER_RADIUS_FACTOR));
            int cy = bottomY + Math.round(size * CLAPPER_OFFSET_FACTOR);
            g2.fillOval(cx - r, cy - r, r * NUMBER_TWO, r * NUMBER_TWO);
        }
        finally {
            g2.dispose();
        }
    }
}
