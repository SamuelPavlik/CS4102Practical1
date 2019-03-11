package app;

import java.util.*;

public class BezierCurve {
    private static final double SIZE_MULTIPLIER = 0.03;
    private static final int PRECISION = 3;
    private static final double STEP_SIZE = 1.0 / Math.pow(10, PRECISION);

    private List<Vector> points;

    public BezierCurve() {
        this.points = new ArrayList<>();
    }

    public BezierCurve(List<Vector> points) {
        this.points = points;
    }

    public BezierCurve(Vector... points) {
        this.points = new ArrayList<>();
        for (Vector point : points) {
            this.points.add(point);
        }
    }

    /**
     * Get point on the curve specified by the parameter
     * @param param the curve parameter
     * @return point as app.Vector object
     */
    public Vector getPoint(double param) {
        if (points.isEmpty())
            return null;

        int n = points.size() - 1;
        int nFact = factorial(n);
        Vector resultPoint = new Vector();

        for (int i = 0; i <= n; i++) {
            int comb = nFact / (factorial(n - i) * factorial(i));
            double toMultSc = comb * (Math.pow(param, i) * Math.pow(1 - param, n - i));
            Vector toAdd = points.get(i).copy().scMult(toMultSc);
            resultPoint.add(toAdd);
        }

        return resultPoint;
    }

    /**
     * Get tangent vector for the point on the curve specified by the parameter
     * @param param he curve parameter
     * @return tangent vector as app.Vector object
     */
    public Vector getTangent(double param) {
        int n = points.size() - 1;
        int nMin1Fact = factorial(n - 1);
        Vector resultPoint = new Vector();

        for (int i = 0; i <= n - 1; i++) {
            int comb = nMin1Fact / (factorial(n - 1 - i) * factorial(i));
            double toMultSc = comb * (Math.pow(param, i) * Math.pow(1 - param, n - 1 - i));
            Vector pointDiff = points.get(i + 1).copy().sub(points.get(i));
            resultPoint.add(pointDiff.scMult(toMultSc));
        }

        return resultPoint.scMult(n*SIZE_MULTIPLIER);
    }

    /**
     * Get second derivative vector for the point on the curve specified by the parameter
     * @param param the curve parameter
     * @return second derivative vector as app.Vector object
     */
    public Vector getSecondDeriv(double param) {
        int n = points.size() - 1;
        int nMin2Fact = factorial(n - 2);
        Vector resultPoint = new Vector();
        double toMultSc;
        Vector pointDiff;

        for (int i = 0; i <= n - 2; i++) {
            int comb = nMin2Fact / (factorial(n - 2 - i) * factorial(i));
            toMultSc = comb * (Math.pow(param, i) * Math.pow(1 - param, n - 2 - i));
            pointDiff = (points.get(i + 2).copy().sub(points.get(i + 1).copy().scMult(2))).add(points.get(i));
            resultPoint.add(pointDiff.scMult(toMultSc));
        }

        return resultPoint.scMult(n * (n - 1)*SIZE_MULTIPLIER);
    }

    /**
     * Get curvature vector for the point on the curve specified by the parameter
     * @param param the curve parameter
     * @return curvature vector as app.Vector object
     */
    public Vector getCurvature(double param) {
        Vector tangent = getTangent(param);
        Vector secondDeriv = getSecondDeriv(param);
        Vector curvature;
        double angle = Math.atan2((tangent.x * secondDeriv.y) - (tangent.y * secondDeriv.x), (tangent.x * secondDeriv.x) - (tangent.y * secondDeriv.y));
        if (angle > 0) {
            curvature = new Vector(-tangent.y, tangent.x);
        }
        else {
            curvature = new Vector(tangent.y, -tangent.x);
        }

        double secondDerivSize = secondDeriv.size();
        if (secondDerivSize == 0)
            return new Vector();
        return curvature.ofSize(1).scMult(secondDeriv.size());
    }

    /**
     * get list of parameters that represent uniformly sampled points on the curve based on the curve length
     * @param num number of uniformly sampled points on the curve
     * @return list of parameters that represent uniformly sampled points on the curve based on the curve length
     */
    public List<Double> getUniformParams(int num) {
        List<Double> uniformParams = new ArrayList<>();
        if (points.size() <= 1 || num <= 0)
            return uniformParams;

        double totalLength = 0;
        Vector oldPoint = getPoint(0);
        Vector newPoint;
        HashMap<Double, Double> initLengthToParamMap = new HashMap<>();
        initLengthToParamMap.put(0.0, 0.0);
        double[] lengths = new double[(int)(1.0 / STEP_SIZE) + 1];
        lengths[0] = 0;
        int index = 1;

        for (double param = STEP_SIZE; param <= 1; param = MainApp.round(param + STEP_SIZE, 3)) {
            newPoint = getPoint(param);
            double length = newPoint.copy().sub(oldPoint).size();
            totalLength += length;
            initLengthToParamMap.put(totalLength, param);
            lengths[index++] = totalLength;

            oldPoint = newPoint;
        }

        double unit = totalLength / (num - 1);
        for (int i = 0; i < num; i++) {
            double pointLength = i * unit;
            double pointParam = initLengthToParamMap.get(lengths[findClosestLargerIndex(pointLength, lengths)]);
            uniformParams.add(pointParam);
        }

        return uniformParams;
    }

    private int findClosestLargerIndex(double desiredLength, double[] lengths) {
        for (int i = 0; i < lengths.length; i++) {
            if (lengths[i] >= desiredLength) {
                return i;
            }
        }

        return lengths.length - 1;
    }

    /**
     * @return list of controlling points
     */
    public List<Vector> getPoints() {
        return points;
    }

    /**
     * @param point controlling point
     */
    public void addPoint(Vector point) {
        points.add(point);
    }

    /**
     * @param number number to get factorial of
     * @return factorial of the number
     */
    private static int factorial(int number) {
        int result = 1;

        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }

        return result;
    }
}