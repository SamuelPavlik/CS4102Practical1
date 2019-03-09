//from www.j a va2s .  c  o m
import java.awt.event.*;
import java.util.List;
import java.awt.*;
import java.awt.geom.*;

public class Main extends Frame implements MouseListener, MouseMotionListener, ActionListener {
    private static final double ARROW_LENGTH = 10;
    private static final double ARROW_HALF_WIDTH = 5;
    private static final int SPACING = 30;
    private static final int SAMPLE_NUM_X = 10;
    private static final int SAMPLE_NUM_Y = 50;
    private static final int SAMPLE_NUM_LABEL_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 20;
    private static final int SAMPLE_NUM_FIELD_X = SAMPLE_NUM_X + SAMPLE_NUM_LABEL_WIDTH;
    private static final int SAMPLE_NUM_FIELD_WIDTH = 50;
    private static final int TANGENT_LABEL_X = SAMPLE_NUM_FIELD_X + SAMPLE_NUM_FIELD_WIDTH + SPACING;
    private static final int TANGENT_LABEL_WIDTH = 70;
    private static final int TANGENT_FIELD_X = TANGENT_LABEL_X + TANGENT_LABEL_WIDTH;
    private static final int TANGENT_FIELD_WIDTH = 20;
    private static final int CURV_LABEL_X = TANGENT_FIELD_X + TANGENT_FIELD_WIDTH + SPACING;
    private static final int CURV_LABEL_WIDTH = 70;
    private static final int CURV_FIELD_X = CURV_LABEL_X + CURV_LABEL_WIDTH;
    private static final int APPLY_BUTTON_X = CURV_FIELD_X + TANGENT_FIELD_WIDTH + SPACING;
    private static final int APPLY_BUTTON_WIDTH = 60;
    private static final int BUTTON_HEIGHT = 30;
    private static final int BUTTON_Y = SAMPLE_NUM_Y - 5;
    private static final int CLEAR_BUTTON_X =  + APPLY_BUTTON_X + APPLY_BUTTON_WIDTH + SPACING;
    private static final int CLEAR_BUTTON_WIDTH =  60;

    private BezierCurve bezierCurve;
    private TextField numOfSamplesField;
    private Checkbox tangentCheckbox;
    private Checkbox curvCheckbox;
    private Button applyButton;
    private Button clearButton;
    private GeneralPath tangentLine = new GeneralPath();
    private GeneralPath curvLine = new GeneralPath();
    private int numOfSamples = 0;

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
        //init bezier curve
        this.bezierCurve = new BezierCurve();

        //setup text field for number of uniform samples
        Label numOfSamplesLabel = new Label("Number of uniform samples: ");
        numOfSamplesLabel.setBounds(SAMPLE_NUM_X, SAMPLE_NUM_Y, SAMPLE_NUM_LABEL_WIDTH, DEFAULT_HEIGHT);
        add(numOfSamplesLabel);
        numOfSamplesField = new TextField();
        numOfSamplesField.setBounds(SAMPLE_NUM_X + SAMPLE_NUM_LABEL_WIDTH, SAMPLE_NUM_Y, SAMPLE_NUM_FIELD_WIDTH, DEFAULT_HEIGHT);
        numOfSamplesField.addActionListener(this);
        add(numOfSamplesField);

        //setup tangent checkbox
        Label tangentLabel = new Label("Tangent: ");
        tangentLabel.setBounds(TANGENT_LABEL_X, SAMPLE_NUM_Y, TANGENT_LABEL_WIDTH, DEFAULT_HEIGHT);
        add(tangentLabel);
        tangentCheckbox = new Checkbox();
        tangentCheckbox.setBounds(TANGENT_FIELD_X, SAMPLE_NUM_Y, TANGENT_FIELD_WIDTH, DEFAULT_HEIGHT);
        add(tangentCheckbox);

        //setup curvature checkbox
        Label curvLabel = new Label("Curvature: ");
        curvLabel.setBounds(CURV_LABEL_X, SAMPLE_NUM_Y, CURV_LABEL_WIDTH, DEFAULT_HEIGHT);
        add(curvLabel);
        curvCheckbox = new Checkbox();
        curvCheckbox.setBounds(CURV_FIELD_X, SAMPLE_NUM_Y, TANGENT_FIELD_WIDTH, DEFAULT_HEIGHT);
        add(curvCheckbox);

        //setup apply button
        applyButton = new Button("Apply");
        applyButton.setBounds(APPLY_BUTTON_X, BUTTON_Y, APPLY_BUTTON_WIDTH, BUTTON_HEIGHT);
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        add(applyButton);

        //setup clear button
        clearButton = new Button("Clear");
        clearButton.setBounds(CLEAR_BUTTON_X, BUTTON_Y, CLEAR_BUTTON_WIDTH, BUTTON_HEIGHT);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                bezierCurve = new BezierCurve();
                repaint();
            }
        });
        add(clearButton);

        setLayout(null);
        setSize(1000, 1000);
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

        if (!bezierCurve.getPoints().isEmpty()) {
            Vector start = bezierCurve.getPoint(0);
            Vector end = bezierCurve.getPoint(1);

            polyline.moveTo (start.x, start.y);
            for (double param = 0.01; param <= 1; param+=0.01) {
                param = round(param, 2);
                Vector current = bezierCurve.getPoint(param);
                polyline.lineTo(current.x, current.y);
            }

            g2.draw(polyline);

            // Draw points with their names
            int i = 1;
            for (Vector point : bezierCurve.getPoints()) {
                g2.draw(new Ellipse2D.Double(point.x, point.y, 10, 10));
                g2.drawString("p" + i++, ((float) point.x), ((float) point.y));
            }
        }

        g2.draw(tangentLine);
        g2.draw(curvLine);
    }

    int x = 200;
    int y =200;
    public void mousePressed(MouseEvent e) {
        bezierCurve.addPoint(new Vector(e.getX(), e.getY()));
//        if (y == 300)
//            x -= 200;
//        if (x == 300) {
//            y += 100;
//            x -= 100;
//        }
//        x+=100;
        initUniformPoints(numOfSamples);
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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        numOfSamples = 0;
        try {
            numOfSamples = Integer.parseInt(numOfSamplesField.getText());
        } catch (NumberFormatException e) {
            numOfSamples = 0;
        }
        if (numOfSamples < 0) numOfSamples = 0;

        initUniformPoints(numOfSamples);
        repaint();
    }

    private void initUniformPoints(int num) {
        Vector curvVector;
        Vector tanVector;
        List<Double> uniformPoints = bezierCurve.getUniformParams(num);
        tangentLine = new GeneralPath();
        curvLine = new GeneralPath();

        for (double param : uniformPoints) {
            //draw small circles around the sampled points
//            g2.draw(new Ellipse2D.Double(current.x - 5, current.y - 5, 10, 10));

            //draw tangent vectors
            Vector current = bezierCurve.getPoint(param);
            curvVector = bezierCurve.getCurvature(param);
            curvLine.moveTo(current.x - curvVector.x, current.y - curvVector.y);
            curvLine.lineTo(current.x + curvVector.x, current.y + curvVector.y);
            drawArrowHead(curvVector, curvLine);

            //draw curvature vectors
            tanVector = bezierCurve.getTangent(param);
            tangentLine.moveTo(current.x - tanVector.x, current.y - tanVector.y);
            tangentLine.lineTo(current.x + tanVector.x, current.y + tanVector.y);
            drawArrowHead(tanVector, tangentLine);
        }
    }

    public static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}