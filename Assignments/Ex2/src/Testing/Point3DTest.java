package Testing;

import Ex2.Point3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * this class represents the tests for Point3D class
 */
class Point3DTest {
    Point3D a = new Point3D(0,1,2);
    Point3D b = new Point3D(0,0,0);
    double distance = Math.sqrt(5);

    @Test
    void x() {
        assertEquals(0,a.x());
        assertEquals(0,b.x());

    }

    @Test
    void y() {
        assertEquals(1,a.y());
        assertEquals(0,b.y());
    }

    @Test
    void z() {
        assertEquals(2,a.z());
        assertEquals(0,b.z());
    }

    @Test
    void distance() {
        assertEquals(distance, a.distance(b));
    }

    /** This Tests the toString function. */
    @Test
    void toString_Test() {
        assertEquals(a.toString(), "(0.0, 1.0, 2.0)");
    }
}