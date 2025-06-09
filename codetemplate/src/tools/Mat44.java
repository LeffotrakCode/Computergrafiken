package tools;

/**
 * Represents a 4x4 matrix for 3D transformations.
 */
public final class Mat44 {

    /** The identity matrix. */
    public static final Mat44 identity;

    static {
        identity = new Mat44();
    }

    /**
     * Returns a string representation of the matrix.
     *
     * @return A string representation of the matrix, with each row on a new line.
     */
    @Override
    public String toString() {
        String s = "";
        for (int r = 0; r < 4; r++) {
            s += String.format("(% .2f % .2f % .2f % .2f)\n", get(0, r), get(1, r), get(2, r), get(3, r));
        }
        return s;
    }

    /**
     * Checks if this matrix is equal to another object.
     *
     * @param o The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Mat44))
            return false;
        if (o == this)
            return true;
        Mat44 m = (Mat44) o;
        for (int i = 0; i != 16; i++)
            if (values[i] != m.values[i])
                return false;
        return true;
    }

    /**
     * Constructs a new Mat44 instance and initializes it as an identity matrix.
     */
    Mat44() {
        makeIdentity();
    }

    /**
     * Returns the internal array of matrix values.
     *
     * @return The array of matrix values.
     */
    double[] values() {
        return values;
    }

    /**
     * Gets the value at the specified column and row of the matrix.
     *
     * @param c The column index (0-3).
     * @param r The row index (0-3).
     * @return The value at the specified position.
     */
    public double get(int c, int r) {
        return values[4 * c + r];
    }

    /**
     * Sets the value at the specified column and row of the matrix.
     *
     * @param c The column index (0-3).
     * @param r The row index (0-3).
     * @param v The value to set.
     */
    void set(int c, int r, double v) {
        values[4 * c + r] = v;
    }

    /**
     * Resets this matrix to the identity matrix.
     *
     * @return This matrix, reset to the identity matrix.
     */
    Mat44 makeIdentity() {
        values = new double[16];
        set(0, 0, 1.0f);
        set(1, 1, 1.0f);
        set(2, 2, 1.0f);
        set(3, 3, 1.0f);
        return this;
    }

    /**
     * Multiplies this matrix with another matrix.
     *
     * @param m The matrix to multiply with.
     * @return 
     * @return A new Mat44 instance representing the result of the multiplication.
     */
    public Mat44 multiply(Mat44 m) {
    Mat44 n = new Mat44();
    for (int c = 0; c < 4; c++) {
        for (int r = 0; r < 4; r++) {
            double sum = 0;
            for (int k = 0; k < 4; k++) {
                sum += this.get(k, r) * m.get(c, k);
            }
            n.set(c, r, sum);
        }
    }
    return n;
    }

