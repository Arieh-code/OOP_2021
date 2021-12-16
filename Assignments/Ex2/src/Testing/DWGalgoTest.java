package Testing;

import Ex2.*;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGalgoTest {

    Point3D p1 = new Point3D(35.19589389346247, 32.10152879327731, 0.0);
    Point3D p2 = new Point3D(35.20319591121872, 32.10318254621849, 0.0);
    Point3D p3 = new Point3D(35.20752617756255, 32.1025646605042, 0.0);
    Point3D p4 = new Point3D(35.21007339305892, 32.10107446554622, 0.0);
    Point3D p5 = new Point3D(35.21310882485876, 32.104636394957986, 0.0);

    NodeData a = new GNode(0, p1);
    NodeData b = new GNode(1, p2);
    NodeData c = new GNode(2, p3);
    NodeData d = new GNode(3, p4);
    NodeData e = new GNode(4, p5);



    @Test
    void init() throws IOException, ParseException {
        DirectedWeightedGraph g = new DWG("G1.json");
        DirectedWeightedGraph g1 = new DWG("G2.json");
        DirectedWeightedGraph g2 = new DWG("G3.json");
        DirectedWeightedGraph g3 = new DWG("1000Nodes.json");
        DirectedWeightedGraph g4 = new DWG("10000Nodes.json");
        DirectedWeightedGraph g5 = new DWG("100000.json");
        DWGalgo test = new DWGalgo();
        test.init(g);
        test.init(g1);
        test.init(g2);
        test.init(g3);
        test.init(g4);
        test.init(g5);
    }

    @Test
    void getGraph() {
        DirectedWeightedGraphAlgorithms test = new DWGalgo();
        DirectedWeightedGraph g = new DWG();
        NodeData a = new GNode(0, p1);
        g.addNode(a);
        test.init(g);
        assertNotNull(test.getGraph());
        assertEquals(1, test.getGraph().nodeSize());
    }

    @Test
    void copy() {
        DirectedWeightedGraphAlgorithms graph = new DWGalgo();
        graph.load("G1.json");
        DirectedWeightedGraph copyG = graph.copy();
        assertEquals(graph.getGraph().nodeSize(), copyG.nodeSize());
        assertEquals(graph.getGraph().edgeSize(), copyG.edgeSize());
        graph.getGraph().removeNode(1);
        assertNotSame(graph.getGraph().nodeSize(), copyG.nodeSize());
    }

    @Test
    void isConnected() throws IOException, ParseException {
        DirectedWeightedGraph g = new DWG("G1.json");
        DirectedWeightedGraph g1 = new DWG("G2.json");
        DirectedWeightedGraph g2 = new DWG("G3.json");
        DirectedWeightedGraph g3 = new DWG("1000Nodes.json");
        DirectedWeightedGraph g4 = new DWG("10000Nodes.json");
        DirectedWeightedGraph g5 = new DWG("100000.json");
        DWGalgo test = new DWGalgo();
        DWGalgo test1 = new DWGalgo();
        DWGalgo test2 = new DWGalgo();
        DWGalgo test3 = new DWGalgo();
        DWGalgo test4 = new DWGalgo();
        DWGalgo test5 = new DWGalgo();
        test.init(g);
        test1.init(g1);
        test2.init(g2);
        test3.init(g3);
        test4.init(g4);
        test5.init(g5);
        long startTime = System.nanoTime();
        assertTrue(test.isConnected());
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("G1 is connected time: " +duration/60000000.0);
        startTime = System.nanoTime();
        assertTrue(test1.isConnected());
         endTime = System.nanoTime();
         duration = (endTime - startTime);
        System.out.println("G2 is connected time: " +duration/60000000.0);
         startTime = System.nanoTime();
        assertTrue(test2.isConnected());
         endTime = System.nanoTime();
         duration = (endTime - startTime);
        System.out.println("G3 is connected time: " +duration/60000000.0);
         startTime = System.nanoTime();
        assertTrue(test3.isConnected());
         endTime = System.nanoTime();
         duration = (endTime - startTime);
        System.out.println("1000Nodes is connected time: " +duration/60000000.0);
         startTime = System.nanoTime();
        assertTrue(test4.isConnected());
         endTime = System.nanoTime();
         duration = (endTime - startTime);
        System.out.println("10000 Nodes is connected time: " +duration/60000000.0);
         startTime = System.nanoTime();
        assertTrue(test5.isConnected());
         endTime = System.nanoTime();
         duration = (endTime - startTime);
        System.out.println("100000 Nodes is connected time: " +duration/60000000.0);
    }

    @Test
    void shortestPathDist() {
        DirectedWeightedGraph g = new DWG();
        DWGalgo test = new DWGalgo();
        test.init(g);
        test.getGraph().addNode(a);
        test.getGraph().addNode(b);
        test.getGraph().addNode(c);
        test.getGraph().addNode(d);
        test.getGraph().connect(0, 1, 3.0);
        test.getGraph().connect(0, 3, 7.0);
        test.getGraph().connect(1, 0, 8.0);
        test.getGraph().connect(1, 2, 2.0);
        test.getGraph().connect(2, 0, 5.0);
        test.getGraph().connect(2, 3, 1.0);
        test.getGraph().connect(3, 0, 2.0);
        assertEquals(7.0, test.shortestPathDist(3, 2));
    }

    @Test
    void shortestPath() {
        DirectedWeightedGraph g = new DWG();
        DWGalgo test = new DWGalgo();
        test.init(g);
        test.getGraph().addNode(a);
        test.getGraph().addNode(b);
        test.getGraph().addNode(c);
        test.getGraph().addNode(d);
        test.getGraph().addNode(e);
        test.getGraph().connect(0, 1, 3.0);
        test.getGraph().connect(0, 3, 7.0);
        test.getGraph().connect(1, 0, 8.0);
        test.getGraph().connect(1, 2, 2.0);
        test.getGraph().connect(2, 0, 5.0);
        test.getGraph().connect(2, 3, 1.0);
        test.getGraph().connect(3, 0, 2.0);
        test.shortestPath(3, 2);
        test.shortestPath(0, 2);
        test.shortestPath(1, 3);
    }

    @Test
    void center() throws IOException, ParseException {
        DirectedWeightedGraph g = new DWG("G1.json");
        DirectedWeightedGraph g1 = new DWG("G2.json");
        DirectedWeightedGraph g2 = new DWG("G3.json");
        DirectedWeightedGraph g3 = new DWG("1000Nodes.json");
        DirectedWeightedGraph g4 = new DWG("10000Nodes.json");
        DirectedWeightedGraph g5 = new DWG("100000.json");
        DWGalgo test = new DWGalgo();
        DWGalgo test1 = new DWGalgo();
        DWGalgo test2 = new DWGalgo();
        DWGalgo test3 = new DWGalgo();
        DWGalgo test4 = new DWGalgo();
        DWGalgo test5 = new DWGalgo();
        test.init(g);
        test1.init(g1);
        test2.init(g2);
        test3.init(g3);
        test4.init(g4);
        test5.init(g5);
        long startTime = System.nanoTime();
        int x = test.center().getKey();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("G1 center it : "+ x + " Time duration: " + duration/1000000000.0);
        startTime = System.nanoTime();
        x = test1.center().getKey();
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("G2 center it : "+ x + " Time duration: " + duration/1000000000.0);
        startTime = System.nanoTime();
        x = test2.center().getKey();
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("G3 center it : "+ x + " Time duration: " + duration/1000000000.0);
        startTime = System.nanoTime();
        x = test3.center().getKey();
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("1000 Nodes center it : "+ x + " Time duration: " + duration/1000000000.0);
        startTime = System.nanoTime();
        x = test4.center().getKey();
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("10000 Nodes center it : "+ x + " Time duration: " + duration/1000000000.0);
    }

    @Test
    void tsp() {
        DirectedWeightedGraph g = new DWG();
        DWGalgo test = new DWGalgo();
        test.init(g);
        test.getGraph().addNode(a);
        test.getGraph().addNode(b);
        test.getGraph().addNode(c);
        test.getGraph().addNode(d);
        test.getGraph().addNode(e);
        test.getGraph().connect(0, 1, 3.0);
        test.getGraph().connect(0, 4, 7.0);
        test.getGraph().connect(1, 0, 8.0);
        test.getGraph().connect(1, 2, 2.0);
        test.getGraph().connect(2, 0, 5.0);
        test.getGraph().connect(2, 4, 1.0);
        test.getGraph().connect(4, 0, 2.0);
        List<NodeData> ll = new ArrayList<>();
//        ll.add(c);
        ll.add(a); //0 //0
        ll.add(b); //1 //1
        ll.add(e);//3 //2
        DWGalgo.matrix_tsp_initializer(test.getGraph(), ll);
        test.tsp(ll);


    }

    @Test
    void save() throws IOException, ParseException {
        DirectedWeightedGraph g = new DWG("G1.json");
        DWGalgo test = new DWGalgo();
        test.init(g);
        assertTrue(test.save("json_array_output.json"));
        DirectedWeightedGraph g1 = new DWG("json_array_output.json");
        DWGalgo test1 = new DWGalgo();
        test1.init(g1);
        assertEquals(test.getGraph().edgeSize(), test1.getGraph().edgeSize());
        assertEquals(test.getGraph().nodeSize(), test1.getGraph().nodeSize());
    }

    @Test
    void load() throws IOException, ParseException {
        String file = "json_array_output.json";
        DirectedWeightedGraphAlgorithms test1 = new DWGalgo();
        assertTrue(test1.load(file));
    }
}