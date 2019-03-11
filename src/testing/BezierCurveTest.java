package testing;

import app.BezierCurve;
import app.Vector;

import java.util.List;

import static org.junit.Assert.*;

public class BezierCurveTest {

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
     * test rounded curve with 3 controlling points for parameter 0, 0.5 and 1
     */
    @org.junit.Test
    public void getPointTestRoundedCurve() {
        BezierCurve curve = new BezierCurve(new Vector(10, 20), new Vector(15, 20), new Vector(15, 30));
        assertEquals(new Vector(10, 20), curve.getPoint(0));
        Vector middle = curve.getPoint(0.5);
        assertTrue(middle.x > 10 && middle.x < 15 && middle.y > 20 && middle.y < 30);
        assertEquals(new Vector(15, 30), curve.getPoint(1));
    }

    /**
     * get tangent for non-existent curve
     */
    @org.junit.Test
    public void getTangentEmptyCurve() {
        BezierCurve emptyCurve = new BezierCurve();
        assertEquals(new Vector(), emptyCurve.getTangent(0));
        assertEquals(new Vector(), emptyCurve.getTangent(1));
    }

    /**
     * get tangent for curve with 1 controlling point
     */
    @org.junit.Test
    public void getTangentTestOnePointCurve() {
        BezierCurve onePointCurve = new BezierCurve(new Vector(10, 20));
        assertEquals(new Vector(), onePointCurve.getTangent(0));
        assertEquals(new Vector(), onePointCurve.getTangent(1));
    }

    /**
     * get tangent for curve with 2 controlling points
     */
    @org.junit.Test
    public void getTangentTestCurve2() {
        BezierCurve curve = new BezierCurve(new Vector(10, 20), new Vector(20, 20));
        assertEquals(0, curve.getTangent(0).dot(new Vector(0, 1)), 0);
        assertEquals(0, curve.getTangent(1).dot(new Vector(0, 1)), 0);
    }

    /**
     * get tangent for bezier curve with 3 controlling points for parameter 0, 0.5 and 1
     */
    @org.junit.Test
    public void getTangentTestCurve3() {
        BezierCurve curve = new BezierCurve(new Vector(10, 20), new Vector(15, 20), new Vector(20, 20));
        assertEquals(0, curve.getTangent(0).dot(new Vector(0, 1)), 0);
        assertEquals(0, curve.getTangent(0.5).dot(new Vector(0, 1)), 0);
        assertEquals(0, curve.getTangent(1).dot(new Vector(0, 1)), 0);
    }

    /**
     * test rounded curve with 3 controlling points for parameter 0, 0.5 and 1
     */
    @org.junit.Test
    public void getTangentTestRoundedCurve3() {
        BezierCurve curve = new BezierCurve(new Vector(10, 20), new Vector(15, 20), new Vector(15, 30));
        assertEquals(0, curve.getTangent(0).dot(new Vector(0, 1)), 0);
        assertTrue(curve.getTangent(0.5).dot(new Vector(1, 0)) > 0);
        assertTrue(curve.getTangent(0.5).dot(new Vector(0, 1)) > 0);
        assertEquals(0, curve.getTangent(1).dot(new Vector(1, 0)), 0);
    }

    /**
     * get curvature vector for non-existent curve
     */
    @org.junit.Test
    public void getCurvatureEmptyCurve() {
        BezierCurve emptyCurve = new BezierCurve();
        assertEquals(new Vector(), emptyCurve.getCurvature(0));
        assertEquals(new Vector(), emptyCurve.getCurvature(1));
    }

    /**
     * get curvature vector for curve with 1 controlling point
     */
    @org.junit.Test
    public void getCurvatureTestOnePointCurve() {
        BezierCurve onePointCurve = new BezierCurve(new Vector(10, 20));
        assertEquals(new Vector(), onePointCurve.getCurvature(0));
        assertEquals(new Vector(), onePointCurve.getCurvature(1));
    }

    /**
     * get curvature vector for curve with 2 controlling points
     */
    @org.junit.Test
    public void getCurvatureTestCurve2() {
        BezierCurve curve = new BezierCurve(new Vector(10, 20), new Vector(20, 20));
        assertEquals(0, curve.getCurvature(0).dot(new Vector(1, 0)), 0);
        assertEquals(0, curve.getCurvature(1).dot(new Vector(1, 0)), 0);
    }

