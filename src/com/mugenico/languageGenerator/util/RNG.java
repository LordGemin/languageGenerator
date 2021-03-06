package com.mugenico.languageGenerator.util;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility implementing a random generator with bounds
 *
 * Created by Gemin on 27.08.2017.
 */
public class RNG extends Random{

    public RNG() {
        super();
    }

    public RNG(long seed) {
        super(seed);
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
            return max+nextInt(diff+1);
        }

        int diff = max-min;
        return min+nextInt(diff+1);
    }

    /**
     * Return the next double within a range
     *
     */
    public double nextBoundDouble(double min, double max) {
        return min+(nextDouble()*(max-min));
    }

    /**
     * Advanced Gaussian generator with standard deviation and the avg around which the number should be generated
     *
     * @return a number within the gaussian bell around avg with standard deviation sd
     */
    public double nextGauss(double avg, double sd) {
        return nextGaussian()*sd+avg;
    }

    /**
     * Boolean generator, that halves the probability of being true
     */
    public boolean nextLikelyFalse() {
        return nextBoolean() && nextBoolean();
    }

    /**
     * Boolean generator that has .75 probability of true
     */
    public boolean nextLikelyTrue() {
        return nextBoolean() || nextBoolean();
    }
}
