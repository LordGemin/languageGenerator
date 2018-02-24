package com.mugenico.languageGenerator.test.generators.languages;

import com.mugenico.languageGenerator.generators.languages.LanguageSet;
import com.mugenico.languageGenerator.generators.languages.Words;
import com.mugenico.languageGenerator.util.RNG;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * Created by Gemin on 23.02.2018.
 */
class LanguageSetTest {

    @Test
    public void testConstructor() {
        // Run a hundred tests and find why if seeding the RNG works
        for(int i = 0;i<100;i++) {
            long seed = new RNG().nextLong();
            RNG rng = new RNG(seed);

            LanguageSet lsONE = new LanguageSet(rng);
            Words words1 = new Words(lsONE);

            rng = new RNG(seed);
            LanguageSet lsTWO = new LanguageSet(rng);
            Words words2 = new Words(lsTWO);

            assertEquals(lsONE.getCONSONANTS().length, lsTWO.getCONSONANTS().length);
            assertEquals(lsONE.getNAME(words1), lsTWO.getCODE(words2));
        }
    }

}