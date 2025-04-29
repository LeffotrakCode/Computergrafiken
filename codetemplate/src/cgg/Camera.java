package cgg;

import tools.*;

public class Camera{
    double alpha;
    double width;
    double height;

    // x right, y up, looking in -z direction
    public Camera(double alpha,double width,double height){
        this.alpha = alpha;
        this.width = width;
        this.height = height;
    }
    
    public Ray generateRay(Vec2 pixel){
         // Conversion to camera coordinates
        double aspectRatio = width / height;
        double imagePlaneHeight = 2.0 * Math.tan(alpha / 2.0);
        double imagePlaneWidth = imagePlaneHeight * aspectRatio;

        // Normalize pixel coordinates to the image range of [-1, 1]
        double x = (pixel.u() / width) * imagePlaneWidth - imagePlaneWidth / 2.0;
        double y = (height - pixel.v()) / height * imagePlaneHeight - imagePlaneHeight / 2.0;


        Vec3 direction = new Vec3(x, y, 1.0);
        direction = Functions.normalize(direction);
        return new Ray(new Vec3(0.0, 0.0, 0.0), direction);
    }
}