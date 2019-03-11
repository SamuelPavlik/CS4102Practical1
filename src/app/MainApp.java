package app;//from www.j a va2s .  c  o m
import java.awt.event.*;
import java.awt.*;

public class MainApp extends Frame implements MouseListener, MouseMotionListener {

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

    private CurveDraw curveDraw;
    private TextField numOfSamplesField;
    private Checkbox tangentCheckbox;
    private Checkbox curvCheckbox;

    private int dragIndex = NOT_DRAGGING;
    private final static int NOT_DRAGGING = -1;

    public static void main(String[] args) {
        (new MainApp()).setVisible(true);
    }

    /**
     * Set up the layout of labels, text fields and buttons
     */
    public MainApp() {
        curveDraw = new CurveDraw(new BezierCurve());

        //setup text field for number of uniform samples
        Label numOfSamplesLabel = new Label("Number of uniform samples: ");
        numOfSamplesLabel.setBounds(SAMPLE_NUM_X, SAMPLE_NUM_Y, SAMPLE_NUM_LABEL_WIDTH, DEFAULT_HEIGHT);
        add(numOfSamplesLabel);
        numOfSamplesField = new TextField();
        numOfSamplesField.setBounds(SAMPLE_NUM_X + SAMPLE_NUM_LABEL_WIDTH, SAMPLE_NUM_Y, SAMPLE_NUM_FIELD_WIDTH, DEFAULT_HEIGHT);
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
        Button applyButton = new Button("Apply");
        applyButton.setBounds(APPLY_BUTTON_X, BUTTON_Y, APPLY_BUTTON_WIDTH, BUTTON_HEIGHT);
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                repaint();
            }
        });
        add(applyButton);

        //setup clear button
        Button clearButton = new Button("Clear");
        clearButton.setBounds(CLEAR_BUTTON_X, BUTTON_Y, CLEAR_BUTTON_WIDTH, BUTTON_HEIGHT);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                curveDraw.setCurve(new BezierCurve());
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

    /**
     * Draw curve and uniformly sampled points on it based on the user input
     * @param g
     */
    public void paint(Graphics g) {
        // Set points
        curveDraw.setG2((Graphics2D) g);
        curveDraw.drawCurve();
        curveDraw.initUniformPoints(numOfSamplesField.getText(), tangentCheckbox.getState(), curvCheckbox.getState());
    }

    /**
     * Add controlling point to the Bezier curve
     * @param e
     */
    public void mousePressed(MouseEvent e) {
        curveDraw.getCurve().addPoint(new Vector(e.getX(), e.getY()));
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


}