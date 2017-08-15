package com.mugenico.languageGenerator.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Defining class for the construction of our words
 *
 *
 * Created by Gemin on 14.08.2017.
 */
public class Words {

    // TODO: Find a way to use this
    //private static final float AVG_WORD_LENGTH = 2.4f;
    private int MAX_WORD_LENGTH;

    // defining a structure where all syllables begin with a consonant followed by a vowel.
    // afterwards, there can be another vowel, a final, both, or none.
    // X? --> X is optional
    private final static String A_CONSTRUCT = "CVV?F?";
    private static final int A_MAX_WORD_LENGTH = 2;

    private static final String[] A_CONSONANTS = {"j","sh","dj","ts","w","g","x","q", "r"};
    private static final String[] A_VOWELS = {"o", "ou", "u", "e", "ya"};
    private static final String[] A_FINALS = {"ng", "m", "n"};
    private static final String[] A_LIQUIDS = {};
    private static final String[] A_SIBILANTS = {};
    private static final String[] A_BANNED_COMBOS = {"ee", "ouu", "uou", "ouou", "ouo"};
    private static final boolean A_CAPITALIZATION = false;
    private static final boolean A_PLACE_NAME_START = false;
    private static final boolean A_PLACE_NAME_END = true;

    private final static String SK_CONSTRUCT = "V??CVF?";
    private static final int SK_MAX_WORD_LENGTH = 3;

    private static final String[] SK_CONSONANTS = {"sk", "s", "st", "h", "m", "t", "k", "ch"};
    private static final String[] SK_VOWELS = {"a", "å", "e", "o",  "i","u"};
    private static final String[] SK_FINALS = {"f", "ft"};
    private static final String[] SK_LIQUIDS = {};
    private static final String[] SK_SIBILANTS = {};
    private static final String[] SK_BANNED_COMBOS = {"åå"};
    private static final boolean SK_CAPITALIZATION = true;
    private static final boolean SK_PLACE_NAME_START = true;
    private static final boolean SK_PLACE_NAME_END = false;

    private final static String J_CONSTRUCT = "C/L?VF??C/L?+V";
    private static final int J_MAX_WORD_LENGTH = 2;

    private static final String[] J_CONSONANTS = {"k","t","s","sh","h","n","w", "ch", "f"};
    private static final String[] J_VOWELS = {"a","i","u","e","o", "ū", "ō"};
    private static final String[] J_FINALS = {"n"};
    private static final String[] J_LIQUIDS = {"g","d","b","p"};
    private static final String[] J_SIBILANTS = {};
    private static final String[] J_BANNED_COMBOS = {"che", "she", "nn", "ti", "si",
            "wō", "wū", "wu", "wi", "we", "fi", "fe", "fo", "fa", "uu", "du"};
    private static final boolean J_CAPITALIZATION = false;
    private static final boolean J_PLACE_NAME_START = false;
    private static final boolean J_PLACE_NAME_END = true;

    private String CONSTRUCT;
    private String[] CONSONANTS;
    private String[] VOWELS;
    private String[] LIQUIDS;
    private String[] SIBILANTS;
    private String[] FINALS;
    private String[] BANNED_COMBOS;
    private boolean CAPITALIZATION;
    private boolean PLACE_NAME_START;
    private boolean PLACE_NAME_END;


    // Catch all phoneme list.
    private List<String> morphemes = new ArrayList<>();


    // Some differentiation will occur for the phonemes
    private List<String> cityMorphemes = new ArrayList<>();
    private List<String> riverMorphemes = new ArrayList<>();
    private List<String> villageMorphemes = new ArrayList<>();
    private List<String> mountainMorphemes = new ArrayList<>();
    private List<String> lakeMorphemes = new ArrayList<>();

    // Morphemes mostly used in peoples names
    private List<String> nameMorphemes = new ArrayList<>();

    // Morphemes used everywhere
    private List<String> commonMorphemes = new ArrayList<>();

