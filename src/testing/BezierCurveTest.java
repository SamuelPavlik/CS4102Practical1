package testing;

import app.BezierCurve;
import app.Vector;

import static org.junit.Assert.*;

public class BezierCurveTest {

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    /**
     * test bezier curve with no controlling points
     */
    @org.junit.Test
    public void getPointTestEmptyCurve() {
        BezierCurve emptyCurve = new BezierCurve();
        assertEquals(null, emptyCurve.getPoint(0));
    }

    /**
     * test curve with 1 controlling point
     */
    @org.junit.Test
    public void getPointTestOnePointCurve() {
        BezierCurve onePointCurve = new BezierCurve(new Vector(10, 20));
        assertEquals(new Vector(10, 20), onePointCurve.getPoint(0));
        assertEquals(new Vector(10, 20), onePointCurve.getPoint(1));
    }

    /**
     * test bezier curve with 2 controlling points for parameter 0 and 1
     */
    @org.junit.Test
    public void getPointTestCurve1() {
        BezierCurve curve = new BezierCurve(new Vector(10, 20), new Vector(20, 20));
        assertEquals(new Vector(10, 20), curve.getPoint(0));
        assertEquals(new Vector(20, 20), curve.getPoint(1));
    }

    /**
     * test bezier curve with 3 controlling points for parameter 0, 0.5 and 1
     */
    @org.junit.Test
    public void getPointTestCurve2() {
        BezierCurve curve = new BezierCurve(new Vector(10, 20), new Vector(15, 20), new Vector(20, 20));
        assertEquals(new Vector(10, 20), curve.getPoint(0));
        assertEquals(new Vector(15, 20), curve.getPoint(0.5));
        assertEquals(new Vector(20, 20), curve.getPoint(1));
    }

    /**
     * test rounded curve with 3 controlling points for parameter 0 and 1
     */
    @org.junit.Test
    public void getPointTestRoundedCurve() {
        BezierCurve curve = new BezierCurve(new Vector(10, 20), new Vector(15, 20), new Vector(15, 30));
        assertEquals(new Vector(10, 20), curve.getPoint(0));
        assertEquals(new Vector(15, 30), curve.getPoint(1));
    }

    @org.junit.Test
    public void getTangent() {

    }

    @org.junit.Test
    public void getSecondDeriv() {
    }

    @org.junit.Test
    public void getCurvature() {
    }

    @org.junit.Test
    public void getUniformParams() {
    }

    @org.junit.Test
    public void getPoints() {
    }

    @org.junit.Test
    public void addPoint() {
    }
}