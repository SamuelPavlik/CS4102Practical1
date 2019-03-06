import java.util.ArrayList;
import java.util.List;

public class BezierCurve {
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

        return resultPoint.scMult(n);
    }

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