    public List<String> getMorphemes() {
        return morphemes;
    }

    public List<String> getCityMorphemes() {
        return cityMorphemes;
    }

    public List<String> getRiverMorphemes() {
        return riverMorphemes;
    }

    public List<String> getVillageMorphemes() {
        return villageMorphemes;
    }

    public List<String> getMountainMorphemes() {
        return mountainMorphemes;
    }

    public List<String> getLakeMorphemes() {
        return lakeMorphemes;
    }

    public List<String> getNameMorphemes() {
        return nameMorphemes;
    }

    public List<String> getCommonMorphemes() {
        return commonMorphemes;
    }

//
//    /**
//     * Upon construction, some first Morphemes are created.
//     */
//    public Words() {
//        CONSTRUCT = A_CONSTRUCT;
//        MAX_WORD_LENGTH = A_MAX_WORD_LENGTH;
//        CONSONANTS = A_CONSONANTS;
//        LIQUIDS = A_LIQUIDS;
//        SIBILANTS = A_SIBILANTS;
//        VOWELS = A_VOWELS;
//        FINALS = A_FINALS;
//        BANNED_COMBOS = A_BANNED_COMBOS;
//
//        for(int i = 0; i<1000; i++) {
//            createMorpheme();
//        }
//    }

    /**
     * Upon construction, some first Morphemes are created.
     */
    public Words(char code) {

        switch(code) {
            case 'A':
                CONSTRUCT = A_CONSTRUCT;
                MAX_WORD_LENGTH = A_MAX_WORD_LENGTH;
                CONSONANTS = A_CONSONANTS;
                LIQUIDS = A_LIQUIDS;
                SIBILANTS = A_SIBILANTS;
                VOWELS = A_VOWELS;
                FINALS = A_FINALS;
                BANNED_COMBOS = A_BANNED_COMBOS;
                CAPITALIZATION = A_CAPITALIZATION;
                PLACE_NAME_START = A_PLACE_NAME_START;
                PLACE_NAME_END = A_PLACE_NAME_END;
                break;
            case 'J':
                CONSTRUCT = J_CONSTRUCT;
                MAX_WORD_LENGTH = J_MAX_WORD_LENGTH;
                CONSONANTS = J_CONSONANTS;
                LIQUIDS = J_LIQUIDS;
                SIBILANTS = J_SIBILANTS;
                VOWELS = J_VOWELS;
                FINALS = J_FINALS;
                BANNED_COMBOS = J_BANNED_COMBOS;
                CAPITALIZATION = J_CAPITALIZATION;
                PLACE_NAME_START = J_PLACE_NAME_START;
                PLACE_NAME_END = J_PLACE_NAME_END;
                break;
            case 'S':
                CONSTRUCT = SK_CONSTRUCT;
                MAX_WORD_LENGTH = SK_MAX_WORD_LENGTH;
                CONSONANTS = SK_CONSONANTS;
                LIQUIDS = SK_LIQUIDS;
                SIBILANTS = SK_SIBILANTS;
                VOWELS = SK_VOWELS;
                FINALS = SK_FINALS;
                BANNED_COMBOS = SK_BANNED_COMBOS;
                CAPITALIZATION = SK_CAPITALIZATION;
                PLACE_NAME_START = SK_PLACE_NAME_START;
                PLACE_NAME_END = SK_PLACE_NAME_END;
                break;
            default:
                CONSTRUCT = A_CONSTRUCT;
                MAX_WORD_LENGTH = A_MAX_WORD_LENGTH;
                CONSONANTS = A_CONSONANTS;
                LIQUIDS = A_LIQUIDS;
                SIBILANTS = A_SIBILANTS;
                VOWELS = A_VOWELS;
                FINALS = A_FINALS;
                BANNED_COMBOS = A_BANNED_COMBOS;
                CAPITALIZATION = A_CAPITALIZATION;
                PLACE_NAME_START = A_PLACE_NAME_START;
                PLACE_NAME_END = A_PLACE_NAME_END;
        }

        for(int i = 0; i<200; i++) {
            createMorpheme();
        }
    }

