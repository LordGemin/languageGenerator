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
        Planet mars = new Planet();

        mars.setLength_day(88642);
        mars.setLength_year(5.9355E7);
        mars.setRadius(3.3895E6);
        mars.setDensity(3933.5);
        mars.setMass(6.4171E23);

        System.out.println("For mars we have calculated gravity of: " + mars.calculateGravity(0));
    }

    @Test
    void generatePlanet() {
        Planet whatever = new Planet();
        do {
            whatever.generatePlanet();
        }while(whatever.livableConditionAnimals()!=1);

        System.out.println("We have calculated gravity: " + whatever.calculateGravity(45));

        System.out.println("Rotational speed: "+whatever.getLength_day());
        System.out.println("Mass: "+whatever.getMass());
        System.out.println("Radius: "+whatever.getRadius());
        System.out.println("Flattening: "+whatever.calculateFlatteningCoefficient());

        System.out.println("Plants can live: "+whatever.livableConditionPlants());
        System.out.println("Animals can live: "+whatever.livableConditionAnimals());
    }
}