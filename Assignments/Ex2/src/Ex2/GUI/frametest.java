package Ex2.GUI;

import Ex2.DWG;
import api.DirectedWeightedGraph;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.jar.JarFile;

public class frametest extends JFrame  {
    MyPanel panel;

    public frametest(DirectedWeightedGraph graph) throws IOException, ParseException {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        panel = new MyPanel(graph);
        this.add(panel);

        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());

        this.pack();

        this.setVisible(true);
    }



    public static void main(String[] args) throws IOException, ParseException {
//        frametest fr = new frametest();
    }
}
