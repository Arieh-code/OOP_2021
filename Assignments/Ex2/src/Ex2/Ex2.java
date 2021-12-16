package Ex2;

import Ex2.GUI.GuiFrame;
import GUI.Window;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * This class is the main class for Ex2.Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGraph(String json_file) throws IOException, ParseException {
        DirectedWeightedGraph ans = null;
        // ****** Add your code here ******
        ans = new DWG(json_file);
        // ********************************
        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGraphAlgo(String json_file) throws IOException, ParseException {
        DirectedWeightedGraphAlgorithms ans = null;
        // ****** Add your code here ******
        ans = new DWGalgo();
        ans.load(json_file);
        // ********************************
        return ans;
    }
    /**
     * This static function will run your GUI using the json file.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) throws IOException, ParseException {
        DirectedWeightedGraphAlgorithms alg = getGraphAlgo(json_file);
        // ****** Add your code here ******
        new GuiFrame(alg);
        // ********************************
    }

    public static void main(String[] args) throws IOException, ParseException {
        getGraphAlgo(args[0]);
        getGraph(args[0]);
        runGUI(args[0]);
    }
}