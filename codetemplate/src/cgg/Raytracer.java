package cgg;

import tools.*;





public class Raytracer implements ISampler {
    private Scene scene;
    private final Camera cam;

    public Raytracer(Camera cam, Scene scene) {
        this.scene = scene;
        this.cam = cam;
    }

    
    public Color getColor(Vec2 position){
        Ray ray = cam.generateRay(position);
        Hit hit = scene.intersect(ray);
        if(hit != null){
            return hit.c();
        }else{
            return Color.cyan;
        }
    }
    //Perform ray tracing to find the closest intersection with a sphere
    

    private Color shade(Hit hit) {
        Vec3 lightDir = Functions.normalize(new Vec3(1, 1, 0.5));
        Color ambient = Functions.multiply(0.1, hit.c());
        Color diffuse = Functions.multiply(0.9 * Math.max(0, Functions.dot(lightDir, hit.n())), hit.c());
        return Functions.add(ambient, diffuse);
    }

}