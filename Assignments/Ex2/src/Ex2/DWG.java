package Ex2;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import api.EdgeData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.w3c.dom.traversal.NodeIterator;

import java.io.IOException;
import java.util.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

public class DWG implements DirectedWeightedGraph {
    public HashMap<Integer, NodeData> nodeMap;
    public HashMap<Integer, HashMap<Integer, EdgeData>> edgeMap;
    int MC = 0;

    /** Constructor. */
    public DWG(){
        this.nodeMap = new HashMap<>();
        this.edgeMap = new HashMap<>();
    }

    /** Json File Constructor. */
    public DWG(String json) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        //Parsing the contents of the JSON file
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(json));
        this.nodeMap = create_nodeMap(jsonObject);
        this.edgeMap = create_edgeMap(jsonObject);
    }

    /** This function receives a JSON file and initializes the  edgeMap of the DWG.
     * Return: HashMap<Integer, HashMap<Integer, EdgeData>>. */
    private HashMap<Integer, HashMap<Integer, EdgeData>> create_edgeMap(JSONObject json_OBJ) throws IOException, ParseException {
        //initializing a new HashMap<Integer, HashMap<Integer, EdgeData>>.
        HashMap<Integer, HashMap<Integer, EdgeData>> edgeMap = new HashMap<>();
        JSONArray jsonArray_Edges = (JSONArray) json_OBJ.get("Edges");

        //looping through the json file (Edges).
        for (int i=0; i<jsonArray_Edges.size(); i++){

            //creating the edge.
            String str = jsonArray_Edges.get(i).toString();
            String[] arr = str.split("[:,]");
            int src = Integer.parseInt(arr[1]);
            double w = Double.parseDouble(arr[3]);
            int dest = Integer.parseInt(arr[5].substring(0, arr[5].length()-1));
            EdgeData temp = new Edge(src, w, dest);

            //if the edgeMap does not contain a key of value 'src', create one.
            if(edgeMap.get(src)==null){
                HashMap<Integer, EdgeData> edge = new HashMap<>();
                edgeMap.put(src, edge);
            }
            edgeMap.get(src).put(dest, temp);
        }
        return edgeMap;
    }

    /** This function receives a JSON file and initializes the  nodeMap of the DWG.
     * Return: HashMap<Integer, NodeData>. */
    private HashMap<Integer, NodeData> create_nodeMap(JSONObject json_OBJ) throws IOException, ParseException {
        //creating a new HashMap<Integer, NodeData>.
        HashMap<Integer, NodeData> nodeMap = new HashMap<>();
        JSONArray jsonArray_Nodes = (JSONArray) json_OBJ.get("Nodes");

        //looping through the json file (Nodes).
        for (int i=0; i<jsonArray_Nodes.size(); i++) {

            //creating a new NodeData.
            String str = jsonArray_Nodes.get(i).toString();
            String[] arr = str.split("[:,]");
            double x = Double.parseDouble(arr[1].substring(1));
            double y = Double.parseDouble(arr[2]);
            double z = Double.parseDouble(arr[3].substring(0, arr[3].length()-1));
            int id = Integer.parseInt(arr[5].substring(0, arr[5].length()-1));
            Point3D p = new Point3D(x, y, z);
            NodeData n = new GNode(id, p);

            //adding the new NodeData to the nodeMap.
            nodeMap.put(n.getKey(), n);
        }
        return nodeMap;
    }


    @Override
    public NodeData getNode(int key) {
        return this.nodeMap.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return this.edgeMap.get(src).get(dest);
    }

    @Override
    public void addNode(NodeData n) {
        this.nodeMap.put(n.getKey(), n);
        HashMap<Integer, EdgeData> map = new HashMap<>();
        this.edgeMap.put(n.getKey(), map);
        this.MC ++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        try{
            EdgeData temp = new Edge(src, w, dest);
            this.edgeMap.get(src).put(dest, temp);
            this.MC++;
        }
        catch (NullPointerException e){
            System.out.println("The Map doesn't contain the src node!");
        }
    }


    @Override
    public Iterator<NodeData> nodeIter() {
        return new NodeIterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return new EdgeIterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return new edgeIterNode(node_id);
    }

    @Override
    public NodeData removeNode(int key) {
        int size = find_Highest_Node(this);
        NodeData temp = new GNode(this.nodeMap.get(key).getKey(), this.nodeMap.get(key).getLocation());
        this.edgeMap.remove(key);
        this.nodeMap.remove(key);
        for (NodeData n : this.nodeMap.values()) {
            for(int i = 0; i < size; i++){
                try{
                    EdgeData temp_edge = this.edgeMap.get(n.getKey()).get(i);
                if(temp_edge.getDest() == key){
                    this.removeEdge(temp_edge.getSrc(), temp_edge.getDest());
                }
            }
                catch (NullPointerException ignored){
                }
                }
        }
//        this.nodeMap.remove(key);
        this.MC++;
        return temp;
        }


    @Override
    public EdgeData removeEdge(int src, int dest) {
        EdgeData temp = new Edge(src, this.edgeMap.get(src).get(dest).getWeight(), dest);
        this.edgeMap.get(src).remove(dest);
        this.MC++;
        return temp;
    }

    @Override
    public int nodeSize() {
        return this.nodeMap.size();
    }

    @Override
    public int edgeSize() {
        int counter = 0;
        for(HashMap<Integer, EdgeData> map : this.edgeMap.values())
            counter += map.size();
        return counter;
    }

    @Override
    public int getMC() {
        return this.MC;
    }
    /** This function loops through a given graph and return the highest NodeData key.
     * Return: Integer. */
    public static int find_Highest_Node(DirectedWeightedGraph graph){
        //finding the highest Node ID.
        int i, j;
        int max_id = 0;
        Iterator<NodeData> itr = graph.nodeIter();
        while (itr.hasNext()) {
            NodeData temp = itr.next();
            if (temp.getKey() > max_id)
                max_id = temp.getKey();
        }
        return max_id;
    }

    private class NodeIterator implements Iterator<NodeData>{
        private int currMC;
        private Iterator<NodeData> itr_node;
        private NodeData temp;

        public  NodeIterator(){
            this.currMC = MC;
            itr_node = nodeMap.values().iterator();
        }

        @Override
        public boolean hasNext() {
            if(this.currMC != MC){
                throw new RuntimeException("Graph has changed since the iterator was constructed");
            }
            return itr_node.hasNext();
        }

        @Override
        public NodeData next() {
            if(this.currMC != MC){
                throw new RuntimeException("Graph has changed since the iterator was constructed");
            }
            temp = itr_node.next();
            return temp;
        }

        @Override
        public void remove() {
            if(this.currMC != MC){
                throw new RuntimeException("Graph has changed since the iterator was constructed");
            }
            currMC++;
            itr_node.remove();
            removeNode(temp.getKey());
        }


        @Override
        public void forEachRemaining(Consumer<? super NodeData> action) {
            Iterator.super.forEachRemaining(action);
        }

    }

    private class EdgeIterator implements Iterator<EdgeData>{
        private int Edgemc;
        private Iterator<EdgeData> itr_edge;
        private EdgeData temp_edge;

        public EdgeIterator(){
            this.Edgemc = MC;;
            HashMap<Integer, EdgeData> new_map = new HashMap<>();
            for(HashMap<Integer, EdgeData> map : edgeMap.values())
                for(EdgeData edge : map.values())
                    new_map.put(edge.getDest(), edge);
            this.itr_edge = new_map.values().iterator();
        }

        @Override
        public boolean hasNext() {
            if(this.Edgemc != MC){
                throw new RuntimeException("Graph has changed since the iterator was constructed");
            }
            return itr_edge.hasNext();
        }

        @Override
        public EdgeData next() {
            if(this.Edgemc != MC){
                throw new RuntimeException("Graph has changed since the iterator was constructed");
            }
            this.temp_edge = itr_edge.next();
            return this.temp_edge;
        }

        @Override
        public void remove() {
            if(this.Edgemc != MC){
                throw new RuntimeException("Graph has changed since the iterator was constructed");
            }
            Edgemc++;
            itr_edge.remove();
            removeEdge(temp_edge.getSrc(), temp_edge.getDest());
        }

        @Override
        public void forEachRemaining(Consumer<? super EdgeData> action) {
            Iterator.super.forEachRemaining(action);
        }
    }

    private class edgeIterNode implements Iterator<EdgeData>{
        private int Edgemc;
        private Iterator<EdgeData> itr_edge;
        private EdgeData temp_edge;

        public edgeIterNode(int GNodeid){
            this.Edgemc = MC;
            this.itr_edge = edgeMap.get(GNodeid).values().iterator();
        }

        @Override
        public boolean hasNext() {
            if(this.Edgemc != MC){
                throw new RuntimeException("Graph has changed since the iterator was constructed");
            }
            return this.itr_edge.hasNext();
        }

        @Override
        public EdgeData next() {
            if(this.Edgemc != MC){
                throw new RuntimeException("Graph has changed since the iterator was constructed");
            }
            this.temp_edge = itr_edge.next();
            return this.temp_edge;
        }

        @Override
        public void remove() {
            if(this.Edgemc != MC){
                throw new RuntimeException("Graph has changed since the iterator was constructed");
            }
            Edgemc++;
            itr_edge.remove();
            removeEdge(temp_edge.getSrc(), temp_edge.getDest());
        }

        @Override
        public void forEachRemaining(Consumer<? super EdgeData> action) {
            Iterator.super.forEachRemaining(action);
        }
    }
}