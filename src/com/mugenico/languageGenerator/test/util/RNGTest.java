package com.mugenico.languageGenerator.test.util;

import com.mugenico.languageGenerator.util.RNG;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * Created by Gemin on 23.02.2018.
 */
class RNGTest {
    RNG rng = new RNG();

    @Test
    void nextBoundInt() {
        int numbers[] = new int[1000];
        for (int i = 0; i<1000;i++) {
            numbers[i]=rng.nextBoundInt(0,24);
        }

        int occurences[] = new int[25];
        for(int i:numbers) {
            occurences[i]++;
        }

        int ctr = 0;
        for(int o:occurences) {
            System.out.println("Occurences of "+ctr+": "+o);
            ctr++;
        }
        assertEquals(1,1);
    }

    @Test
    void nextGauss() {
    }

    @Test
    void nextLikelyFalse() {
    }

    @Test
    void nextLikelyTrue() {
    }

}