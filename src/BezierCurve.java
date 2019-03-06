import java.util.*;

public class BezierCurve {
    private static final double TANGENT_SIZE = 20;
    private static final double STEP_SIZE = 0.001;
    private static final double EPSILON = STEP_SIZE;

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

    public Vector getPoint(double param) {
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

    public Vector getTangent(double param) {
        int n = points.size() - 1;
        int nMinFact = factorial(n - 1);
        Vector resultPoint = new Vector();

        for (int i = 0; i <= n - 1; i++) {
            int comb = nMinFact / (factorial(n - 1 - i) * factorial(i));
            double toMultSc = comb * (Math.pow(param, i) * Math.pow(1 - param, n - 1 - i));
            Vector pointDiff = points.get(i + 1).copy().sub(points.get(i));
            resultPoint.add(pointDiff.scMult(toMultSc));
        }

        return resultPoint.scMult(n).ofSize(TANGENT_SIZE);
    }

    public List<Double> getUniformParams(int num) {
        List<Double> uniformParams = new ArrayList<>();
        if (points.size() <= 1 || num <= 0)
            return uniformParams;

        double fly = points.get(0).copy().sub(points.get(points.size() - 1)).size();

        double totalLength = 0;
        Vector oldPoint = getPoint(0);
        Vector newPoint;
        HashMap<Double, Double> initLengthToParamMap = new HashMap<>();
        initLengthToParamMap.put(0.0, 0.0);
        double[] lengths = new double[(int)(1.0 / STEP_SIZE) + 1];
        lengths[0] = 0;
        int index = 1;

        for (double param = STEP_SIZE; param <= 1; param = Main.round(param + STEP_SIZE, 3)) {
            newPoint = getPoint(param);
            double length = newPoint.copy().sub(oldPoint).size();
            totalLength += length;
            initLengthToParamMap.put(totalLength, param);
            lengths[index++] = totalLength;

            oldPoint = newPoint;
        }

        double unit = totalLength / num;
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

//    private double findParam(double desiredLength, double[] lengths, TreeMap<Double, Double> initParamToLengthMap) {
//        //1. get lower index
//        //2. look if either is within epsilon
//        //1. if not, get centre between these params and check if that is within epsilon
//        //4. if not, get half in which the length is and repeat from 3.
//        int higherIndex = findClosestLargerIndex(desiredLength, lengths);
//        int lowerIndex = higherIndex - 1;
//
//        if (lengths[higherIndex] - desiredLength <= EPSILON) {
//            return initParamToLengthMap.get(lengths[higherIndex]);
//        }
//        else if (desiredLength - lengths[lowerIndex] <= EPSILON) {
//            return initParamToLengthMap.get(lengths[lowerIndex]);
//        }
//        else {
//            double lowerLength = initParamToLengthMap.get(lengths[lowerIndex]);
//            double middle = (initParamToLengthMap.get(lengths[higherIndex]) - lowerLength) / 2.0;
//
//            while (Math.abs(lowerLength + getPoint(middle).sub(getPoint(lowerLength)).size() - desiredLength) > EPSILON) {
//
//                lowerLength = initParamToLengthMap.get(lengths[lowerIndex]);
//                middle = (initParamToLengthMap.get(lengths[higherIndex]) - lowerLength) / 2.0;
//            }
//        }
//
//    }

    public List<Vector> getPoints() {
        return points;
    }

    public void addPoint(Vector point) {
        points.add(point);
    }

    private static int factorial(int number) {
        int result = 1;

        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }

        return result;
    }
}