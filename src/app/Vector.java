package app;

public class Vector {
    public double x;
    public double y;
    public double z;

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

    /**
     * @param scalar
     * @return this vector multiplied by the scalar
     */
    public Vector scMult(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;

        return this;
    }

    /**
     * @param other vector to add
     * @return this vector with the other vector added
     */
    public Vector add(Vector other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;

        return this;
    }

    /**
     * @param other vector to subtract
     * @return this vector with the other vector subtracted
     */
    public Vector sub(Vector other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;

        return this;
    }

    /**
     * @param other vector to use for dot product
     * @return dot product of this and the other vector
     */
    public double dot(Vector other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    /**
     * @return copy of this vector
     */
    public Vector copy() {
        Vector other = new Vector(this.x, this.y, this.z);

        return other;
    }

    /**
     * @return size of the vector
     */
    public double size() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    /**
     * @param size desired size
     * @return vector with the desired size but unchanged direction
     */
    public Vector ofSize(double size) {
        double actualSize = size();
        if (actualSize == 0)
            return new Vector();

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
