package Ex2;

import api.EdgeData;

public class Edge implements EdgeData {
    private final int src;
    private final int dest;
    private final double weight;
    private String info;
    private int tag;

    /** Constructor */
    public Edge(int Src, double Weight, int Dest){
        this.src = Src;
        this.weight = Weight;
        this. dest = Dest;


    }
    /** Copy Constructors */
    public Edge(Edge other){
        this.src = other.src;
        this.weight = other.weight;
        this.dest = other.dest;
        this.info = other.info;
        this.tag = other.tag;
    }

    public Edge(EdgeData other){
        this.src = other.getSrc();
        this.weight = other.getWeight();
        this.dest = other.getDest();
        this.info = other.getInfo();
        this.tag = other.getTag();
    }

    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    public String toString(){
        return "Src: "+this.src+".\nWeight: "+this.weight+".\nDest: "+this.dest+".";
    }
}