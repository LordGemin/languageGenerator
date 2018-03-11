package com.mugenico.languageGenerator.generators.worlds;

import com.mugenico.languageGenerator.util.RNG;

/**
 * Class to create a base Planet that we can base our tectonics, continents and oceans on.
 * This class will generate the physical properties of our Planet.
 *
 * Created by Gemin on 03.03.2018.
 */
public class Planet {

    RNG rng = new RNG();

    private static final double G = 6.67384E-11;

    // What defines a Planet?
    /**
     * We declare these variables to measure the Planet out physically and chemically
     * From those two measurements we can determine the kind of life possible on this Planet
     *
     * I.e. If there is no nitrogen on the Planet, plants will have it much harder to life as they need nitrogen in the ground for metabolism
     *
     */
    // Physical measurements
    private double radius;
    private double density;
    private double mass;
    private double length_day;
    private double length_year;
    private double mean_temperature;
    private double surface;

    // Measurements in relation to the star
    private double stellarMass = 1.98855E30;
    private double semiMajorAxis;
    private double semiMinorAxis;

    // Chemical Measurements
    private double oxygenLevel;
    private double carbonLevel;
    private double hydrogenLevel;
    private double nitrogenLevel;
    private double heliumLevel;
    private double phosphorusLevel;

    // Molecules in the atmosphere
    private double o2;
    private double ch4;
    private double c02;
    private double h20;

    // Climate-related measurements
    private double temperature_gradient;

    // Constructor gives a randomly generated planet.
    public Planet() {
        generatePlanet();
    }

    public Planet(RNG rng) {
        this.rng = rng;
    }

    // Getters and Setters, folded for beauty

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getLength_day() {
        return length_day;
    }

    public void setLength_day(double length_day) {
        this.length_day = length_day;
    }

    public double getLength_year() {
        return length_year;
    }

    public void setLength_year(double length_year) {
        this.length_year = length_year;
    }

    public double getMean_temperature() {
        return mean_temperature;
    }

