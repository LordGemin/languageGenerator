package com.mugenico.languageGenerator.generators.worlds;

import com.mugenico.languageGenerator.util.RNG;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to populate the planet with tectonic plates and calculate their movements over time
 *
 * Created by Gemin on 11.03.2018.
 */
public class TectonicPlates {

    private List<Plate> platesList = new ArrayList<>();
    private List<Double> plateSizes = new ArrayList<>();
    private List<FaultLines> faultLines = new ArrayList<>();
    private Planet planet;
    private RNG rng;
    private int numberPlates;

    public TectonicPlates(Planet planet) {
        this.planet = planet;
        this.rng = planet.rng;

        generateNumberPlates();
        generatePlateSizes();
        generatePlates();
        placePlates();
    }

    public TectonicPlates(Planet planet, int numberPlates) {
        this.planet = planet;
        this.rng = planet.rng;
        this.numberPlates = numberPlates;

        generatePlateSizes();
        generatePlates();
        placePlates();
    }

    public TectonicPlates(RNG rng) {
        this.rng = rng;
    }

    /**
     * Randomly decide the number of plates from between 5 and 25
     */
    private void generateNumberPlates() {
        numberPlates = rng.nextBoundInt(5,25);
    }

    /**
     * Randomly assign platesizes. Last plate will most likely be the largest
     */
    private void generatePlateSizes() {
        double surfaceArea = planet.calculateSurfaceArea();
        int reasonableDivider = numberPlates-2;
        for(int i =0;i<numberPlates;i++) {
            double size = surfaceArea/rng.nextBoundInt(numberPlates/reasonableDivider,numberPlates);
            if(i==numberPlates-1) {
                size = surfaceArea;
            }
            plateSizes.add(size);
            surfaceArea -= size;
            if(surfaceArea == 0) {
                numberPlates = i+1;
                return;
            }
        }
    }

    /**
     * Actually generate the plates. All in one go.
     */
    private void generatePlates() {
        for(int i =0;i<numberPlates;i++) {
            double size = plateSizes.get(i);
            // Sometimes, we will have no surface area left after a number of plates, then we can just stop generating plates
            if(size == 0) {
                numberPlates = i+1;
                return;
            }
            platesList.add(new Plate(rng, size));
        }
    }

    /**
     * Place the plates on the surface of our planet, so that they don't overlap
     * TODO: Find a way to implement this
     */
    private void placePlates() {
        for(Plate p:platesList) {
            p.setX(0);
            p.setY(0);
        }
    }

    /**
     * Calculates the positions and strengths of fault lines along the plate borders
     * Stores them in some format that can be used to then model the surface of the planet
     */
    private void faultLines() {
        // Compare every plate with every other plate
        for(Plate p:platesList) {
            for(Plate y:platesList) {
                if(doesTouch(p,y)) {
                    //TODO: Implement
                    return;
                }
            }
        }
    }

    /**
     * Checks if two plates touch each other
     * @param p
     * @param y
     */
    private boolean doesTouch(Plate p, Plate y) {
        //TODO: Implement
        return false;
    }

    public Plate getPlate(int i) {
        return platesList.get(i);
    }

    public int getNumberPlates() {
        return numberPlates;
    }

    public FaultLines getFaultLine(int i) {
        return faultLines.get(i);
    }

    /**
     * Class to describe the properties of tectonic plates
     */
    public class Plate {
        // Size in km²
        private double size = 0;
        // Movement in m/y
        // negative value, moving south
        private double northMovement;
        // negative value, moving west
        private double eastMovement;

        // Position on planet
        private double x;
        private double y;

        // Expansion in cm²/y
        // negative value, shrinking
        private double expansion;

        private RNG rng;

        Plate(RNG rng, double desiredSize) {
            this.rng = rng;
            this.size = desiredSize;
            generatePlate(size);
        }

        public double getSize() {
            return size;
        }

        public void setSize(double size) {
            this.size = size;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getNorthMovement() {
            return northMovement;
        }

        public void setNorthMovement(double northMovement) {
            this.northMovement = northMovement;
        }

        public double getEastMovement() {
            return eastMovement;
        }

        public void setEastMovement(double eastMovement) {
            this.eastMovement = eastMovement;
        }

        public double getExpansion() {
            return expansion;
        }

        public void setExpansion(double expansion) {
            this.expansion = expansion;
        }

        public RNG getRng() {
            return rng;
        }

        public void setRng(RNG rng) {
            this.rng = rng;
        }

        private void generatePlate() {
            if(size!=0) {
                generatePlate(size);
            }
            generatePlate(rng.nextBoundDouble(1E5,1E9));
        }

        private void generatePlate(double desiredSize) {
            northMovement = rng.nextBoundDouble(-0.005,0.005);
            eastMovement = rng.nextBoundDouble(-0.005,0.005);
            expansion = rng.nextBoundDouble(-0.00001,0.00001);
        }
    }

    /**
     * Defining a simple fault line. We want their position, length, width and whether it shuffles terrain up or down
     */
    private class FaultLines {
        private double x;
        private double y;
        private double length;
        private double width;
        private boolean up;

        FaultLines() {

        }

        // Getter and Setter below this

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getLength() {
            return length;
        }

        public void setLength(double length) {
            this.length = length;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public boolean isUp() {
            return up;
        }

        public void setUp(boolean up) {
            this.up = up;
        }
    }
}
