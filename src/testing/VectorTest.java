package testing;

import app.Vector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VectorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void scMult0() {
        Vector vect1 = new Vector(0, 0);
        vect1.scMult(5);
        assertEquals(new Vector(0, 0), vect1);
    }

    @Test
    public void scMult1() {
        Vector vect1 = new Vector(5, 5);
        vect1.scMult(1);
        assertEquals(new Vector(5, 5), vect1);
    }

    @Test
    public void scMult() {
        Vector vect1 = new Vector(5, 5);
        vect1.scMult(2);
        assertEquals(new Vector(10, 10), vect1);
    }


    @Test
    public void addEmpty() {
        Vector vect1 = new Vector(0, 0);
        vect1.add(new Vector(0, 0));
        assertEquals(new Vector(0, 0), vect1);
    }

    @Test
    public void addTest1() {
        Vector vect1 = new Vector(0, 0);
        vect1.add(new Vector(1, 2));
        assertEquals(new Vector(1, 2), vect1);
    }

    @Test
    public void subEmpty() {
        Vector vect1 = new Vector(0, 0);
        vect1.sub(new Vector(0, 0));
        assertEquals(new Vector(0, 0), vect1);
    }

    @Test
    public void subTest1() {
        Vector vect1 = new Vector(0, 0);
        vect1.sub(new Vector(1, 2));
        assertEquals(new Vector(-1, -2), vect1);
    }

    @Test
    public void dot0() {
        Vector vect1 = new Vector(1, 2);
        assertEquals(0, vect1.dot(new Vector(0, 0)), 0);
    }

    @Test
    public void dotPerpendicular() {
        Vector vect1 = new Vector(1, 2);
        assertEquals(0, vect1.dot(new Vector(2, -1)), 0);
        assertEquals(0, vect1.dot(new Vector(-2, 1)), 0);
    }

    @Test
    public void dotTest() {
        Vector vect1 = new Vector(3, 4);
        assertEquals(10, vect1.dot(new Vector(2, 1)), 0);
    }

    @Test
    public void copy() {
        Vector vector = new Vector(5, 4);
        Vector vectorCopy = vector.copy();

        assertEquals(vector.x, vectorCopy.x, 0);
        assertEquals(vector.y, vectorCopy.y, 0);
        assertEquals(vector.z, vectorCopy.z, 0);
    }

    @Test
    public void sizeEmpty() {
        Vector vect1 = new Vector(0, 0);
        assertEquals(0, vect1.size(), 0);
    }

    @Test
    public void sizeTest() {
        Vector vect1 = new Vector(-3, 4);
        assertEquals(5, vect1.size(), 0);
    }

    @Test
    public void ofSizeTestEmpty() {
        Vector vectorEmpty = new Vector(0, 0);
        assertEquals(0, vectorEmpty.size(), 0);
    }

    @Test
    public void ofSizeTest() {
        Vector vector = new Vector(3, 4);
        assertEquals(new Vector(6, 8), vector.ofSize(10));
    }
}