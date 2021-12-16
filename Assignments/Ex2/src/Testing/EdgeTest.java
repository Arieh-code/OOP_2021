package Testing;

import Ex2.Edge;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    Edge e1 = new Edge(0,1.3118716362419698, 16);

    @Test
    void getSrc() {
        assertEquals(e1.getSrc(), 0);
        assertNotEquals(e1.getSrc(), 100);
    }

    @Test
    void getDest() {
        assertEquals(e1.getDest(), 16);
        assertNotEquals(e1.getDest(), 10);
    }

    @Test
    void getWeight() {
        assertEquals(e1.getWeight(), 1.3118716362419698);
        assertNotEquals(e1.getWeight(), 1.4518716362419698);
    }

    @Test
    void getInfo() {
        assertEquals(e1.getInfo(), null);
        e1.setInfo("testing info");
        assertEquals(e1.getInfo(), "testing info");
    }

    @Test
    void setInfo() {
        assertNotEquals(e1.getInfo(), "Changed info");
        e1.setInfo("Changed info");
        assertEquals(e1.getInfo(), "Changed info");
    }

    @Test
    void getTag() {
        e1.setTag(10);
        assertEquals(e1.getTag(), 10);
        assertNotEquals(e1.getTag(), -1);
    }

    @Test
    void setTag() {
        assertEquals(e1.getTag(), 0);
        e1.setTag(99);
        assertNotEquals(e1.getTag(), 10);
        assertEquals(e1.getTag(), 99);
    }

    /** This Tests the toString function. */
    @Test
    void toString_Test(){
        assertEquals(e1.toString(), "Src: 0.\nWeight: 1.3118716362419698.\nDest: 16.");
    }
}