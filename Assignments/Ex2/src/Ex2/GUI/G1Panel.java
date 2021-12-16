package Ex2.GUI;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class G1Panel extends JPanel {
    DirectedWeightedGraph graph;
    double scaleX;
    double scaleY;
    final int Width = Toolkit.getDefaultToolkit().getScreenSize().width;
    final int Height = Toolkit.getDefaultToolkit().getScreenSize().height;
    double minX;
    double minY;

    public G1Panel(DirectedWeightedGraph g){
        this.graph = g;
        this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        this.setOpaque(false);
    }
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        scale();
        Iterator<NodeData> itr = graph.nodeIter();
        while (itr.hasNext()) {
            NodeData node = itr.next();
            g2d.setStroke(new BasicStroke(5));
            g2d.setPaint(Color.PINK);

            int x = (int) ((node.getLocation().x() - minX) * scaleX);
            int y = (int) ((node.getLocation().y() - minY) * scaleY);

            g2d.fillOval(x, y, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("MV Boli", Font.BOLD, 13));
            g2d.drawString("" + node.getKey(), x, y + 35);
            repaint();

            Iterator<EdgeData> itr_edge = graph.edgeIter(node.getKey());
            while (itr_edge.hasNext()) {
                EdgeData edge = itr_edge.next();
                int srcX = (int) ((graph.getNode(edge.getSrc()).getLocation().x() - minX) * scaleX);
                int srcY = (int) ((graph.getNode(edge.getSrc()).getLocation().y() - minY) * scaleY);

                int destX = (int) ((graph.getNode(edge.getDest()).getLocation().x() - minX) * scaleX);
                int destY = (int) ((graph.getNode(edge.getDest()).getLocation().y() - minY) * scaleY);

                g2d.setStroke(new BasicStroke(2));
                g2d.setPaint(Color.BLUE);
                drawArrowLine(g2d, srcX, srcY, destX, destY, 5, 5);

                double edgeW = edge.getWeight();
                String E = String.format("%.3f", edgeW);
                g2d.setFont(new Font("MV Boli", Font.BOLD, 10));
                g2d.drawString(E, (int) (srcX * 0.25 + destX * 0.75), (int) (srcY * .25 + destY * .75));
                repaint();
            }
        }
    }

    /**
     * I found this function in an article https://newbedev.com/how-to-draw-a-directed-arrow-line-in-java
     * Draw an arrow line between two points.
     * @param g the graphics component.
     * @param x1 x-position of first point.
     * @param y1 y-position of first point.
     * @param x2 x-position of second point.
     * @param y2 y-position of second point.
     * @param d  the width of the arrow.
     * @param h  the height of the arrow.
     */
    private void drawArrowLine(Graphics2D g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.drawPolygon(xpoints, ypoints, 3);
    }



    public void scale(){
        ArrayList<Double> pointX = new ArrayList<>();
        ArrayList<Double> pointY = new ArrayList<>();

        Iterator<NodeData> itr = graph.nodeIter();

        while(itr.hasNext()){
            NodeData temp = itr.next();
            pointX.add(temp.getLocation().x());
            pointY.add(temp.getLocation().y());
        }
        minX = Collections.min(pointX);
        minY = Collections.min(pointY);
        scaleX = Width / Math.abs(Collections.max(pointX) - Collections.min(pointX)) * 0.97;
        scaleY = Height / Math.abs(Collections.max(pointY) - Collections.min(pointY)) * 0.9;

    }

}
