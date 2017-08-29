package com.mugenico.languageGenerator.util;

import java.util.Random;

/**
 * Utility implementing a random generator with bounds
 *
 * Created by Gemin on 27.08.2017.
 */
public class RNG extends Random{

    Random rng;

    public RNG() {
        rng = new Random();
    }

    public RNG(long seed) {
        rng = new Random(seed);
    }

    /**
     *  Return the next int within a range.
     *
     */
    public int nextBoundInt(int min, int max) {
        if(min == max) {
            return max;
        }

        if(min > max) {
            int diff = min-max;
            return max+rng.nextInt(diff+1);
        }

        int diff = max-min;
        return min+rng.nextInt(diff+1);
    }


    /**
     * Advanced Gaussian generator with standard deviation and the avg around which the number should be generated
     *
     * @return a number within the gaussian bell around avg with standard deviation sd
     */
    public double nextGauss(double avg, double sd) {
        return rng.nextGaussian()*sd+avg;
    }

    /**
     * Boolean generator, that halves the probability of being true
     */
    public boolean nextLikelyFalse() {
        return rng.nextBoolean() && rng.nextBoolean();
    }

    /**
     * Boolean generator that has .75 probability of true
     */
    public boolean nextLikelyTrue() {
        return rng.nextBoolean() || rng.nextBoolean();
    }

}
