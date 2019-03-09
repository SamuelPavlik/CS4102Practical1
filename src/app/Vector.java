package app;

public class Vector {
    double x;
    double y;
    double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Vector() {
        this.x = 0;
        this.y = 0;
        this.z = 0;

    }

    public Vector scMult(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;

        return this;
    }

    public Vector add(Vector other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;

        return this;
    }

    public Vector sub(Vector other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;

        return this;
    }

    public Vector copy() {
        Vector other = new Vector(this.x, this.y, this.z);

        return other;
    }

    public double size() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public Vector ofSize(double size) {
        double actualSize = size();
        Vector other = copy();
        other.x = (other.x / actualSize) * size;
        other.y = (other.y / actualSize) * size;
        other.z = (other.z / actualSize) * size;

        return other;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector)
            return ((Vector) obj).x == this.x && ((Vector) obj).y == this.y;
        return false;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
