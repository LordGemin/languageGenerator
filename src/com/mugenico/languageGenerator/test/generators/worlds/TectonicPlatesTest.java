package com.mugenico.languageGenerator.test.generators.worlds;

import com.mugenico.languageGenerator.generators.worlds.Planet;
import com.mugenico.languageGenerator.generators.worlds.TectonicPlates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * Created by Gemin on 11.03.2018.
 */
class TectonicPlatesTest {

    @Test
    public void generatePlates() {
        Planet whatever = new Planet();
        do {
            whatever.generatePlanet();
        }while(whatever.livableConditionAnimals()!=1);

        TectonicPlates tectonicPlates = new TectonicPlates(whatever);

        double totalArea = 0;
        for(int i=0; i<tectonicPlates.getNumberPlates(); i++) {
            TectonicPlates.Plate plate = tectonicPlates.getPlate(i);
            System.out.println("Plate "+i+" moves north with: "+plate.getNorthMovement()+"m/y");
            System.out.println("Plate "+i+" moves east with: "+plate.getEastMovement()+"m/y");
            System.out.println("Plate "+i+" expands with: "+plate.getExpansion()+"cm²/y");
            System.out.println("Plate "+i+" has a size of: "+plate.getSize()+"km²");
            totalArea += plate.getSize();
        }

        assertEquals(whatever.calculateSurfaceArea(),totalArea);
    }
}