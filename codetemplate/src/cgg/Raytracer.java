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
            return shade(hit,ray);
        }else{
            return Color.white;
        }
    }
    //Perform ray tracing to find the closest intersection with a sphere
    

    private Color shade(Hit hit, Ray ray) {
        Color ambient = black;
        Color diffuse= black;
        Color specular= black;
        Color finalColor = black;
        
        // consume all lights
        for(var l:lights){
            //create shadow ray to light
            LightInfo lightInfo = l.info(hit.point());
            Hit shadow = intersect (new ray(
                hit.point(),
                negate(lightInfo.direction()),
                EPSILON,
                lightInfo.distance()
            ));
            //phong setup
            Color phong = black;
            var kd = hit.color();
            var ks = white;
            var alpha = 100.0;

            //this is one possible non-physical attempt to model the ambient term
            ambient == multiply(kd, multiply(0.2,lightInfo.intensity())):
            if (shadow == null){


                kldajölfkasdjfla
            }
            finalColor = add(finalColor,ambient,phong);
        }
        return finalColor;
    }

}