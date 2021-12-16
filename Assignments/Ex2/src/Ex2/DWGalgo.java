package Ex2;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DWGalgo implements DirectedWeightedGraphAlgorithms {
    DirectedWeightedGraph graph;



    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        DirectedWeightedGraph copy_graph = new DWG();
        Iterator<NodeData> itr_node = this.graph.nodeIter();
        while(itr_node.hasNext()) {
            NodeData temp_node = new GNode(itr_node.next());
            copy_graph.addNode(temp_node);
        }
        Iterator<NodeData> itr = this.graph.nodeIter();
        while(itr.hasNext()){
            NodeData temp = itr.next();
            Iterator<EdgeData> itr_edge = this.graph.edgeIter(temp.getKey());
            while(itr_edge.hasNext()) {
                EdgeData temp_edge = new Edge(itr_edge.next());
                copy_graph.connect(temp_edge.getSrc(), temp_edge.getDest(), temp_edge.getWeight());
            }
        }

        return copy_graph;
    }


    @Override
    public boolean isConnected() {
        ArrayList<NodeData> Nlist = new ArrayList<>();
        HashMap<Integer, Integer> nodeMap = new HashMap<>();
        int nodes = 0;
        Iterator<NodeData> itr_node = this.graph.nodeIter();
        while(itr_node.hasNext()){
            NodeData Temp = itr_node.next();
            nodeMap.put(Temp.getKey(), nodes);
            Nlist.add(Temp);
            nodes++;
        }
        boolean[] visited = new boolean[nodes];
        for(int i = 0; i< nodes; i++){
            DFS(this.graph, i, visited, Nlist, nodeMap);
            for (boolean b: visited)
            {
                if (!b) {
                    return false;
                }
            }
        }
        return true;
    }


    /**This function performs DFS traversal on a graph.
     * Return: VOID. */
    void DFS(DirectedWeightedGraph graph, int v, boolean[] visited, ArrayList<NodeData> Nlist, HashMap<Integer, Integer>nodeMap ) {


        // Create a stack for DFS
        Stack<NodeData> stack = new Stack<>();

        // Push the current source node
        stack.push(Nlist.get(v));

        while (!stack.empty()) {
                // Pop a vertex from stack and print it
                v = Nlist.indexOf(stack.peek());
                stack.pop();

                // Stack may contain same vertex twice.
                if (!visited[v]) {
                    visited[v] = true;
                }

                // Get all adjacent vertices of the popped vertex s
                // If a adjacent has not been visited, then push it
                // to the stack.
                Iterator<EdgeData> itr_edge = graph.edgeIter(Nlist.get(v).getKey());

                while (itr_edge.hasNext()) {
                    EdgeData e = itr_edge.next();
                    int edge = nodeMap.get(e.getDest());
                    if (!visited[edge])
                        stack.push(Nlist.get(edge));
                }
        }
    }



    @Override
    public double shortestPathDist(int src, int dest) {
        return find_shortestPathDist(src, dest, this.graph);
    }

    /** This function receives the src, dest and graph. It returns the shortest path between src and dest nodes.
     * Return: Double. */
    private double find_shortestPathDist(int src, int dest, DirectedWeightedGraph graph) {
        double[][] node_matrix = matrix_initializer(graph);
        System.out.println("Starting array: "+Arrays.deepToString(node_matrix));
        int[][] stam = next_matrix_initializer(node_matrix);
        warshall(node_matrix, stam);
        System.out.println("Final array: "+Arrays.deepToString(node_matrix));
        return node_matrix[src][dest];
    }

    /** This is the Floyd Warshall Algorithm, function. It calculates the shortest path between two indexes.
     * Return: VOID. */
    private void warshall(double[][] node_matrix, int[][] next_matrix) {
        int len = node_matrix.length;
        for (int k = 0; k < len; k++)
            for (int i = 0; i < len; i++)
                for (int j = 0; j < len; j++) {
                    if (node_matrix[i][k] == -1.0 || node_matrix[k][j] == -1.0)
                        continue;
                    if (node_matrix[i][j] > node_matrix[i][k] + node_matrix[k][j]) {
                        node_matrix[i][j] = node_matrix[i][k] + node_matrix[k][j];
                        next_matrix[i][j] = next_matrix[i][k];
                    }
                }
    }


    @Override
    public List<NodeData> shortestPath ( int src, int dest){
        double[][] node_matrix = matrix_initializer(this.graph);
        int[][] next_matrix = next_matrix_initializer(node_matrix);
        warshall(node_matrix, next_matrix);
        ArrayList<NodeData> ans = constructPath(src, dest, next_matrix, this.graph);
        printPath(ans);
        return  ans;
    }
    /** This function initializes the next matrix.
     * Return: int[][]. */
    public static int[][] next_matrix_initializer(double[][] node_matrix){
        int len = node_matrix.length;
        int[][] next_matrix = new int[len][len];
        for(int i=0; i<len; i++) {
            for (int j = 0; j < len; j++) {
                if(node_matrix[i][j] == -1.0)
                    next_matrix[i][j] = -1;
                else
                    next_matrix[i][j] = j;
            }
        }
        return  next_matrix;
    }
    /** This function construct the shortest path between u and v.
     * Return: ArrayList<NodeData>. */
    public static ArrayList<NodeData> constructPath(int u, int v, int[][] next_matrix, DirectedWeightedGraph graph) {
        // If there's no path between
        // node u and v, simply return
        // an empty array
        if (next_matrix[u][v] == -1)
            return null;

        // Storing the path in a vector
        Vector<Integer> path = new Vector<Integer>();
        path.add(u);

        while (u != v)
        {
            u = next_matrix[u][v];
            path.add(u);
        }
        ArrayList<NodeData> arr = new ArrayList<>();
        for(int i : path)
            arr.add(graph.getNode(i));
        return arr;
    }
    /** This function initializes a matrix given a graph.
     * Return: Double[][].  */
    public static double[][] matrix_initializer(DirectedWeightedGraph graph){
        //finding the highest Node ID.
        int i, j;
        int max_id = find_Highest_Node(graph);
        max_id++;

        //initializing the matrix:
        Iterator<NodeData> itr2 = graph.nodeIter();
        double[][] node_matrix = new double[max_id][max_id];
        for (i = 0; i < max_id; i++) {
            for (j = 0; j < max_id; j++) {
                //if the nodes don't exist, initialize with -99.0
                try{
                    if(graph.getNode(i)==null || graph.getNode(j)==null){
                        node_matrix[i][j] = -99.0;
                        continue;
                    }
                }
                catch (NullPointerException e){
                    node_matrix[i][j] = -99.0;
                    continue;
                }
                //initializing the main diagonal with zeros:
                if (i == j) { // same node.
                    node_matrix[i][j] = 0.0;
                    continue;
                }
                //initializing the indexes that edges don't exist with Max Value:
                try {
                    if (graph.getEdge(i, j) == null && graph.getNode(i)!=null && graph.getNode(j)!=null) {
                        node_matrix[i][j] = Double.MAX_VALUE;
                        continue;
                    }
                }
                catch (NullPointerException d){
                    node_matrix[i][j] = Double.MAX_VALUE;
                    continue;
                }
                //else the edge exists,  initialize the index with its weight.
                node_matrix[i][j] = graph.getEdge(i, j).getWeight();
            }
        }
        return node_matrix;
    }

    /** Prints the shortest path */
    public static void printPath(ArrayList<NodeData> path) {
        int n = path.size();
        System.out.print("The Shortest path: ");
        for(int i = 0; i < n - 1; i++)
            System.out.print(path.get(i).getKey() + " -> ");
        System.out.print(path.get(n - 1).getKey() + "\n");
    }



    @Override
    public NodeData center () {
        if(this.isConnected()){
            double[][] node_matrix = matrix_initializer(graph);
            int[][] stam = next_matrix_initializer(node_matrix);
            warshall(node_matrix, stam);
            double[] max_nodes_weight = new double[node_matrix.length];
            for(int i=0; i<node_matrix.length; i++){
                double max = 0.0;
                for(int j=0; j<node_matrix.length; j++){
                    if(node_matrix[i][j] > max)
                        max = node_matrix[i][j];
                }
                max_nodes_weight[i] = max;
            }
            double min = Double.MAX_VALUE;
            int index = 0;
            for(int i=0; i<max_nodes_weight.length; i++){
                if(max_nodes_weight[i] < min){
                    min = max_nodes_weight[i];
                    index = i;
                }
            }
            return this.graph.getNode(index);
        }
        else
            return null;
    }

    @Override
    public List<NodeData> tsp (List < NodeData > cities) {
        double[][] matrix = matrix_tsp_initializer(this.graph, cities);
        warshall_tsp(matrix);
        int len = matrix.length;
        boolean[] visted = new boolean[len];
        double ans = Double.MAX_VALUE;
        int t = 1, f = 0, skip = -1;
        visted[0] = true;
        int loop_count = 0;
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(0);
        ans = tsp_helper(matrix, visted, 0, len, 1, 0.0, ans, loop_count, arr);
        arr.add(0);
        List<NodeData> arr_final = new ArrayList<>();
        for(int i : arr){
            arr_final.add(cities.get(i));
        }
        return arr_final;
    }

    /** This function recursively computes the tsp.
     * This function idea was inspired by the 'TSP geekesforgeeks article.
     * Return: Double. */
    public static double tsp_helper(double[][] matrix, boolean[] visted, int currPos, int len, int count, double cost, double ans, int loop_count, ArrayList<Integer> arr){
        if (count == len && matrix[currPos][0] > 0) {
            ans = Math.min(ans, cost + matrix[currPos][0]);
            return ans;
        }
        for(int i=0; i<len; i++){
            if (visted[i] == false && matrix[currPos][i] > 0) {
                // Mark as visited
                visted[i] = true;
                if(arr.get(arr.size()-1) != i)
                    arr.add(i);
                loop_count++;
                ans = tsp_helper(matrix, visted, i, len, count + 1, cost + matrix[currPos][i], ans, loop_count, arr);
                // Mark ith node as unvisited
                visted[i] = false;
            }
        }
        return ans;
    }
    /** This is the Floyd Warshall Algorithm, function. It calculates the shortest path between two indexes.
     * Return: VOID. */
    private void warshall_tsp(double[][] node_matrix) {
        int len = node_matrix.length;
        for (int k = 0; k < len; k++)
            for (int i = 0; i < len; i++)
                for (int j = 0; j < len; j++) {
                    if (node_matrix[i][k] == -99.0 || node_matrix[k][j] == -99.0)
                        continue;
                    if(node_matrix[i][j] == -99.0)
                        node_matrix[i][j] = node_matrix[i][k] + node_matrix[k][j];
                    else
                        if (node_matrix[i][j] > node_matrix[i][k] + node_matrix[k][j]) {
                            node_matrix[i][j] = node_matrix[i][k] + node_matrix[k][j];
                    }
                }
    }

    /** This function initializes the tsp Matrix.
     * Return: double[][]. */
    public static double[][] matrix_tsp_initializer(DirectedWeightedGraph graph, List < NodeData > cities) {
        //finding the highest Node ID.
        int i, j;
        int max_id = 0;
        ArrayList<Integer> key_id = new ArrayList<>();
        for (NodeData n : cities) {
            key_id.add(n.getKey());
            if (n.getKey() > max_id)
                max_id = n.getKey();
        }
        max_id += 1;

        //initializing the starting matrix with -1 and 0.
        double[][] node_matrix = new double[max_id][max_id];
        for (i = 0; i < max_id; i++)
            Arrays.fill(node_matrix[i], -99);//-99 == edge doesn't exist
        for (i = 0; i < max_id; i++) {
            for (j = 0; j < max_id; j++) {
                if (!key_id.contains(i) || !key_id.contains(j)) {
                    node_matrix[i][j] = -1.0; //-1 = node not in list
                } else if (i == j)
                    node_matrix[i][j] = 0.0; // 0 = same node index
            }
        }

        for (i = 0; i < max_id; i++) {
            try{
                Iterator<EdgeData> itr = graph.edgeIter(i);
                while (itr.hasNext()) {
                    EdgeData temp = itr.next();
                    try {
                        if (node_matrix[i][temp.getDest()] != -1.0 && i != temp.getDest())
                            node_matrix[i][temp.getDest()] = temp.getWeight();
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
            }
            catch (NullPointerException ignored) {
            }
        }

        int count = 0;
        for(i = 0; i<node_matrix.length; i++)
            if(node_matrix[0][i] != -1.0)
                count++;
        int k=0;
        double[][] clean_matrix = new double[count][count];
        for(i = 0; i<max_id; i++){
            if(node_matrix[i][0] == -1.0)
                continue;
            int y=0;
            for(j=0; j<max_id; j++){
                if(node_matrix[i][j] != -1.0)
                    clean_matrix[k][y++] = node_matrix[i][j];
            }
            k++;
        }
        return clean_matrix;
    }

    @Override
    public boolean save (String file) {
        JSONObject top = new JSONObject();
        JSONArray edge_arr = new JSONArray();
        top.put("Edges", edge_arr);
        JSONArray node_arr = new JSONArray();
        top.put("Nodes", node_arr);
        Iterator<NodeData> itr_nodeForEdge = this.graph.nodeIter();
        while(itr_nodeForEdge.hasNext()) {
            Iterator<EdgeData> itr_edge = this.graph.edgeIter(itr_nodeForEdge.next().getKey());
            while (itr_edge.hasNext()) {
                EdgeData temp = itr_edge.next();
                JSONObject edge_obj = new JSONObject();
                edge_obj.put("src", temp.getSrc());
                edge_obj.put("w", temp.getWeight());
                edge_obj.put("dest", temp.getDest());
                edge_arr.add(edge_obj);
            }
        }
        Iterator<NodeData> itr_nodes = this.graph.nodeIter();
        while (itr_nodes.hasNext()) {
            NodeData temp = itr_nodes.next();
            JSONObject node_obj = new JSONObject();
            node_obj.put("pos", temp.getLocation().x() + "," + temp.getLocation().y() + "," + temp.getLocation().z());
            node_obj.put("id", temp.getKey());
            node_arr.add(node_obj);
        }
        try (FileWriter f = new FileWriter(file)) {
            //We can write any JSONArray or JSONObject instance to the file
            f.write(top.toJSONString());
            f.flush();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public boolean load (String file){
        try{
            DirectedWeightedGraph graph = new DWG(file);
            this.init(graph);
            return true;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            e.printStackTrace();
            return false;
        }
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
}