    /**
     * get curvature vector for rounded curve with 3 controlling points for parameter 0, 0.5 and 1
     */
    @org.junit.Test
    public void getCurvatureTestCurve3() {
        BezierCurve curve = new BezierCurve(new Vector(10, 20), new Vector(15, 20), new Vector(15, 30));
        assertEquals(0, curve.getCurvature(0).dot(new Vector(1, 0)), 0);
        assertTrue(curve.getCurvature(0.5).dot(new Vector(-1, 0)) > 0);
        assertTrue(curve.getCurvature(0.5).dot(new Vector(0, 1)) > 0);
        assertEquals(0, curve.getCurvature(1).dot(new Vector(0, 1)), 0);
    }

    /**
     * get curvature vector for bezier curve with 4 controlling points for parameter 0, 0.5 and 1
     */
    @org.junit.Test
    public void getCurvatureTestCurve4() {
        BezierCurve curve = new BezierCurve(new Vector(10, 20), new Vector(20, 20),
                                            new Vector(10, 30), new Vector(20, 30));
        assertEquals(0, curve.getCurvature(0).dot(new Vector(1, 0)), 0);
        assertEquals(0, curve.getCurvature(0.5).dot(new Vector(0, 1)), 0);
        assertEquals(0, curve.getCurvature(1).dot(new Vector(1, 0)), 0);
    }

    /**
     * get second derivative vector for non-existent curve
     */
    @org.junit.Test
    public void getSecondDerivEmptyCurve() {
        BezierCurve emptyCurve = new BezierCurve();
        assertEquals(new Vector(), emptyCurve.getSecondDeriv(0));
        assertEquals(new Vector(), emptyCurve.getSecondDeriv(1));
    }

    /**
     * get second derivative vector for curve with 1 controlling point
     */
    @org.junit.Test
    public void getSecondDerivTestOnePointCurve() {
        BezierCurve onePointCurve = new BezierCurve(new Vector(10, 20));
        assertEquals(new Vector(), onePointCurve.getSecondDeriv(0));
        assertEquals(new Vector(), onePointCurve.getSecondDeriv(1));
    }

    /**
     * get second derivative vector for curve with 2 controlling points
     */
    @org.junit.Test
    public void getSecondDerivTestCurve2() {
        BezierCurve curve = new BezierCurve(new Vector(10, 20), new Vector(20, 20));
        assertEquals(0, curve.getSecondDeriv(0).dot(new Vector(1, 0)), 0);
        assertEquals(0, curve.getSecondDeriv(1).dot(new Vector(1, 0)), 0);
    }

    /**
     * get second derivative vector for rounded curve with 3 controlling points for parameter 0, 0.5 and 1
     */
    @org.junit.Test
    public void getSecondDerivTestCurve3() {
        BezierCurve curve = new BezierCurve(new Vector(10, 20), new Vector(15, 20), new Vector(15, 30));
        assertTrue(curve.getSecondDeriv(0).dot(new Vector(-1, 0)) > 0);
        assertTrue(curve.getSecondDeriv(0).dot(new Vector(0, 1)) > 0);
        assertTrue(curve.getSecondDeriv(0.5).dot(new Vector(-1, 0)) > 0);
        assertTrue(curve.getSecondDeriv(0.5).dot(new Vector(0, 1)) > 0);
        assertTrue(curve.getSecondDeriv(1).dot(new Vector(-1, 0)) > 0);
        assertTrue(curve.getSecondDeriv(1).dot(new Vector(0, 1)) > 0);
    }

    /**
     * get second derivative vector for bezier curve with 4 controlling points for parameter 0, 0.5 and 1
     */
    @org.junit.Test
    public void getSecondDerivTestCurve4() {
        BezierCurve curve = new BezierCurve(new Vector(10, 20), new Vector(20, 20),
                new Vector(10, 30), new Vector(20, 30));
        assertTrue(curve.getSecondDeriv(0).dot(new Vector(-1, 0)) > 0);
        assertTrue(curve.getSecondDeriv(0).dot(new Vector(0, 1)) > 0);
        assertEquals(new Vector(), curve.getSecondDeriv(0.5));
        assertTrue(curve.getSecondDeriv(1).dot(new Vector(1, 0)) > 0);
        assertTrue(curve.getSecondDeriv(1).dot(new Vector(0, -1)) > 0);
    }

