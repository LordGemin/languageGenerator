package com.mugenico.languageGenerator.generators.worlds;

/**
 * Class to create a base Planet that we can base our tectonics, continents and oceans on.
 * This class will generate the physical properties of our Planet.
 *
 * Created by Gemin on 03.03.2018.
 */
public class Planet {

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
    private double median_temperature;

    // Chemical Measurements
    private double oxygenLevel;
    private double carbonLevel;
    private double hydrogenLevel;
    private double nitrogenLevel;
    private double heliumLevel;

    // Stub constructor
    public Planet() {

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

    public double getMedian_temperature() {
        return median_temperature;
    }

    public void setMedian_temperature(double median_temperature) {
        this.median_temperature = median_temperature;
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

    public double getHeliumLevel() {
        return heliumLevel;
    }

    public void setHeliumLevel(double heliumLevel) {
        this.heliumLevel = heliumLevel;
    }

    private double calculateCircumference() {
        return radius*2*Math.PI;
    }

    private double calculateFlatteningCoefficient() {
        double omega = 2*Math.PI/length_day;

        return (5/4)*(omega*omega*radius*radius*radius)/(G*mass);
    }

    private double calculateEquatorialRadius() {
        return radius*(1+(calculateFlatteningCoefficient()/3));
    }

    private double calculatePolarRadius() {
        return radius*(1-(2*calculateFlatteningCoefficient()/3));
    }

    private double calculatePolarGravity() {
//        return Math.PI*4/3*G*density*calculatePolarRadius();
        double r = calculatePolarRadius();
        return G*mass/(r*r);
    }

    private double calculateEquatorialGravity() {
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
}