    /**
     * Called to create new morphemes.
     * Distributes them fairly into the different categories, with "common" getting the most.
     */
    private void createMorpheme() {
        String morpheme = "";

        Random rng = new Random();

        Parser parser = new Parser(CONSTRUCT);

        while(parser.hasNext()) {
            // normally we don't skip. ~20% of cases shall be skipped
            char nextPhoneme = parser.getNext((rng.nextInt(10)<2));

            switch (nextPhoneme) {
                case 'C':
                    morpheme = morpheme.concat(CONSONANTS[rng.nextInt(CONSONANTS.length)]);
                    break;
                case 'L':
                    morpheme = morpheme.concat(LIQUIDS[rng.nextInt(LIQUIDS.length)]);
                    break;
                case 'S':
                    morpheme = morpheme.concat(SIBILANTS[rng.nextInt(SIBILANTS.length)]);
                    break;
                case 'V':
                    morpheme = morpheme.concat(VOWELS[rng.nextInt(VOWELS.length)]);
                    break;
                case 'F':
                    morpheme = morpheme.concat(FINALS[rng.nextInt(FINALS.length)]);
                    break;

                default: break;
            }
        }

        // Check if the created morpheme is on the language specific banned list
        boolean banned = false;

        for(String s: BANNED_COMBOS) {
            if(morpheme.contains(s)) {
                banned = true;
            }
        }

        if (banned) {
            return ;
        }

        // Check if the morpheme is empty for some reason, we don't want those
        if (morpheme.equals("")) {
            return ;
        }

        if(!morphemeExists(morpheme)) {
            morphemes.add(morpheme);
            int placement = rng.nextInt(100);

            if(placement < 70) {
                getCommonMorphemes().add(morpheme);
            } else if (placement < 72) {
                getCityMorphemes().add(morpheme);
            } else if (placement < 75) {
                getRiverMorphemes().add(morpheme);
            } else if (placement < 82) {
                getVillageMorphemes().add(morpheme);
            } else if (placement < 92) {
                getMountainMorphemes().add(morpheme);
            } else if (placement < 97) {
                getLakeMorphemes().add(morpheme);
            } else if (placement < 100) {
                getNameMorphemes().add(morpheme);
            }
        }
    }

    /**
     * Creating citynames.
     * @return hopefully a valid cityname
     */
    public String createCity() {
        StringBuilder cityname = new StringBuilder();
        Random rng = new Random();

        while(getCityMorphemes().size() < 1) {
            createMorpheme();
        }

        while(getCommonMorphemes().size() < 5) {
            createMorpheme();
        }

        List<String> nameSyllables = new ArrayList<>();
        int length = rng.nextInt(MAX_WORD_LENGTH-1)+1;

        int cmLength = getCityMorphemes().size();
        int coLength = getCommonMorphemes().size();

        // If we have a language that uses their Morphemes in the beginning
        if(PLACE_NAME_START) {
            nameSyllables.add(getCityMorphemes().get(rng.nextInt(cmLength)));
        }
        // But we need to take care not to add too many morphemes before our ending
        else {
            length--;
        }
        // Creating a word with maximal size of MAX_WORD_LENGTH
        // TODO: Let words hover around AVG_WORD_LENGTH;
        while(nameSyllables.size() <= length) {
            nameSyllables.add(getCommonMorphemes().get(rng.nextInt(coLength)));
        }

        // If we have language that uses their morphemes in the end
        if(PLACE_NAME_END) {
            nameSyllables.add("-"+getCityMorphemes().get(rng.nextInt(cmLength)));
        }

        for(String s:nameSyllables) {
            cityname.append(s);
        }

        String toReturn = cityname.toString();

        if(toReturn.equals("")) {
            return "Something failed!!!!";
        }

        // Capitalize the first letter
        return toReturn.substring(0, 1).toUpperCase() + toReturn.substring(1);
    }

