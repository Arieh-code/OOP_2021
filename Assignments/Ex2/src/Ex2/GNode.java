package Ex2;

import api.GeoLocation;
import api.NodeData;

public class GNode implements NodeData {
    private final int key;
    private GeoLocation location;
    private double weight;
    private String info;
    private int tag;


    /** Constructor */
    public GNode(int key, GeoLocation g){
        this.key = key;
        this.location = g;
    }

    /** Copy Constructor */
    public GNode(GNode other){
        this.key = other.getKey();
        this.location = other.getLocation();
        this.weight = other.getWeight();
        this.info = other.getInfo();
        this.tag = other.getTag();
    }

    /** Copy NodeData Constructor*/
    public GNode(NodeData other){
        this.key = other.getKey();
        this.location = other.getLocation();
    }


    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.location = p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
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
        return "Key: "+this.key +".\nLocation: "+this.location.toString()+".";
    }
}
