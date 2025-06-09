package cgg;

import tools.*;

public class Camera {
    private Mat44 transform;
    private double alpha;
    private double width;
    private double height;

    public Camera(double alpha, double width, double height, Mat44 transform) {
        this.alpha = alpha;
        this.width = width;
        this.height = height;
        this.transform = transform;
    }

    public void setTransform(Mat44 transform) {
        this.transform = transform;
    }

    public Mat44 getTransform() {
        return transform;
    }

    // Read translation directly from the matrix
    public Vec3 getTranslation() {
        return Functions.vec3(
            transform.get(3, 0),
            transform.get(3, 1),
            transform.get(3, 2)
        );
    }

    public Ray generateRay(Vec2 pixel) {
        double aspectRatio = width / height;
        double imagePlaneHeight = 2.0 * Math.tan(alpha / 2.0);
        double imagePlaneWidth = imagePlaneHeight * aspectRatio;

        // Convert the pixel to the image plane region
        double x = (pixel.u() / width) * imagePlaneWidth - imagePlaneWidth / 2.0;
        double y = (height - pixel.v()) / height * imagePlaneHeight - imagePlaneHeight / 2.0;

        Vec3 localDir = Functions.normalize(Functions.vec3(x, y, 1.0));
        Vec3 worldDir = Functions.normalize(Functions.multiplyDirection(transform, localDir));
        Vec3 worldOrigin = Functions.multiplyPoint(transform, Functions.vec3(0, 0, 0));

        return new Ray(worldOrigin, worldDir);
    }
}
