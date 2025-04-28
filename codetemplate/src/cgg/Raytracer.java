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
            return scene.shade(hit,ray);
        }else{
            return Color.white;
        }
    }
}