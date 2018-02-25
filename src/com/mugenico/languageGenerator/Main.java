package com.mugenico.languageGenerator;


import com.mugenico.languageGenerator.generators.languages.LanguageSet;
import com.mugenico.languageGenerator.generators.languages.Sentences;
import com.mugenico.languageGenerator.generators.languages.Words;
import com.mugenico.languageGenerator.util.RNG;

import java.util.Arrays;
import java.util.Scanner;

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
        long seed = new RNG().nextLong();

        if(args.length != 0) {
            try {
                seed = Long.parseLong(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Please reference a number in your call.");
            }
        }

        RNG global_rng = new RNG(seed);

        LanguageSet ls = new LanguageSet(global_rng);
        Words words = new Words(ls);

        System.out.println("\nGrammar: ");
        System.out.println("Language Name: "+ ls.getNAME(words));
        System.out.println("Shorthand: "+ls.getCODE(words));
        System.out.println("Seed: " + seed);
        System.out.println("Construct: "+ls.getCONSTRUCT());
        System.out.println("Grammar: "+ls.getGRAMMAR());
        System.out.println("Vowels: "+ Arrays.toString(ls.getVOWELS()));
        System.out.println("Consonants: "+ Arrays.toString(ls.getCONSONANTS()));
        System.out.println("Finals: "+ Arrays.toString(ls.getFINALS()));
        System.out.println("Sibilants: "+ Arrays.toString(ls.getSIBILANTS()));
        System.out.println("Liquids: "+ Arrays.toString(ls.getLIQUIDS()));
/*
        System.out.println("\n Stats: ");
        System.out.println("Number of created morphemes: "+words.getMorphemes().size());
        System.out.println("Number of created city morphemes: "+words.getCityMorphemes().size());
        System.out.println("Number of created river morphemes: "+words.getRiverMorphemes().size());
        System.out.println("Number of created village morphemes: "+words.getVillageMorphemes().size());
        System.out.println("Number of created mountain morphemes: "+words.getMountainMorphemes().size());
        System.out.println("Number of created lake morphemes: "+words.getLakeMorphemes().size());
        System.out.println("Number of created name morphemes: "+words.getNameMorphemes().size());
        System.out.println("Number of created common morphemes: "+words.getCommonMorphemes().size());

        System.out.println("\n Cities: ");
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
    */
        Sentences sentences = new Sentences(ls);

        System.out.println("\n Sentences: ");

        for(int i =1; i<=10;i++) {
            System.out.println("("+sentences.getUsedLanguage()+") "+"Sentence "+i+": "+sentences.createSentence());
        }

        System.out.println("\n Paragraph: ");
        System.out.println("Ancient Paragraph written in "+ words.getLanguageName()+":\n"+sentences.createParagraph(6));
        System.out.println("\n");

        for(int i=1; i<=10;i++) {
            seed = new RNG().nextLong();

            ls = new LanguageSet(new RNG(seed));
            sentences = new Sentences(ls);

            System.out.println("Lorem Ipsum of: "+ls.getNAME(sentences.getWords())+" (Seed: "+seed+")");
            System.out.println(sentences.createLoremIpsum(2000)+"\n");

//            System.out.println("\n A new language: "+sentences.getWords().getLanguageName());
//            System.out.println("\n Average word length: "+ls.getAVG_WORD_LENGTH());
//            System.out.println("\n Average morpheme length: "+sentences.getWords().getAvgMorphLength());
//            System.out.println("Ancient Paragraph written:\n"+sentences.createParagraph());
        }


//
//
//        words = new Words('S');
//
//        System.out.println("\n Cities: ");
//        System.out.println(words.createCity());
//        System.out.println(words.createCity());
//        System.out.println(words.createCity());
//        System.out.println(words.createCity());
//        System.out.println(words.createCity());
//        System.out.println(words.createCity());
//        System.out.println(words.createCity());
//        System.out.println(words.createCity());
//        System.out.println(words.createCity());
//        System.out.println(words.createCity());
//        System.out.println(words.createCity());
//
//        System.out.println("\n Words: ");
//        System.out.println(words.createWord());
//        System.out.println(words.createWord());
//        System.out.println(words.createWord());
//        System.out.println(words.createWord());
//        System.out.println(words.createWord());
//        System.out.println(words.createWord());
//
//
//        System.out.println("\n Stats: ");
//        System.out.println("Number of created morphemes: "+words.getMorphemes().size());
//        System.out.println("Number of created city morphemes: "+words.getCityMorphemes().size());
//        System.out.println("Number of created river morphemes: "+words.getRiverMorphemes().size());
//        System.out.println("Number of created village morphemes: "+words.getVillageMorphemes().size());
//        System.out.println("Number of created mountain morphemes: "+words.getMountainMorphemes().size());
//        System.out.println("Number of created lake morphemes: "+words.getLakeMorphemes().size());
//        System.out.println("Number of created name morphemes: "+words.getNameMorphemes().size());
//        System.out.println("Number of created common morphemes: "+words.getCommonMorphemes().size());
//
//
//        for(int i =1; i<=10;i++) {
//            Sentences sentences = new Sentences();
//            System.out.println("("+sentences.getUsedLanguage()+") "+"Sentence "+i+": "+sentences.createSentence());
//        }
//        System.out.println("\n\n");
//
//        for(int i =1; i<=10;i++) {
//            Sentences sentences = new Sentences('A');
//            System.out.println("("+sentences.getUsedLanguage()+") "+"Sentence "+i+": "+sentences.createSentence());
//        }
//        System.out.println("\n\n");
//
//        for(int i =1; i<=100;i++) {
//            Sentences sentences = new Sentences('J');
//            System.out.println("("+sentences.getUsedLanguage()+") "+"Sentence "+i+": "+sentences.createSentence());
//        }
//        System.out.println("\n\n");
//
//        for(int i =1; i<=10;i++) {
//            Sentences sentences = new Sentences('S');
//            System.out.println("("+sentences.getUsedLanguage()+") "+"Sentence "+i+": "+sentences.createSentence());
//        }
    }
}
