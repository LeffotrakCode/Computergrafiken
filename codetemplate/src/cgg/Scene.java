package cgg;



import java.util.ArrayList;

import cgg.ILight.LightInfo;
import tools.*;

public class Scene{
    private ArrayList<Sphere> spheres = new ArrayList<>();
    // private ArrayList<ILight> lights = new ArrayList<>();
    public Scene () {
        spheres= new ArrayList<>();
       // lights = new ArrayListy<>();
        
    }
    public void addSphere(Sphere sphere){
        spheres.add(sphere);
    }
    //public void addLight(Lights light){
    //    lights.add(light);
    //}
    public Hit intersect(Ray ray){
            Hit closest = null;
    
            for (Sphere s : spheres) {
                Hit h = s.intersect(ray);
                if (h != null && (closest == null || h.t() < closest.t())) {
                    closest = h;
                }
            }
    
            if (closest != null) {
                return closest;
            }
    
            //Backgound color
            return closest;
        }

    private Color shade(Hit hit, Ray ray) {
        Color ambient = Color.black;
        Color diffuse= Color.black;
        Color specular= Color.black;
        Color finalColor = Color.black;
        
        // consume all lights
        for(var l:lights){
            //create shadow ray to light
            LightInfo lightInfo = l.info(hit.x());
            Hit shadow = intersect (new ray(
                hit.x(),
                Functions.negate(lightInfo.direction()),
                Functions.EPSILON,
                lightInfo.distance()
            ));
            //phong setup
            Color phong = Color.black;
            var kd = hit.c();
            var ks = Color.white;
            var alpha = 100.0;

            //this is one possible non-physical attempt to model the ambient term
            ambient = Functions.multiply(kd, Functions.multiply(0.2,lightInfo.intensity()));
            if (shadow == null){


                kldajölfkasdjfla
            }
            finalColor = add(finalColor,ambient,phong);
        }
        return finalColor;
    }
    }
    

    
    

    
