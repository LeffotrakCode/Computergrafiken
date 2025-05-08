package cgg;

import tools.Color;
import tools.Functions;
import tools.Vec2;
import tools.ISampler;

import java.util.Random;

public record SuperSampler(ISampler sampler, int samples, String mode) implements ISampler {
    private static final Random rand = new Random();

    @Override
    public Color getColor(Vec2 pos) {
        Color sum = new Color(0,0,0);

        int n = (int) Math.sqrt(samples);
        if (n * n != samples) {
            throw new IllegalArgumentException("Samples must be a perfect square (e.g. 16, 25, 36, 64, 100)");
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double u, v;

                switch (mode.toLowerCase()) {
                    case "grid":
                        u = (i + 0.5) / n;
                        v = (j + 0.5) / n;
                        break;
                    case "random":
                        u = rand.nextDouble();
                        v = rand.nextDouble();
                        break;
                    case "stratified":
                        u = (i + rand.nextDouble()) / n;
                        v = (j + rand.nextDouble()) / n;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown sampling mode: " + mode);
                }

                Vec2 offset = new Vec2(u - 0.5, v - 0.5); // zentriert
                sum = Functions.add(sum, sampler.getColor(Functions.add(pos,offset)));


            }
        }

        return Functions.multiply(sum, 1.0 / samples);


    }
}