    /**
     * Creating words.
     * @return hopefully a valid word
     */
    public String createWord() {
        StringBuilder word = new StringBuilder();
        Random rng = new Random();

        int coLength = getCommonMorphemes().size();

        while(coLength < 5) {
            createMorpheme();
        }

        int length = rng.nextInt(MAX_WORD_LENGTH-1)+1;


        // Creating a word with maximal size of MAX_WORD_LENGTH
        // TODO: Let words hover around AVG_WORD_LENGTH;
        while(word.length() <= length) {
            word.append(getCommonMorphemes().get(rng.nextInt(coLength)));
        }

        String toReturn = word.toString();

        // some languages are not capitalized
        if(!CAPITALIZATION) {
            return toReturn;
        }

        // in the other languages some words are not capitalized
        if(rng.nextBoolean()) {
            return toReturn;
        }


        if(toReturn.equals("")) {
            return "Something failed!!!!";
        }

        // Capitalize the first letter
        return toReturn.substring(0, 1).toUpperCase() + toReturn.substring(1);
    }



    private boolean morphemeExists(String morpheme) {
        return getMorphemes().contains(morpheme);
    }

    private class Parser {
        private int pos = 0;

        private final String FORMAT;

        Parser(String format) {
            this.FORMAT = format;
        }

        /**
         * @return the next phoneme, '.' if no more chars, ' ' if skipping
         */
        char getNext(boolean skip) {

            if(pos == -1) {
                return '.';
            }

            // charAt(0) gives the first character
            char toReturn = FORMAT.charAt(pos);

            // increase pointer to check for modifiers
            pos++;

            // We might have already read the exact last symbol!
            if (pos >= FORMAT.length()) {
                return toReturn;
            }

            //Checks if we need to check skipping optional character
            if (FORMAT.charAt(pos) == '?') {
                if (skip) {
                    //Sets position of pointer to the next character in line
                    pos++;

                    // And check for OoB
                    if (outOfBounds(FORMAT,pos))
                        return ' ';

                    //Famous Double skip, enables skipping a character ONLY if the previous character was skipped
                    if(FORMAT.charAt(pos) == '+' && new Random().nextBoolean()) {
                        //Sets position of pointer to the character to be skipped
                        pos++;
                        //Sets position of pointer to next character after that.
                        pos++;
                    } else if(FORMAT.charAt(pos) != '+') {
                        // if there is no '+' don't just skip all of the rest!
                        return ' ';
                    } else {
                        //Sets pointer to character after '+', which will be read in next run
                        pos++;
                    }
                    // we return empty handed because of the skip
                    return ' ';
                }
                //Sets position of pointer to the next character in line
                pos++;

                // And check for OoB
                if (outOfBounds(FORMAT,pos))
                    return toReturn;

                // Double ? (e.g. C/L?VF??) should be much less likely to trigger.
                if(FORMAT.charAt(pos) == '?') {
                    // Sets position of pointer to the next character in line
                    pos++;

                    // Check to skip.
                    if(new Random().nextBoolean()) {
                        // we return empty handed because of the skip
                        return ' ';
                    }
                }
            }

            if (FORMAT.charAt(pos) == '/') {
                //Checked if we need to consider skipping replaceable character
                // Checks if we replace the read character with the next in line
                if (skip) {
                    // If yes, we increment pointer to point to next character
                    pos++;
                    // It will be read next turn
                    // For now we return empty handed.
                    return ' ';
                } else {
                    // If we don't replace, we need to jump behind the next character.
                    pos++;
                    // If it's another modifier, it doesn't matter.
                    pos++;
                }
            }

            return toReturn;
        }

        boolean hasNext() {
            if (pos >= FORMAT.length()) {
                pos = -1;
            }
            return pos != -1;
        }

        boolean outOfBounds(String s, int p) {
            return s.length() >= p;
        }
    }
}
