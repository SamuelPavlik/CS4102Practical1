package app;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.List;

public class CurveDraw {
    public static final double POINT_HALF_WIDTH = 5;
    private static final double ARROW_LENGTH = 10;
    private static final double ARROW_HALF_WIDTH = 5;
    private static final int PRECISION = 2;
    private static final double STEP_SIZE = 1.0 / Math.pow(10, PRECISION);
    private final static int NOT_DRAGGING = -1;

    private Graphics2D g2;
    private BezierCurve curve;
    private int dragIndex = NOT_DRAGGING;

    public CurveDraw(BezierCurve curve) {
        this.g2 = null;
        this.curve = curve;
    }

    /**
     * get number of uniform samples as the user input and display the samples on the curve with tangent and
     * curvature vectors if specified
     */
    public void initUniformPoints(String numOfSamplesText, boolean tangent, boolean curvature, boolean secondDeriv) {
        //get number of uniform samples as the user input
        int numOfSamples = 0;
        try {
            numOfSamples = Integer.parseInt(numOfSamplesText);
        } catch (NumberFormatException e) {
            numOfSamples = 0;
        }
        if (numOfSamples < 0) numOfSamples = 0;

        Vector curvVector;
        Vector tanVector;
        Vector derivVector;
        List<Double> uniformPoints = curve.getUniformParams(numOfSamples);
        GeneralPath tangentLine = new GeneralPath();
        GeneralPath curvLine = new GeneralPath();
        GeneralPath derivLine = new GeneralPath();

        for (double param : uniformPoints) {
            Vector current = curve.getPoint(param);

            //draw small circles around the sampled points
            g2.draw(new Ellipse2D.Double(current.x - POINT_HALF_WIDTH,
                    current.y - POINT_HALF_WIDTH,
                    2*POINT_HALF_WIDTH,
                    2*POINT_HALF_WIDTH));

            if (tangent) {
                //draw tangent vectors
                tanVector = curve.getTangent(param);
                tangentLine.moveTo(current.x, current.y);
                tangentLine.lineTo(current.x + tanVector.x, current.y + tanVector.y);
                drawArrowHead(tanVector, tangentLine);
            }

            if (curvature) {
                //draw curvature vectors
                curvVector = curve.getCurvature(param);
                curvLine.moveTo(current.x, current.y);
                curvLine.lineTo(current.x + curvVector.x, current.y + curvVector.y);
                drawArrowHead(curvVector, curvLine);
            }

            if (secondDeriv) {
                //draw second derivative vectors
                derivVector = curve.getSecondDeriv(param);
                derivLine.moveTo(current.x, current.y);
                derivLine.lineTo(current.x + derivVector.x, current.y + derivVector.y);
                drawArrowHead(derivVector, derivLine);
            }

        }
        g2.setColor(Color.BLUE);
        g2.draw(tangentLine);
        g2.setColor(Color.RED);
        g2.draw(curvLine);
        g2.setColor(Color.WHITE);
        g2.draw(derivLine);
    }

    /**
     * Draw the Bezier Curve
     */
    public void drawCurve() {
        GeneralPath polyline = new GeneralPath();

        if (!curve.getPoints().isEmpty()) {
            Vector start = curve.getPoint(0);
            polyline.moveTo (start.x, start.y);
            for (double param = STEP_SIZE; param <= 1; param = round(param + STEP_SIZE, PRECISION)) {
                Vector current = curve.getPoint(param);
                polyline.lineTo(current.x, current.y);
            }

            g2.draw(polyline);

            // Draw points with their names
            int i = 1;
            for (Vector point : curve.getPoints()) {
                g2.draw(new Ellipse2D.Double(point.x - POINT_HALF_WIDTH, point.y - POINT_HALF_WIDTH, 2*POINT_HALF_WIDTH, 2*POINT_HALF_WIDTH));
                g2.drawString("p" + i++, ((float) point.x), ((float) point.y));
            }
        }
    }

    /**
     * Start dragging point if mouse pressed and inside a controlling point
     * @param e
     */
    public void onMousePressed(MouseEvent e) {
        dragIndex = NOT_DRAGGING;
        int indexOfClosestPoint = NOT_DRAGGING;
        List<Vector> points = curve.getPoints();
        for (int i = 0; i < points.size(); i++) {
            double diffX = points.get(i).x - e.getX();
            double diffY = points.get(i).y - e.getY();
            int distance = (int) (Math.sqrt(diffX * diffX + diffY * diffY));
            if (distance < CurveDraw.POINT_HALF_WIDTH) {
                indexOfClosestPoint = i;
            }
        }

        dragIndex = indexOfClosestPoint;

    }

    /**
     * keep dragging if mouse still pressed
     * @param e
     */
    public void onMouseDragged(MouseEvent e) {
        if (dragIndex == NOT_DRAGGING)
            return;

        List<Vector> points = curve.getPoints();
        points.set(dragIndex, new Vector(e.getX(), e.getY()));
    }

    /**
     * stop dragging when mouse released
     * @param e
     */
    public void onMouseReleased(MouseEvent e) {
        if (dragIndex == NOT_DRAGGING)
            return;
        List<Vector> points = curve.getPoints();
        points.set(dragIndex, new Vector(e.getX(), e.getY()));
        dragIndex = NOT_DRAGGING;
    }

    /**
     * draws arrowhead for tangent and curvature vectors
     * @param vector tangent or curvature vector
     * @param vectorLine tangent or curvature line without the arrowhead
     */
    private void drawArrowHead(Vector vector, GeneralPath vectorLine) {
        Point2D point = vectorLine.getCurrentPoint();
        Vector perpVector = (new Vector(-vector.y, vector.x)).ofSize(ARROW_HALF_WIDTH);
        Vector arrowVector = vector.ofSize(ARROW_LENGTH);
        Vector tip = new Vector(point.getX() + arrowVector.x, point.getY() + arrowVector.y);
        Vector leftCorner = new Vector(point.getX() + perpVector.x, point.getY() + perpVector.y);
        Vector rightCorner = new Vector(point.getX() - perpVector.x, point.getY() - perpVector.y);

        vectorLine.lineTo(leftCorner.x, leftCorner.y);
        vectorLine.lineTo(tip.x, tip.y);
        vectorLine.lineTo(rightCorner.x, rightCorner.y);
        vectorLine.lineTo(point.getX(), point.getY());
    }

    public BezierCurve getCurve() {
        return curve;
    }

    public void setCurve(BezierCurve curve) {
        this.curve = curve;
    }

    public void setG2(Graphics2D g2) {
        this.g2 = g2;
    }

    /**
     * @param value value to round
     * @param precision precision to round to
     * @return value rounded to the specified precision
     */
    public static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
