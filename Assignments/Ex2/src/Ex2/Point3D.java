package Ex2;

import api.GeoLocation;

public class Point3D implements GeoLocation {
    private final double _x;
    private final double _y;
    private final double _z;

    /** Constructors */
    public Point3D(double x, double y, double z){
        this._x = x;
        this._y = y;
        this._z = z;
    }

    /** Copy Constructors */
    public Point3D(Point3D other){
        this._x = other._x;
        this._y = other._y;
        this._z = other._z;
    }


    @Override
    // returning x value
    public double x() {
        return this._x;
    }

    @Override
    // returning y value
    public double y() {
        return this._y;
    }

    @Override
    // returning z value
    public double z() {
        return this._z;
    }

    @Override
    // returning distance between two points
    public double distance(GeoLocation g) {
        /* the equation of the distance between two 3d points is
        sqrt ((x2-x1)**+(y2-y1)**+(z2-z1)**)
         */
        double x_pow = Math.pow(g.x()-this.x(), 2);
        double y_pow = Math.pow(g.y()-this.y(), 2);
        double z_pow = Math.pow(g.z()-this.z(), 2);

        return Math.sqrt(x_pow+y_pow+z_pow);
    }

    public String toString(){
        return "("+this._x+", "+this._y+", "+this._z+")";
    }


}