    /**
     * Returns a new matrix that is the transpose of this matrix.
     *
     * @return The transposed matrix.
     */
    public Mat44 transposed() {
        Mat44 t = new Mat44();
        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 4; r++) {
                t.set(r, c, this.get(c, r));
            }
        }
        return t;
    }

    /**
     * Returns the inverse of this matrix.
     *
     * @return A new Mat44 instance that is the inverse of this matrix.
     * @throws IllegalStateException if the matrix is not invertible.
     */
    public Mat44 inverted() {
        double[] m = this.values;
        double[] inv = new double[16];

        inv[0] = m[5]  * m[10] * m[15] - 
                 m[5]  * m[11] * m[14] - 
                 m[9]  * m[6]  * m[15] + 
                 m[9]  * m[7]  * m[14] +
                 m[13] * m[6]  * m[11] - 
                 m[13] * m[7]  * m[10];

        inv[4] = -m[4]  * m[10] * m[15] + 
                  m[4]  * m[11] * m[14] + 
                  m[8]  * m[6]  * m[15] - 
                  m[8]  * m[7]  * m[14] - 
                  m[12] * m[6]  * m[11] + 
                  m[12] * m[7]  * m[10];

        inv[8] = m[4]  * m[9] * m[15] - 
                 m[4]  * m[11] * m[13] - 
                 m[8]  * m[5] * m[15] + 
                 m[8]  * m[7] * m[13] + 
                 m[12] * m[5] * m[11] - 
                 m[12] * m[7] * m[9];

        inv[12] = -m[4]  * m[9] * m[14] + 
                   m[4]  * m[10] * m[13] +
                   m[8]  * m[5] * m[14] - 
                   m[8]  * m[6] * m[13] - 
                   m[12] * m[5] * m[10] + 
                   m[12] * m[6] * m[9];

        inv[1] = -m[1]  * m[10] * m[15] + 
                  m[1]  * m[11] * m[14] + 
                  m[9]  * m[2] * m[15] - 
                  m[9]  * m[3] * m[14] - 
                  m[13] * m[2] * m[11] + 
                  m[13] * m[3] * m[10];

        inv[5] = m[0]  * m[10] * m[15] - 
                 m[0]  * m[11] * m[14] - 
                 m[8]  * m[2] * m[15] + 
                 m[8]  * m[3] * m[14] + 
                 m[12] * m[2] * m[11] - 
                 m[12] * m[3] * m[10];

        inv[9] = -m[0]  * m[9] * m[15] + 
                  m[0]  * m[11] * m[13] + 
                  m[8]  * m[1] * m[15] - 
                  m[8]  * m[3] * m[13] - 
                  m[12] * m[1] * m[11] + 
                  m[12] * m[3] * m[9];

        inv[13] = m[0]  * m[9] * m[14] - 
                  m[0]  * m[10] * m[13] - 
                  m[8]  * m[1] * m[14] + 
                  m[8]  * m[2] * m[13] + 
                  m[12] * m[1] * m[10] - 
                  m[12] * m[2] * m[9];

        inv[2] = m[1]  * m[6] * m[15] - 
                 m[1]  * m[7] * m[14] - 
                 m[5]  * m[2] * m[15] + 
                 m[5]  * m[3] * m[14] + 
                 m[13] * m[2] * m[7] - 
                 m[13] * m[3] * m[6];

        inv[6] = -m[0]  * m[6] * m[15] + 
                  m[0]  * m[7] * m[14] + 
                  m[4]  * m[2] * m[15] - 
                  m[4]  * m[3] * m[14] - 
                  m[12] * m[2] * m[7] + 
                  m[12] * m[3] * m[6];

        inv[10] = m[0]  * m[5] * m[15] - 
                  m[0]  * m[7] * m[13] - 
                  m[4]  * m[1] * m[15] + 
                  m[4]  * m[3] * m[13] + 
                  m[12] * m[1] * m[7] - 
                  m[12] * m[3] * m[5];

        inv[14] = -m[0]  * m[5] * m[14] + 
                   m[0]  * m[6] * m[13] + 
                   m[4]  * m[1] * m[14] - 
                   m[4]  * m[2] * m[13] - 
                   m[12] * m[1] * m[6] + 
                   m[12] * m[2] * m[5];

        inv[3] = -m[1] * m[6] * m[11] + 
                  m[1] * m[7] * m[10] + 
                  m[5] * m[2] * m[11] - 
                  m[5] * m[3] * m[10] - 
                  m[9] * m[2] * m[7] + 
                  m[9] * m[3] * m[6];

        inv[7] = m[0] * m[6] * m[11] - 
                 m[0] * m[7] * m[10] - 
                 m[4] * m[2] * m[11] + 
                 m[4] * m[3] * m[10] + 
                 m[8] * m[2] * m[7] - 
                 m[8] * m[3] * m[6];

        inv[11] = -m[0] * m[5] * m[11] + 
                   m[0] * m[7] * m[9] + 
                   m[4] * m[1] * m[11] - 
                   m[4] * m[3] * m[9] - 
                   m[8] * m[1] * m[7] + 
                   m[8] * m[3] * m[5];

        inv[15] = m[0] * m[5] * m[10] - 
                  m[0] * m[6] * m[9] - 
                  m[4] * m[1] * m[10] + 
                  m[4] * m[2] * m[9] + 
                  m[8] * m[1] * m[6] - 
                  m[8] * m[2] * m[5];

        double det = m[0] * inv[0] + m[1] * inv[4] + m[2] * inv[8] + m[3] * inv[12];

        if (det == 0)
            throw new IllegalStateException("Matrix not invertible");

        det = 1.0 / det;

        Mat44 inverse = new Mat44();
        for (int i = 0; i < 16; i++) {
            inverse.values[i] = inv[i] * det;
        }
        return inverse;
    }
    /**
     * Creates a translation matrix for translation by (x, y, z).
     */
    public static Mat44 translation(double x, double y, double z) {
        Mat44 m = new Mat44();
        m.makeIdentity();
        m.set(3, 0, x); // column 3, row 0
        m.set(3, 1, y); // column 3, row 1
        m.set(3, 2, z); // column 3, row 2
        return m;
    }
    /**
     * Creates a rotation matrix around the Y-axis with an angle in radians.
     */
    public static Mat44 rotationY(double angle) {
        Mat44 m = new Mat44();
        m.makeIdentity();
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        m.set(0, 0, cos);
        m.set(2, 0, -sin);
        m.set(0, 2, sin);
        m.set(2, 2, cos);
        return m;
    }


    private double[] values;
}