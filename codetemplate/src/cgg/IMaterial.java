package cgg;

import tools.Color;

public interface IMaterial {
    Color getDiffuse(Hit hit);
    Color getSpecular(Hit hit);
    double getShininess(Hit hit);
    Color getEmission(Hit hit);
    Ray getSecondaryRay(Hit hit);
}
