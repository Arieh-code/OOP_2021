package Testing;


import Ex2.GNode;
import Ex2.Edge;
import Ex2.Point3D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GNodeTest {
    /**
     * building Nodes and running tests on the GraphNode Class
     */
    Point3D p1 = new Point3D(35.19589389346247,32.10152879327731,0.0);
    Point3D p2 = new Point3D(35.20319591121872,32.10318254621849,0.0);
    Point3D p3 = new Point3D(35.20752617756255,32.1025646605042,0.0);
    Point3D p4 = new Point3D(35.21007339305892,32.10107446554622,0.0);
    Point3D p5 = new Point3D(35.21310882485876,32.104636394957986,0.0);
    Point3D p6 = new Point3D(1, 2, 3);
    Point3D p7 = new Point3D(4, 5, 6);
    Point3D p8 = new Point3D(7, 8, 9);
    Point3D p9 = new Point3D(10, 11, 12);


    GNode a = new GNode(0, p1);
    GNode b = new GNode(1, p2);
    GNode c = new GNode(2, p3);
    GNode d = new GNode(3, p4);
    GNode e = new GNode(4, p5);

    Edge e1 = new Edge(0,1.3118716362419698, 16);



    /**
     * checking if we can get the key from the node
     */
    @Test
    void getKey() {
        Assertions.assertEquals(0, a.getKey());
        Assertions.assertEquals(1, b.getKey());
        Assertions.assertEquals(2, c.getKey());
        Assertions.assertEquals(3, d.getKey());
        Assertions.assertEquals(4, e.getKey());

    }

    /**
     * checking if we can get the geolocation from a node
     */
    @Test
    void getLocation() {
        Assertions.assertEquals(p1, a.getLocation());
        Assertions.assertEquals(p2, b.getLocation());
        Assertions.assertEquals(p3, c.getLocation());
        Assertions.assertEquals(p4, d.getLocation());
        Assertions.assertEquals(p5, e.getLocation());
    }

    /**
     * checking if we can set the geolocation of a node
     */
    @Test
    void setLocation() {
        a.setLocation(p6);
        b.setLocation(p7);
        c.setLocation(p8);
        d.setLocation(p9);
        Assertions.assertEquals(p6, a.getLocation());
        Assertions.assertEquals(p7, b.getLocation());
        Assertions.assertEquals(p8, c.getLocation());
        Assertions.assertEquals(p9, d.getLocation());

    }

    /**
     * checking if we can get the weight associated with a node
     */
    @Test
    void getWeight() {
        a.setWeight(33.0);
        b.setWeight(44.5);
        c.setWeight(28.22);
        Assertions.assertEquals(33.0, a.getWeight());
        Assertions.assertEquals(44.5, b.getWeight());
        Assertions.assertEquals(28.22, c.getWeight());

    }

    /**
     * checking if we can set the weight associated with a node
     */
    @Test
    void setWeight() {
        a.setWeight(33.0);
        b.setWeight(44.5);
        c.setWeight(28.22);
        Assertions.assertEquals(33.0, a.getWeight());
        Assertions.assertEquals(44.5, b.getWeight());
        Assertions.assertEquals(28.22, c.getWeight());
    }


    @Test
    void getInfo() {
        a.setInfo("Testing");
        assertEquals("Testing", a.getInfo());
    }

    @Test
    void setInfo() {
        a.setInfo("Testing");
        assertEquals("Testing", a.getInfo());
    }

    @Test
    void getTag() {
        b.setTag(10);
        assertEquals(10,b.getTag());
    }

    @Test
    void setTag() {
        b.setTag(10);
        assertEquals(10,b.getTag());
    }


    /** This Tests the toString function. */
    @Test
    void toString_Test(){
        assertEquals(a.toString(), "Key: 0.\nLocation: (35.19589389346247, 32.10152879327731, 0.0).");
    }


}