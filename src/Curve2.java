import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.CubicCurve2D;

public class Curve2 extends Frame {

    // Construct frame
    public Curve2() {
        super("First Bezier Curve");
        setSize(600, 600);
//        center();
        setVisible(true);
    }

    public void paint(Graphics g) {
        // Set points
        Graphics2D g2 = (Graphics2D) g;
        Vector p1 = new Vector(100, 100);
        Vector p2 = new Vector(200, 100);
        Vector p3 = new Vector(100, 300);
        Vector p4 = new Vector(300, 200);
        Vector p5 = new Vector(300, 300);
        BezierCurve bezierCurve = new BezierCurve(p1, p2, p3, p4, p5);
        GeneralPath polyline = new GeneralPath();

        Vector start = bezierCurve.getPoint(0);
        polyline.moveTo (start.x, start.y);

        for (double index = 0.01; index <= 1; index+=0.1) {
            index = round(index, 2);
            Vector current = bezierCurve.getPoint(index);
            polyline.lineTo(current.x, current.y);
            System.out.println(current.x);
            System.out.println(current.y);
            System.out.println();
        }

        g2.draw(polyline);

        // Draw points with their names
        int i = 1;
        for (Vector point : bezierCurve.getPoints()) {
            g2.draw(new Ellipse2D.Double(point.x, point.y, 10, 10));
            g2.drawString("p" + i++, ((float) point.x), ((float) point.y));
        }
    }

    public static void main(String[] args) {
        new Curve2();
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}