    public void setMean_temperature(double mean_temperature) {
        this.mean_temperature = mean_temperature;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public double getOxygenLevel() {
        return oxygenLevel;
    }

    public void setOxygenLevel(double oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    public double getCarbonLevel() {
        return carbonLevel;
    }

    public void setCarbonLevel(double carbonLevel) {
        this.carbonLevel = carbonLevel;
    }

    public double getHydrogenLevel() {
        return hydrogenLevel;
    }

    public void setHydrogenLevel(double hydrogenLevel) {
        this.hydrogenLevel = hydrogenLevel;
    }

    public double getNitrogenLevel() {
        return nitrogenLevel;
    }

    public void setNitrogenLevel(double nitrogenLevel) {
        this.nitrogenLevel = nitrogenLevel;
    }

    public double getPhosphorusLevel() {
        return phosphorusLevel;
    }

    public void setPhosphorusLevel(double phosphorusLevel) {
        this.phosphorusLevel = phosphorusLevel;
    }

    public double getHeliumLevel() {
        return heliumLevel;
    }

    public void setHeliumLevel(double heliumLevel) {
        this.heliumLevel = heliumLevel;
    }


    /**
     * Using some Wikipedia magic, we calculate the flattening coefficient of our planet
     * Formula taken from https://en.wikipedia.org/wiki/Equatorial_bulge#Mathematical_expression
     * @return f
     */
    public double calculateFlatteningCoefficient() {
        double omega = 2*Math.PI/length_day;

        return (5/4)*(omega*omega*radius*radius*radius)/(G*mass);
    }

    /**
     * Using the flattening, we calculate the equatorial radius from the mean
     * @return equatorial radius
     */
    public double calculateEquatorialRadius() {
        return radius*(1+(calculateFlatteningCoefficient()/3));
    }

    /**
     * Using the flattening, we calculate the polar radius from the mean
     * @return polar radius
     */
    public double calculatePolarRadius() {
        return radius*(1-(2*calculateFlatteningCoefficient()/3));
    }

    /**
     * Calculating the gravity we expect at the poles
     * @return g_p
     */
    public double calculatePolarGravity() {
//        return Math.PI*4/3*G*density*calculatePolarRadius();
        double r = calculatePolarRadius();
        return G*mass/(r*r);
    }

    /**
     * Calculating the gravity we expect at the equator
     * @return g_e
     */
    public double calculateEquatorialGravity() {
//        return Math.PI*4/3*G*density*calculateEquatorialRadius();
        double r = calculateEquatorialRadius();
        return G*mass/(r*r);
    }

    /**
     * Calculating the gravity for any point on the surface of our Planet using the Somigliana equation
     *
     * This is somewhat complicated so please refer https://en.wikipedia.org/wiki/Clairaut's_theorem#Somigliana_equation
     *
     * @param phi The latitude at which we want our gravity calculated
     * @return Calculated gravity for given latitude phi
     */
    public double calculateGravity(double phi) {

        // Getting formula constants out of the way
        double b = calculatePolarRadius();
        double G_p = calculatePolarGravity();

        double a = calculateEquatorialRadius();
        double G_e = calculateEquatorialGravity();

        double k = (b*G_p-a*G_e)/(a*G_e);
        // For some reason this only gives out sensible esults if we divide by  3...
        // TODO: Find the cause, defeat it.
        k = k/3;

        // Eccentricity of our Planet
        double e_squared = (a*a-b*b)/(a*a);

        double phi_rad = phi*Math.PI/180;

        double sin_2_phi = Math.sin(phi_rad)*Math.sin(phi_rad);

        // This is finally the actual formula
        double g = G_e*((1+k*sin_2_phi)/Math.sqrt(1-e_squared*sin_2_phi));

//        if(true) {
//            System.out.println("b: "+b);
//            System.out.println("G_p: "+G_p);
//            System.out.println("a: "+a);
//            System.out.println("G_e: "+G_e);
//            System.out.println("e_squared: "+e_squared);
//            System.out.println("phi_rad: "+phi_rad);
//            System.out.println("k: "+k);
//            System.out.println("f: " +(calculateFlatteningCoefficient()));
//        }

        return g;
    }

    /**
     * Checks various physical and chemical parameters to see if the planet is habitable for animals
     *
     * @return 1 if livable, -1 if only special animals could live, 0 if no life
     */
    public int livableConditionAnimals() {
        int livable = 1;

        // To high of gravity would make big animals an impossibility (probably? TODO: Find research)
        if(calculatePolarGravity()>25 || calculateEquatorialGravity()>25) {
            livable = -1;
        }

        // If there is no plants, there have been no cells to evolve into complex animals
        if(!livableConditionPlants()) {
            livable = 0;
        }

        // This would create a drastic difference in gravity depending on the region
        if(calculateFlatteningCoefficient()>0.15) {
            livable = 0;
        }

        // We need organic compounds for DNA and stuff
        if(carbonLevel<0.1) {
            livable = 0;
        }

        // Important part of ATP
        if(nitrogenLevel<0.3) {
            livable = 0;
        }

        // Usually associated with breathing
        if(oxygenLevel<0.35) {
            livable = 0;
        }

        // Most of everything is water
        if(hydrogenLevel<0.1) {
            livable = 0;
        }

        return livable;
    }

    /**
     * Checks chemical parameters to see if planet is habitable for plants
     *
     * @return true if plants could survive
     */
    public boolean livableConditionPlants() {
        boolean livable = true;


        // If one year is this long, the planet is most likely to far from its star to be warm enough for life
        if(length_year>3.5E8 || length_year < 5E5) {
            livable = false;
        }

        // Leaning far out here, but plants can probably not set root on a jupiter-like planet
        if(density<1500) {
            livable = false;
        }

        // ATP
        if(nitrogenLevel<0.1) {
            livable = false;
        }

        // Plants need this, it's weird but helps
        if(phosphorusLevel<0.01) {
            livable = false;
        }

        // We want water to be theoretically possible
        if(hydrogenLevel<0.05 || oxygenLevel<0.025) {
            livable = false;
        }

        if(carbonLevel<0.05) {
            livable = false;
        }

        return livable;
    }

    /**
     * Generating a planet. Randomly gives values to all the different parameters.
     * Usually with some boundary condition, in order to minimise ridiculousness.
     */
    public void generatePlanet() {

        double atmosphere;
        // THIS IS HELLA INEFFICIENT (mostly around 100 steps... maybe fine?). BUT WHAT ELSE TO DO IN RANDOM
        // TODO: FIND OUT
        while(true){
            nitrogenLevel = rng.nextBoundDouble(0.05,0.6);
            oxygenLevel = rng.nextBoundDouble(0.01,0.8);
            carbonLevel = rng.nextBoundDouble(0.01,0.4);
            phosphorusLevel = rng.nextBoundDouble(0.001,0.3);
            hydrogenLevel = rng.nextBoundDouble(0.02,0.7);
            atmosphere = (nitrogenLevel+oxygenLevel+carbonLevel+phosphorusLevel+hydrogenLevel);
            if(atmosphere>1.00) {
                continue;
            }
            if(atmosphere>0.99) {
                break;
            }
        }

        double schwarzschildradius;
        // Find a mass to radius ratio that will not resolve in an instant black hole to swallow the universe
        do {
            mass = rng.nextBoundDouble(1E23, 1E26);
            radius = rng.nextBoundDouble(1.00E6, 1.5E7);

            schwarzschildradius= (2 * G * mass) / 8.9875517873681764E16;
        }while(radius<schwarzschildradius);

        density = rng.nextBoundDouble(500, 10000);
        length_day = rng.nextBoundDouble(500,100000);
        length_year = rng.nextBoundDouble(500000, 9E8);

        double inRoot = (length_day*length_year*G*stellarMass)/(4*Math.PI*Math.PI);
        semiMajorAxis = Math.pow(inRoot,1/3);

        // It's more unlikely to have an extremely elliptical orbit, so we make a negativity check
        // If it returns true, we calculate even probability of extremely elliptical and circle
        // Else, we stew towards very circular orbits.
        if(rng.nextLikelyFalse()) {
            semiMinorAxis = semiMajorAxis * rng.nextBoundDouble(0.1, 1);
        } else {
            semiMinorAxis = semiMinorAxis * rng.nextBoundDouble(0.8, 1);
        }
    }

    /**
     * Function to calculate the temperature gradient
     */
    public double calculateTemperatureGradient() {
        mean_temperature = calculateStefanBoltzmanLaw((semiMajorAxis+semiMinorAxis)/2);

        // The more extreme the flattening, the more extreme the falloff in temperature when we get near the polar
        temperature_gradient = Math.pow(calculateFlatteningCoefficient()+1, 5);

        return temperature_gradient;
    }

    /**
     * We calculate the eccentricity using center-to-focus = a*e
     *
     * @return The distance from center of orbital ellipse to focal point (star)
     */
    private double linearEccentricity() {
        return semiMajorAxis*orbitalEccentricity();
    }

    /**
     * We calculate the eccentricity using 1-b²/a² = e²
     *
     * @return The eccentricity 0 <= e <= 1
     */
    private double orbitalEccentricity() {
        return Math.sqrt(1-(semiMinorAxis*semiMinorAxis)/(semiMajorAxis*semiMajorAxis));
    }

    /**
     * Using the eccentricity of the orbit, we calculate the minimal distance from the star
     *
     * @return Maximal Temperature we expect on the planet just from stellar radiation
     */
    private double calculateMaxTemperature() {
        double minimalDistance = semiMajorAxis - linearEccentricity();
        return calculateStefanBoltzmanLaw(minimalDistance);
    }

    /**
     * Using the eccentricity of the orbit, we calculate the maximal distance from the star
     *
     * @return Minimal temperature we expect on the planet just from stellar radiation
     */
    private double calculateMinTemperature() {
        double maximalDistance = semiMajorAxis + linearEccentricity();
        return calculateStefanBoltzmanLaw(maximalDistance);
    }

    /**
     * There is a formular for approximating the mean temperature of a planet by using the distance from the sun
     * As we are creating planets, we will assume our star is the sun.
     * In this formula we also ignore the effects that atmosphere and tilt would have.
     *
     * @param distance The distance we want to use for our calculation
     * @return The calculated, simplified mean temperature of a planet
     */
    private double calculateStefanBoltzmanLaw(double distance) {
        return 5780*Math.pow((695000000/(distance*2)),0.5);
    }

    /**
     *
     * @return Surface area of the planet
     */
    public double calculateSurfaceArea() {
        double c_square = calculatePolarRadius()*calculatePolarRadius();
        double a_square = calculateEquatorialRadius()*calculateEquatorialRadius();
        double e = Math.sqrt(1-(c_square/a_square));
        return (2*Math.PI*a_square)+(Math.PI*(c_square/e)*Math.log((1+e)/(1-e)));
    }

}
