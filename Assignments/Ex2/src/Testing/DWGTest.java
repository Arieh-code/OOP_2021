package Testing;

import Ex2.DWG;
import Ex2.Edge;
import Ex2.GNode;
import Ex2.Point3D;
import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

class DWGTest {

    Point3D p1 = new Point3D(35.19589389346247,32.10152879327731,0.0);
    Point3D p2 = new Point3D(35.20319591121872,32.10318254621849,0.0);
    Point3D p3 = new Point3D(35.20752617756255,32.1025646605042,0.0);
    Point3D p4 = new Point3D(35.21007339305892,32.10107446554622,0.0);
    Point3D p5 = new Point3D(35.21310882485876,32.104636394957986,0.0);

    NodeData a = new GNode(0, p1);
    NodeData b = new GNode(1, p2);
    NodeData c = new GNode(2, p3);
    NodeData d = new GNode(3, p4);
    NodeData e = new GNode(4, p5);


    DWG dwg = new DWG();

    @Test
    /** This tests the DWG JSON file constructor.*/
    void JSON_constructer() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        //Parsing the contents of the JSON file
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("G3.json"));
        DWG my_DWG = new DWG("G3.json");
        //check that the G3 info was inserted in the maps.
        assertEquals(48, my_DWG.nodeMap.size());
        assertEquals(48, my_DWG.edgeMap.size());


    }
    @Test
    void getNode() {
        dwg.addNode(a);
        assertEquals(dwg.getNode(0), a);
    }

    @Test
    void getEdge() {
        dwg.addNode(a);
        dwg.addNode(c);
        dwg.connect(a.getKey(), c.getKey(), 1.5);
        EdgeData e1 = new Edge(0,1.5, 2);
        assertEquals(dwg.edgeMap.get(a.getKey()).get(c.getKey()).getSrc(), e1.getSrc());
        assertEquals(dwg.edgeMap.get(a.getKey()).get(c.getKey()).getDest(), e1.getDest());
        assertEquals(dwg.edgeMap.get(a.getKey()).get(c.getKey()).getWeight(), e1.getWeight());
    }

    @Test
    void addNode() {
        assertNull(dwg.getNode(0));
        dwg.addNode(a);
        assertEquals(dwg.getNode(0), a);
    }

    @Test
    void connect() {
        assertEquals(0, dwg.edgeSize());
        dwg.addNode(a);
        dwg.addNode(c);
        dwg.connect(a.getKey(), c.getKey(), 1.0);
        assertEquals(1, dwg.edgeSize());
    }

    @Test
    void nodeIter() {
    }

    @Test
    void edgeIter() {
    }

    @Test
    void testEdgeIter() {
    }

    @Test
    void removeNode() throws IOException, ParseException {
        DirectedWeightedGraph g2 = new DWG("G2.json");
        g2.removeNode(16);
    }

    @Test
    void removeEdge() throws IOException, ParseException {
        DirectedWeightedGraph g2 = new DWG("G1.json");
        g2.removeNode(0);
        assertEquals(33, g2.edgeSize());
    }

    @Test
    void nodeSize() {
        assertEquals(dwg.nodeSize(), 0);
        dwg.addNode(a);
        dwg.addNode(c);
        assertEquals(dwg.nodeSize(), 2);
        dwg.removeNode(a.getKey());
        assertEquals(dwg.nodeSize(), 1);
    }

    @Test
    void edgeSize() {
        assertEquals(0, dwg.edgeSize());
        dwg.addNode(a);
        dwg.addNode(c);
        dwg.connect(a.getKey(), c.getKey(), 1.278647826423);
        assertEquals(1, dwg.edgeSize());
    }

    @Test
    void getMC() {
        assertEquals(0, dwg.getMC());
        dwg.addNode(a);
        assertEquals(1, dwg.getMC());
        dwg.removeNode(a.getKey());
        assertEquals(2, dwg.getMC());
        dwg.addNode(a);
        dwg.addNode(c);
        dwg.connect(a.getKey(), c.getKey(), 2.0);
        assertEquals(5, dwg.getMC());
        dwg.removeEdge(0, 2);
        assertEquals(6, dwg.getMC());
    }
}