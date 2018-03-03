package com.mugenico.languageGenerator.test.generators.worlds;

import com.mugenico.languageGenerator.generators.worlds.Planet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing our formulas and generation
 *
 * Created by Gemin on 03.03.2018.
 */
class PlanetTest {
    @Test
    void calculateGravity() {
        Planet earth = new Planet();

        earth.setLength_day(88642);
        earth.setLength_year(5.9355E7);
        earth.setRadius(3.3895E6);
        earth.setDensity(3933.5);
        earth.setMass(6.4171E23);

        System.out.println("We have calculated: " + earth.calculateGravity(0));
    }

}