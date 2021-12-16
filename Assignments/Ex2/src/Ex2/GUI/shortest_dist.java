package Ex2.GUI;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class shortest_dist extends JPanel {

    DirectedWeightedGraph graph;
    double scaleX;
    double scaleY;
    final int Width = Toolkit.getDefaultToolkit().getScreenSize().width;
    final int Height = Toolkit.getDefaultToolkit().getScreenSize().height;
    double minX;
    double minY;
    ArrayList<NodeData> nList;


    public shortest_dist(DirectedWeightedGraph algo, ArrayList<NodeData> list,   double dist){
        this.graph = algo;
        this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        this.setOpaque(false);
        this.nList = list;
    }



    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        scale();
        for (int i = 0; i < nList.size() - 1; i++) {
            g2d.setStroke(new BasicStroke(5));
            g2d.setPaint(Color.PINK);

            int x = (int) ((nList.get(i).getLocation().x() - minX) * scaleX);
            int y = (int) ((nList.get(i).getLocation().y() - minY) * scaleY);

            g2d.fillOval(x, y, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("MV Boli", Font.BOLD, 13));
            g2d.drawString("" + nList.get(i).getKey(), x, y + 35);
            repaint();

            int srcX = (int) ((nList.get(i).getLocation().x() - minX) * scaleX);
            int srcY = (int) ((nList.get(i).getLocation().y() - minY) * scaleY);

            int destX = (int) ((nList.get(i + 1).getLocation().x() - minX) * scaleX);
            int destY = (int) ((nList.get(i + 1).getLocation().y() - minY) * scaleY);

            g2d.setStroke(new BasicStroke(2));
            g2d.setPaint(Color.BLUE);
            drawArrowLine(g2d, srcX, srcY, destX, destY, 5, 5);

            double edgeW = graph.getEdge(nList.get(i).getKey(), nList.get(i + 1).getKey()).getWeight();
            String E = String.format("%.3f", edgeW);
            g2d.setFont(new Font("MV Boli", Font.BOLD, 10));
            g2d.drawString(E, (int) (srcX * 0.25 + destX * 0.75), (int) (srcY * .25 + destY * .75));
            repaint();


        }
        g2d.setStroke(new BasicStroke(5));
        g2d.setPaint(Color.PINK);

        int x = (int) ((nList.get(nList.size()-1).getLocation().x() - minX) * scaleX);
        int y = (int) ((nList.get(nList.size()-1).getLocation().y() - minY) * scaleY);

        g2d.fillOval(x, y, 20, 20);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("MV Boli", Font.BOLD, 13));
        g2d.drawString("" + nList.get(nList.size()-1).getKey(), x, y + 35);
        repaint();

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
//        g.fillPolygon(xpoints, ypoints, 3);
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
