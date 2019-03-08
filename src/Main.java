//from www.j a va2s .  c  o m
import java.util.List;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.*;

public class Main extends Frame implements MouseListener, MouseMotionListener {
    private static final double ARROW_LENGTH = 10;
    private static final double ARROW_HALF_WIDTH = 5;

    private BezierCurve bezierCurve;
    private int dragIndex = NOT_DRAGGING;
    private final static int NOT_DRAGGING = -1;

    public static void main(String[] args) {
        (new Main()).setVisible(true);
    }

    private void drawArrowHead(Vector tangent, GeneralPath tangentLine) {
        Point2D point = tangentLine.getCurrentPoint();
        Vector perpVector = (new Vector(-tangent.y, tangent.x)).ofSize(ARROW_HALF_WIDTH);
        Vector arrowVector = tangent.ofSize(ARROW_LENGTH);
        Vector tip = new Vector(point.getX() + arrowVector.x, point.getY() + arrowVector.y);
        Vector leftCorner = new Vector(point.getX() + perpVector.x, point.getY() + perpVector.y);
        Vector rightCorner = new Vector(point.getX() - perpVector.x, point.getY() - perpVector.y);

        tangentLine.lineTo(leftCorner.x, leftCorner.y);
        tangentLine.lineTo(tip.x, tip.y);
        tangentLine.lineTo(rightCorner.x, rightCorner.y);
        tangentLine.lineTo(point.getX(), point.getY());
    }

    public Main() {
        this.bezierCurve = new BezierCurve();
        setSize(1000, 1000);
//        add(new Checkbox())
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
        GeneralPath tangentLine = new GeneralPath();

        if (!bezierCurve.getPoints().isEmpty()) {
            Vector start = bezierCurve.getPoint(0);
            polyline.moveTo (start.x, start.y);
            tangentLine.moveTo(start.x, start.y);
            Vector tanVector = bezierCurve.getCurvature(0);
            tangentLine.lineTo(start.x + tanVector.x, start.y + tanVector.y);

            for (double param = 0.01; param <= 1; param+=0.01) {
                param = round(param, 2);
                Vector current = bezierCurve.getPoint(param);
                polyline.lineTo(current.x, current.y);

//                if (((int) (param * 100)) % 10 == 0) {
////                    tanVector = bezierCurve.getTangent(param);
////                    tangentLine.moveTo(current.x - tanVector.x, current.y - tanVector.y);
////                    tangentLine.lineTo(current.x + tanVector.x, current.y + tanVector.y);
////                    drawArrowHead(tanVector, tangentLine);
////                    g2.draw(tangentLine);
//                    g2.draw(new Ellipse2D.Double(current.x, current.y, 10, 10));
//                }
            }

            List<Double> uniformPoints = bezierCurve.getUniformParams(10);
            for (double param : uniformPoints) {
                Vector current = bezierCurve.getPoint(param);
                tanVector = bezierCurve.getCurvature(param);
                tangentLine.moveTo(current.x - tanVector.x, current.y - tanVector.y);
                tangentLine.lineTo(current.x + tanVector.x, current.y + tanVector.y);
                drawArrowHead(tanVector, tangentLine);
                g2.draw(tangentLine);
                g2.draw(new Ellipse2D.Double(current.x, current.y, 10, 10));
            }

            g2.draw(tangentLine);
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

    public static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}