    /**
     * get specified number of sampling points for non-existent curve
     */
    @org.junit.Test
    public void getUniformParamsEmptyCurve() {
        BezierCurve emptyCurve = new BezierCurve();
        assertEquals(0, emptyCurve.getUniformParams(3).size());
    }

    /**
     * get specified number of sampling points for curve with 1 controlling point
     */
    @org.junit.Test
    public void getUniformParamsTestOnePointCurve() {
        BezierCurve onePointCurve = new BezierCurve(new Vector(10, 20));
        assertEquals(0, onePointCurve.getUniformParams(3).size());
    }

    /**
     * get specified number of sampling points for curve with 2 controlling points
     */
    @org.junit.Test
    public void getUniformParamsTestCurve2() {
        BezierCurve curve = new BezierCurve(new Vector(10, 20), new Vector(15, 20));
        List<Double> params = curve.getUniformParams(6);
        assertEquals(6, curve.getUniformParams(6).size());

        for (int i = 0; i < params.size(); i++) {
            Vector point = curve.getPoint(params.get(i));
            assertEquals(10 + i, point.x, 0);
            assertEquals(20, point.y, 0);
        }
    }

    /**
     * get specified number of sampling points for rounding curve with 3 controlling points
     */
    @org.junit.Test
    public void getUniformParamsTestCurve3() {
        BezierCurve curve = new BezierCurve(new Vector(10, 20), new Vector(15, 20), new Vector(15, 30));
        List<Double> params = curve.getUniformParams(6);
        assertEquals(6, curve.getUniformParams(6).size());

        // check that each point is further down the line of the curve
        // each x and y should be larger than the previous
        Vector prev = curve.getPoint(params.get(0));
        for (int i = 1; i < params.size(); i++) {
            Vector point = curve.getPoint(params.get(i));
            assertTrue(point.x > prev.x);
            assertTrue(point.y > prev.y);
        }

        // check that the first and the last sampling points are equal to starting and ending controlling points
        assertEquals(new Vector(10, 20), curve.getPoint(params.get(0)));
        assertEquals(new Vector(15, 30), curve.getPoint(params.get(params.size() - 1)));
    }

    /**
     * get specified number of sampling points for bezier curve with 4 controlling points
     */
    @org.junit.Test
    public void getUniformParamsTestCurve4() {
        BezierCurve curve = new BezierCurve(new Vector(10, 20), new Vector(15, 20),
                                            new Vector(10, 30), new Vector(15, 30));
        List<Double> params = curve.getUniformParams(6);
        assertEquals(6, curve.getUniformParams(6).size());

        // check that each point is further down the line of the curve
        // each x and y should be larger than the previous
        Vector prev = curve.getPoint(params.get(0));
        for (int i = 1; i < params.size(); i++) {
            Vector point = curve.getPoint(params.get(i));
            assertTrue(point.x > prev.x);
            assertTrue(point.y > prev.y);
        }

        // check that the first and the last sampling points are equal to starting and ending controlling points
        assertEquals(new Vector(10, 20), curve.getPoint(params.get(0)));
        assertEquals(new Vector(15, 30), curve.getPoint(params.get(params.size() - 1)));    }

    @org.junit.Test
    public void getPointsTestEmpty() {
        BezierCurve bezierCurve = new BezierCurve();
        assertEquals(0, bezierCurve.getPoints().size());
    }

    @org.junit.Test
    public void getPointsTest1() {
        BezierCurve bezierCurve = new BezierCurve(new Vector());
        assertEquals(1, bezierCurve.getPoints().size());
    }

    @org.junit.Test
    public void getPointsTestMany() {
        BezierCurve bezierCurve = new BezierCurve(new Vector(), new Vector(), new Vector());
        assertEquals(3, bezierCurve.getPoints().size());
    }

    @org.junit.Test
    public void addPointTest1() {
        BezierCurve bezierCurve = new BezierCurve(new Vector());
        assertEquals(1, bezierCurve.getPoints().size());
        bezierCurve.addPoint(new Vector());
        assertEquals(2, bezierCurve.getPoints().size());
    }

    @org.junit.Test
    public void addPointTestMany() {
        BezierCurve bezierCurve = new BezierCurve(new Vector());
        assertEquals(1, bezierCurve.getPoints().size());
        bezierCurve.addPoint(new Vector());
        bezierCurve.addPoint(new Vector());
        bezierCurve.addPoint(new Vector());
        assertEquals(4, bezierCurve.getPoints().size());
    }
}