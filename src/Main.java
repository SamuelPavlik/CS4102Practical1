//from www.j a va2s .  c  o m
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

public class Main extends Frame implements MouseListener, MouseMotionListener {

    private BezierCurve bezierCurve;
    private int dragIndex = NOT_DRAGGING;

    private final static int NEIGHBORHOOD = 15;

    private final static int NOT_DRAGGING = -1;

    public static void main(String[] args) {
        (new Main()).setVisible(true);
    }

    public Main() {
        this.bezierCurve = new BezierCurve();
        setSize(500, 450);
        addMouseListener(this);
        addMouseMotionListener(this);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void paint(Graphics g) {
        // Set points
        Graphics2D g2 = (Graphics2D) g;
        GeneralPath polyline = new GeneralPath();
        GeneralPath tangent = new GeneralPath();

        if (!bezierCurve.getPoints().isEmpty()) {
            Vector start = bezierCurve.getPoint(0);
            polyline.moveTo (start.x, start.y);
//            tangent.moveTo(start.x, start.y);
//            Vector tanVector = bezierCurve.getTangent(0);
//            tangent.lineTo(start.x + tanVector.x, start.y + tanVector.y);

            for (double param = 0.01; param <= 1; param+=0.01) {
                param = round(param, 2);
                Vector current = bezierCurve.getPoint(param);
                polyline.lineTo(current.x, current.y);

                if (((int) (param * 100)) % 50 == 0) {
                    tangent.moveTo(current.x, current.y);
                    Vector tanVector = bezierCurve.getTangent(param);
                    tangent.lineTo(start.x + tanVector.x, start.y + tanVector.y);
                    g2.draw(tangent);
                    g2.draw(new Ellipse2D.Double(current.x, current.y, 10, 10));
                }
            }

//            g2.draw(tangent);
            g2.draw(polyline);

            // Draw points with their names
            int i = 1;
            for (Vector point : bezierCurve.getPoints()) {
                g2.draw(new Ellipse2D.Double(point.x, point.y, 10, 10));
                g2.drawString("p" + i++, ((float) point.x), ((float) point.y));
            }
        }

    }

    public void mousePressed(MouseEvent e) {
        bezierCurve.addPoint(new Vector(e.getX(), e.getY()));
        repaint();
//        dragIndex = NOT_DRAGGING;
//        int minDistance = Integer.MAX_VALUE;
//        int indexOfClosestPoint = -1;
//        for (int i = 0; i < 4; i++) {
//            int deltaX = xs[i] - e.getX();
//            int deltaY = ys[i] - e.getY();
//            int distance = (int) (Math.sqrt(deltaX * deltaX + deltaY * deltaY));
//            if (distance < minDistance) {
//                minDistance = distance;
//                indexOfClosestPoint = i;
//            }
//        }
//        if (minDistance > NEIGHBORHOOD)
//            return;
//
//        dragIndex = indexOfClosestPoint;
    }

    public void mouseReleased(MouseEvent e) {
//        if (dragIndex == NOT_DRAGGING)
//            return;
//        xs[dragIndex] = e.getX();
//        ys[dragIndex] = e.getY();
//        dragIndex = NOT_DRAGGING;
//        repaint();
    }

    public void mouseDragged(MouseEvent e) {
//        if (dragIndex == NOT_DRAGGING)
//            return;
//
//        xs[dragIndex] = e.getX();
//        ys[dragIndex] = e.getY();
//        repaint();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}