package cgg;



import java.util.ArrayList;

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
        public Color shade(Hit hit){return null;}
    }
    

    
    

    
