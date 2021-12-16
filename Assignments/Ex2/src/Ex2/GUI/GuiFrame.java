package Ex2.GUI;

import Ex2.DWG;
import Ex2.DWGalgo;
import Ex2.GNode;
import Ex2.Point3D;
import GUI.progress_bar;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class GuiFrame extends JFrame implements ActionListener {
    private DirectedWeightedGraphAlgorithms algorithm;
    MyPanel panel;
    G1Panel panel1;
    shortest_dist SPanel;
    DirectedWeightedGraphAlgorithms refresh;

    public GuiFrame(DirectedWeightedGraphAlgorithms algo){
        super();
        this.algorithm = algo;
        this.refresh = new DWGalgo();
        refresh.init(algo.copy());
        panel1 = new G1Panel(algorithm.getGraph());

        this.add(panel1);

        initialize_frame_specs();
        initialize_menu_bar();


        this.setLayout(new FlowLayout());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


    /** This functions initializes the JFrame specs.
     * Return: VOID. */
    private void initialize_frame_specs(){
        this.setTitle("Ex2 GUI");
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setLayout(null);//using no layout managers
        //exit GUI when pressing the close button.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /** This functions initializes the Menu Bar.
     * Return: VOID. */
    private void initialize_menu_bar() {
        //initializing the 'Menu-Bar'.
        JMenuBar menu_bar = new JMenuBar();

        //initializing the Menu button.
        JMenu menu_button, help_button;
        menu_button  = new JMenu("Menu");
        help_button = new JMenu("Help");

        //initializing the Menu-buttons:
        JMenuItem save, clean, load, edit, algo_actions, refresh_graph;
        save = new JMenuItem("Save Graph");
        save.addActionListener(this);
        clean = new JMenuItem("Clean Graph");
        clean.addActionListener(this);
        //submenus of Menus-buttons:
        load = new JMenu("Load Graph");
        edit = new JMenu("Edit Graph");
        algo_actions = new JMenu("Algorithms");
        refresh_graph = new JMenuItem("Refresh Graph");
        refresh_graph.addActionListener(this);
        // Load
        //initializing the 'Load" submenu options:
        JMenuItem add_path;
        add_path = new JMenuItem("Add Path");
        add_path.addActionListener(this);
        //adding the items to the 'Load' option:
        load.add(add_path);

        // Edit
        //initializing the 'Edit" submenu options:
        //initializing the submenus of "Edit:
        JMenu node_actions, edge_actions;
        node_actions = new JMenu("Node Actions");//Edit-submenu
        edge_actions = new JMenu("Edge Actions");//Edit-submenu
        //initializing the 'node_actions" and "edge_actions" submenu options:
        JMenuItem addNode, connect, removeNode, removeEdge;
        addNode = new JMenuItem("Add Node");
        addNode.addActionListener(this);
        connect = new JMenuItem("Connect");
        connect.addActionListener(this);
        removeNode = new JMenuItem("Remove Node");
        removeNode.addActionListener(this);
        removeEdge = new JMenuItem("Remove Edge");
        removeEdge.addActionListener(this);
        //adding the items to the submenus of "Edit:
        node_actions.add(addNode);
        node_actions.add(removeNode);
        edge_actions.add(connect);
        edge_actions.add(removeEdge);
        //adding the submenus of "Edit" to Edit:
        edit.add(node_actions);
        edit.add(edge_actions);

        // Algorithms
        //initializing the "Algorithms" items:
        JMenuItem isConnected, shortestPath, center, tsp;
        isConnected = new JMenuItem("Is Connected?");
        isConnected.addActionListener(this);
        shortestPath = new JMenuItem("Shortest Path");
        shortestPath.addActionListener(this);
        center = new JMenuItem("Center");
        center.addActionListener(this);
        tsp = new JMenuItem("Travelling Salesman Problem");
        tsp.addActionListener(this);
        //adding the items to the submenus of "Algorithms":
        algo_actions.add(isConnected);
        algo_actions.add(shortestPath);
        algo_actions.add(center);
        algo_actions.add(tsp);

        //adding the Menu scroller items to the Menu Scroller:
        menu_button.add(load);
        menu_button.add(save);
        menu_button.add(edit);
        menu_button.add(algo_actions);
        menu_button.add(refresh_graph);
        menu_button.addSeparator();
        menu_button.add(clean);

        //adding the button to the Menu Bar:
        menu_bar.add(menu_button);
        menu_bar.add(help_button);

        //adding the Menu Bar to the Frame:
        this.add(menu_bar);
        this.setJMenuBar(menu_bar);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        if (str.equals("Add Path")) {
            String file = JOptionPane.showInputDialog("Please enter the new path in the box below.");
            try {
                add_path_func(file);
            } catch (IOException | ParseException ex) {
                ex.printStackTrace();
            }
        }
        if(str.equals("Save Graph")){
            save_graph();
        }
        if (str.equals("Add Node")) {
            add_Node();
        }
        if (str.equals("Remove Node")) {
            remove_Node();
        }
        if (str.equals("Connect")) {
            connect_Edge();
        }
        if (str.equals("Remove Edge")) {
            remove_Edge();
        }
        if (str.equals("Is Connected?")) {
            is_connected();
        }
        if (str.equals("Shortest Path")) {
            shortest_path();
        }
        if (str.equals("Center")) {
            center_find();
        }
        if (str.equals("Travelling Salesman Problem")) {
            tsp_find();
        }
        if(str.equals("Refresh Graph")){
            refreshG();
        }
        if(str.equals("Clean Graph")){
            this.getContentPane().removeAll();
            this.repaint();
        }
    }

    private void refreshG(){
        this.getContentPane().removeAll();
        this.add(new MyPanel(refresh.getGraph()));
        SwingUtilities.updateComponentTreeUI(this);
        JPanel myPanel = new JPanel();
        JOptionPane.showMessageDialog(myPanel, "Refresh Complete", "Refreshed page", JOptionPane.INFORMATION_MESSAGE );
    }

    //works...No need to touch
    private void is_connected() {
        JPanel myPanel = new JPanel();
        if(this.algorithm.isConnected()){
            JOptionPane.showMessageDialog(myPanel, "The Graph is connected", "", JOptionPane.INFORMATION_MESSAGE );
        }
        else{
            JOptionPane.showMessageDialog(myPanel, "The Graph is not connected", "", JOptionPane.INFORMATION_MESSAGE );
        }

    }

    //need to finish show on graph the center node
    private void center_find() {
        NodeData center_node = this.algorithm.center();
        this.getContentPane().removeAll();
        this.add(new center_panel(this.algorithm.getGraph(), center_node));
        SwingUtilities.updateComponentTreeUI(this);
        JPanel myPanel = new JPanel();
        JOptionPane.showMessageDialog(myPanel, "The center is: " + center_node.getKey(), "", JOptionPane.INFORMATION_MESSAGE );
    }

    private void tsp_find() {
        ArrayList<NodeData> ans_list = new ArrayList<>();
        int max_node = find_Highest_Node(this.algorithm.getGraph());
        JTextField nodeField = new JTextField(7);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Node Key:"));
        myPanel.add(nodeField);
        int result = -99;
            while (true) {
                String[] arr = {"Ok", "Done", "Cancel"};
                result = JOptionPane.showOptionDialog(null, myPanel,
                        "Please enter a Node. If finished press Done.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, arr, 0);
                if (result == -1) // the user pressed x.
                    break;
                if (result == 0) {
                    int key = Integer.parseInt(nodeField.getText());
                    if (key < 0 || key > max_node) {
                        JOptionPane.showMessageDialog(myPanel, "THE NODE DOES NOT EXIST!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        ans_list.add(this.algorithm.getGraph().getNode(key));
                        nodeField.setText("");
                    }
                    continue;
                }
                if (result == 1) {//the user pressed done
                    if (!nodeField.getText().equals("")) {
                        int key = Integer.parseInt(nodeField.getText());
                        if (key < 0 || key > max_node) {
                            JOptionPane.showMessageDialog(myPanel, "THE NODE DOES NOT EXIST!", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            ans_list.add(this.algorithm.getGraph().getNode(key));
                            nodeField.setText("");
                        }
                    }
                break;
                }
                if(result==2) { //the user pressed cancel and wants to exit the panel.
                    myPanel.setVisible(false);
                    break;
                }
            }
        //continue here...
        // ans_list (ArrayList<Nodedata>)
        if(!ans_list.isEmpty()) {
            this.getContentPane().removeAll();
            this.add(new tsp(ans_list, this.algorithm.getGraph()));
            SwingUtilities.updateComponentTreeUI(this);
        }
        }





    private void shortest_path() {
        double ans_dist = 0.0;
        ArrayList<NodeData> ans_list = new ArrayList<>();

        JTextField srcField = new JTextField(7);
        JTextField destField = new JTextField(7);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Source:"));
        myPanel.add(srcField);
        myPanel.add(new JLabel("Destination:"));
        myPanel.add(destField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter the Source and Destination of the path.", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            while (true) {
                if(result != JOptionPane.OK_OPTION)
                    break;
                int src = Integer.parseInt(srcField.getText());
                int dest = Integer.parseInt(destField.getText());
                try {
                    ans_dist = this.algorithm.shortestPathDist(src, dest);
                    ans_list = (ArrayList<NodeData>) this.algorithm.shortestPath(src, dest);
                    break; //if input was correct breaks out of loop.
                }
                //cathces if the src or dest are not in range.
                catch (ArrayIndexOutOfBoundsException e) {
                    if(this.algorithm.getGraph().getNode(src)==null)
                        JOptionPane.showMessageDialog(myPanel, "THE SOURCE NODE DOES NOT EXIST!", "Warning", JOptionPane.WARNING_MESSAGE);
                    else if(this.algorithm.getGraph().getNode(dest)==null)
                        JOptionPane.showMessageDialog(myPanel, "THE DESTINATION NODE DOES NOT EXIST!", "Warning", JOptionPane.WARNING_MESSAGE);
                    result = JOptionPane.showConfirmDialog(null, myPanel, "Please enter the Source and Destination of the path.", JOptionPane.OK_CANCEL_OPTION);
                }
            }
            // If the user input was successful continue here.
            // ans_dist = (Double)
            // ans_list = (ArrayList<NodeData)
            this.getContentPane().removeAll();
            this.add(new shortest_dist(algorithm.getGraph(), ans_list, ans_dist ));
            SwingUtilities.updateComponentTreeUI(this);
            JOptionPane.showMessageDialog(myPanel, "The shortest dist = " + ans_dist, "", JOptionPane.INFORMATION_MESSAGE );
        }
    }


    private void save_graph() {
        String path = "";
        JTextField fileField = new JTextField(7);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("File name (Must end with .json):"));
        myPanel.add(fileField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter the file name below", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            boolean b = true;
            while(b && result == JOptionPane.OK_OPTION){
                if(!fileField.getText().contains(".json")){
                    JOptionPane.showMessageDialog(myPanel, "Need to add '.json'  at the end!", "Warning", JOptionPane.WARNING_MESSAGE);
                    JOptionPane.showConfirmDialog(null, myPanel, "Please enter Node to be removed below", JOptionPane.OK_CANCEL_OPTION);
                }
                else if(fileField.getText().substring(0, fileField.getText().indexOf('.')).length()==0){
                    JOptionPane.showMessageDialog(myPanel, "Need to add text before the '.json'!", "Warning", JOptionPane.WARNING_MESSAGE);
                    JOptionPane.showConfirmDialog(null, myPanel, "Please enter Node to be removed below", JOptionPane.OK_CANCEL_OPTION);
                }
                else {
                    JOptionPane.showMessageDialog(myPanel, "Save Complete", "", JOptionPane.INFORMATION_MESSAGE );
                    path = fileField.getText();
                    b = false;
                }
            }
        }
        this.algorithm.save(path);
    }



    private void remove_Edge() {

        JTextField srcField = new JTextField(7);
        JTextField destField = new JTextField(7);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Source:"));
        myPanel.add(srcField);
        myPanel.add(new JLabel("Destination:"));
        myPanel.add(destField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter Edge Source and Destination to be removed below", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            boolean b = true;
            while (b) {
                int src = Integer.parseInt(srcField.getText());
                int dest = Integer.parseInt(destField.getText());
                try {
                    this.algorithm.getGraph().removeEdge(src, dest);
                    JOptionPane.showMessageDialog(myPanel, "Delete Complete", "", JOptionPane.INFORMATION_MESSAGE );
                    b = false;
                }
                catch (NullPointerException e) {
                    if(this.algorithm.getGraph().getNode(src)==null)
                        JOptionPane.showMessageDialog(myPanel, "THE SOURCE NODE DOES NOT EXIST!", "Warning", JOptionPane.WARNING_MESSAGE);
                    else if(this.algorithm.getGraph().getNode(dest)==null)
                        JOptionPane.showMessageDialog(myPanel, "THE DESTINATION NODE DOES NOT EXIST!", "Warning", JOptionPane.WARNING_MESSAGE);
                    JOptionPane.showConfirmDialog(null, myPanel, "Please enter Node to be removed below", JOptionPane.OK_CANCEL_OPTION);
                }
            }
        }
        this.getContentPane().removeAll();
        this.add(new MyPanel(algorithm.getGraph()));
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void remove_Node() {
        JTextField KeyField = new JTextField(7);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Key:"));
        myPanel.add(KeyField);
        System.out.println("nodes before: "+algorithm.getGraph().nodeSize());
        System.out.println("edges before : "+algorithm.getGraph().edgeSize());
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter Node to be removed below", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            while (true) {
                if(result != JOptionPane.OK_OPTION)
                    break;
                int key = Integer.parseInt(KeyField.getText());
                try {
                    this.algorithm.getGraph().getNode(key);
                    this.algorithm.getGraph().removeNode(key);
                    System.out.println("nodes after: " + algorithm.getGraph().nodeSize());
                    System.out.println("edges after : " + algorithm.getGraph().edgeSize());
                    break;

                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(myPanel, "NODE DOES NOT EXIST!", "Warning", JOptionPane.WARNING_MESSAGE);
                    result = JOptionPane.showConfirmDialog(null, myPanel,
                            "Please enter Node to be removed below", JOptionPane.OK_CANCEL_OPTION);
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(myPanel, "Please enter a number!", "Warning", JOptionPane.WARNING_MESSAGE);
                    result = JOptionPane.showConfirmDialog(null, myPanel,
                            "Please enter Node to be removed below", JOptionPane.OK_CANCEL_OPTION);
                }
                catch (ConcurrentModificationException e){
                    JOptionPane.showMessageDialog(myPanel, "Delete Complete", "", JOptionPane.INFORMATION_MESSAGE );
                    break;
                }

            }
        }
        this.getContentPane().removeAll();
        this.add(new MyPanel(algorithm.getGraph()));
        SwingUtilities.updateComponentTreeUI(this);
    }


    private void connect_Edge() {
        JTextField srcField = new JTextField(4);
        JTextField destField = new JTextField(4);
        JTextField weightField = new JTextField(4);


        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Source:"));
        myPanel.add(srcField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Destination:"));
        myPanel.add(destField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Weight:"));
        myPanel.add(weightField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please add values to connect below", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            this.algorithm.getGraph().connect(Integer.parseInt(srcField.getText()), Integer.parseInt(destField.getText()), Double.parseDouble(weightField.getText()));
        }
        this.getContentPane().removeAll();
        this.add(new MyPanel(algorithm.getGraph()));
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void add_Node() {
        JTextField xField = new JTextField(4);
        JTextField yField = new JTextField(4);
        JTextField zField = new JTextField(4);
        JTextField KeyField = new JTextField(4);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("x:"));
        myPanel.add(xField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("y:"));
        myPanel.add(yField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("z:"));
        myPanel.add(zField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Key:"));
        myPanel.add(KeyField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please add new Node Values below", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try{
                Point3D p = new Point3D(Double.parseDouble(xField.getText()),Double.parseDouble(yField.getText()),Double.parseDouble(KeyField.getText()));
                NodeData node = new GNode(Integer.parseInt(KeyField.getText()), p);
                this.algorithm.getGraph().addNode(node);
                System.out.println("x value: " + xField.getText());
                System.out.println("y value: " + yField.getText());
                System.out.println("z value: " + zField.getText());
                System.out.println("Key value: " + KeyField.getText());
                System.out.println(this.algorithm.getGraph().getNode(node.getKey()));
            }
            catch (Exception e){
                System.out.println("Enter the values properly!");
            }
        }
        this.getContentPane().removeAll();
        this.add(new MyPanel(algorithm.getGraph()));
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void add_path_func(String file) throws IOException, ParseException {
        DirectedWeightedGraph graph = new DWG(file);
        algorithm.init(graph);
        refresh.init(algorithm.copy());
        this.getContentPane().removeAll();
        this.add(new MyPanel(algorithm.getGraph()));
        SwingUtilities.updateComponentTreeUI(this);

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

    public static void main(String[] args) throws IOException, ParseException {
        String g1 = "G1.json";
        DirectedWeightedGraphAlgorithms algo = Ex2.Ex2.getGraphAlgo(g1);
        new GuiFrame(algo);
    }
}