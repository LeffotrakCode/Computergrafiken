package cgg;

import static tools.Functions.EPSILON;

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
            return Scene.shade(hit,ray);
        }else{
            return Color.white;
        }
    }
    //Perform ray tracing to find the closest intersection with a sphere
    

    

}