package com.mugenico.languageGenerator;


import com.mugenico.languageGenerator.generators.Words;

/**
 * Starter class to experiment with procedurally generated constructed languages
 * Inspired by http://mewo2.com/notes/naming-language/ (Martin O'Leary)
 * & The Language Construction Kit (Mark Rosenfelder)
 *
 * Author: Gemin
 */
public class Main {

    /**
     * For now this will only start the subroutine in another class
     */
    public static void main(String[] args) {
        Words words = new Words('S');

        System.out.println("\n Cities: ");
        System.out.println(words.createCity());
        System.out.println(words.createCity());
        System.out.println(words.createCity());
        System.out.println(words.createCity());
        System.out.println(words.createCity());
        System.out.println(words.createCity());
        System.out.println(words.createCity());
        System.out.println(words.createCity());
        System.out.println(words.createCity());
        System.out.println(words.createCity());
        System.out.println(words.createCity());

        System.out.println("\n Words: ");
        System.out.println(words.createWord());
        System.out.println(words.createWord());
        System.out.println(words.createWord());
        System.out.println(words.createWord());
        System.out.println(words.createWord());
        System.out.println(words.createWord());


        System.out.println("\n Stats: ");
        System.out.println("Number of created morphemes: "+words.getMorphemes().size());
        System.out.println("Number of created city morphemes: "+words.getCityMorphemes().size());
        System.out.println("Number of created river morphemes: "+words.getRiverMorphemes().size());
        System.out.println("Number of created village morphemes: "+words.getVillageMorphemes().size());
        System.out.println("Number of created mountain morphemes: "+words.getMountainMorphemes().size());
        System.out.println("Number of created lake morphemes: "+words.getLakeMorphemes().size());
        System.out.println("Number of created name morphemes: "+words.getNameMorphemes().size());
        System.out.println("Number of created common morphemes: "+words.getCommonMorphemes().size());
    }
}
