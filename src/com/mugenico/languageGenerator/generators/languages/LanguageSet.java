package com.mugenico.languageGenerator.generators.languages;

import com.mugenico.languageGenerator.util.RNG;

import java.util.*;

/**
 * Class to define all generation of languages, and all predefined languages.
 *
 * Created by Gemin on 29.08.2017.
 */
public class LanguageSet {

    public enum Languages {
        A('A'),
        S('S'),
        J('J');

        private final char fieldDescription;


        Languages(char value) {
            fieldDescription = value;
        }

        @Override
        public String toString() {
            return ""+fieldDescription;
        }

        public char getFieldDescription() {
            return fieldDescription;
        }

        public static Languages getField(char description) {
            for(Languages a: Languages.values()) {
                if(a.getFieldDescription() == description ) {
                    return a;
                }
            }
            return null;
        }


        private static final Languages[] VALUES = values();
        private static final int SIZE = VALUES.length;
        private static final Random RANDOM = new Random();

        public static Languages randomLanguage()  {
            return VALUES[RANDOM.nextInt(SIZE)];
        }
    }

    private char CODE;
    private String CONSTRUCT;
    private String GRAMMAR;
    private String[] CONSONANTS;
    private String[] VOWELS;
    private String[] LIQUIDS;
    private String[] SIBILANTS;
    private String[] FINALS;
    private String[] BANNED_COMBOS;
    private boolean CAPITALIZATION;
    private boolean PLACE_NAME_START;
    private boolean PLACE_NAME_END;
    private int MAX_WORD_LENGTH;
    private double AVG_WORD_LENGTH;

    private static RNG rng = new RNG();

    public LanguageSet() {
        do {
            generateCODE();
            generateCONSONANTS();
            generateVOWELS();
            generateLIQUIDS();
            generateSIBILANTS();
            generateFINALS();
            generateBANNED_COMBOS();
            generateBANNED_COMBOS();
            decideCAPTITALIZATION();
            generatePLACE_NAME_POSITION();
            generateMAX_WORD_LENGTH();
            generateAVG_WORD_LENGTH();


            generateCONSTRUCT();
            generateGrammar();
        }while(estimateCombinations() < 100);
    }

    public char getCODE() {
        return CODE;
    }

    public String getCONSTRUCT() {
        return CONSTRUCT;
    }

    public String getGRAMMAR() {
        return GRAMMAR;
    }

    public String[] getCONSONANTS() {
        return CONSONANTS;
    }

    public String[] getVOWELS() {
        return VOWELS;
    }

    public String[] getLIQUIDS() {
        return LIQUIDS;
    }

    public String[] getSIBILANTS() {
        return SIBILANTS;
    }

    public String[] getFINALS() {
        return FINALS;
    }

    public String[] getBANNED_COMBOS() {
        return BANNED_COMBOS;
    }

    public boolean isCAPITALIZATION() {
        return CAPITALIZATION;
    }

    public boolean isPLACE_NAME_START() {
        return PLACE_NAME_START;
    }

    public boolean isPLACE_NAME_END() {
        return PLACE_NAME_END;
    }

    public int getMAX_WORD_LENGTH() {
        return MAX_WORD_LENGTH;
    }

    public double getAVG_WORD_LENGTH() {
        return AVG_WORD_LENGTH;
    }

    /**
     * Generating a random name for the language
     */
    private void generateCODE() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        CODE = alphabet.charAt(rng.nextInt(alphabet.length()));
    }

    /**
     * Generating a random construct to use for our language
     */
    private void generateCONSTRUCT() {

        boolean hasL = true;
        boolean hasS = true;
        boolean hasF = true;

        StringBuilder c = new StringBuilder();
        c.append('C');
        c.append('V');

        if(LIQUIDS.length != 0) {
            c.append('L');
            hasL = false;
        }
        if(SIBILANTS.length != 0) {
            c.append('S');
            hasS = false;
        }
        if(FINALS.length != 0) {
            c.append('F');
            hasF = false;
        }

        String components = c.toString();
        String[] operators = {"/","?", "??", "?+"};

        StringBuilder con = new StringBuilder();

        boolean firstCharacterSet = false;
        // We want our construct to be at least 4 long, likely to be longer
        while(con.length() <= 4 || (rng.nextLikelyFalse() && hasF && hasL && hasS)) {
            char add = components.charAt(rng.nextInt(components.length()));

            if(!firstCharacterSet) {
                if((add == 'C' || add == 'V')) {
                    con.append(add);
                    firstCharacterSet = true;
                    continue;
                } else {
                    continue;
                }
            }

            // We only want Finals to be at the end, as the name implies, so if we get 'F' before our length reaches 3, we roll again until we have ANYTHING else
            // If we already reached 3, we break out of our loop, and let the final be final.
            if(con.length() > 3 && add == 'F') {
                con.append(add);
                break;
            } else while (add == 'F') {
                add = components.charAt(rng.nextInt(components.length()));
            }

            // we also don't want the same character twice in a row
            // con.charAt(con.length()-1) returns the last character of our string
            try {
                while (con.charAt(con.length()-1) == add) {
                    add = components.charAt(rng.nextInt(components.length()));
                }
            } catch(StringIndexOutOfBoundsException e) {
                add = add;
            }

            if(add == 'L') {
                hasL = true;
            }
            if(add == 'S') {
                hasS = true;
            }
            if(add == 'F') {
                hasF = true;
            }

            con.append(add);

            // Sometimes we add operators for funsies
            // Every combination of stuff should be handled fine... hopefully
            // TODO: TEST, DELETE THIS IF TESTS ARE GOOD
            if(rng.nextBoolean()) {
                con.append(operators[rng.nextInt(operators.length)]);
            }
        }

        // Finally, let our monster be created =D
        CONSTRUCT = con.toString();

    }

    /**
     * randomly generating a grammar. Choose which is first (SPO), insert some optional points
     */
    public void generateGrammar() {
        String grammar = "SPO";

        // Shuffle our SPO --> OPS --> SOP --> PSO etc. creating random optional grammar
        List<String> letters = Arrays.asList(grammar.split(""));
        Collections.shuffle(letters);

        // We want one optional Grammar for our entire grammar.
        String optional = generateOptionalGrammar();

        StringBuilder g = new StringBuilder();

        for(String letter : letters) {
            // Check if we insert an optional block, but only if we don't have a preposition next
            if (rng.nextLikelyFalse() && !Objects.equals(letter, "P")) {
                g.append(optional);
                // In few cases add a star to this optional block
                if (rng.nextLikelyFalse()) {
                    g.append('*');
                }
            }
            g.append(letter);
        }

        // Check if we finish with an optional block
        if (rng.nextLikelyFalse()) {
            g.append(optional);
            // In some cases add a star to this optional block
            if (rng.nextBoolean()) {
                g.append('*');
            }
        }

        String a = g.toString().replaceAll("^[(,]+", "(");
        a = a.replaceAll("[,)*]+$", ")*");
        GRAMMAR = a.replaceAll("[,)]+$", ")");
    }

    /**
     * Randomly generate optional grammar for the grammar. comes in format (,SPO,)
     * If we want )* to be used, we need to add the asterisk within the main function
     *
     * @return Randomly created optionalGrammar
     */
    private String generateOptionalGrammar() {
        String grammar = "SPO";

        StringBuilder optional = new StringBuilder();
        optional.append("(");

        boolean needsS = rng.nextLikelyTrue();
        boolean needsO = rng.nextLikelyTrue();
        boolean usesCommata = rng.nextLikelyTrue();

        if(!needsS && !needsO) {
            if (rng.nextBoolean()) {
                needsO = true;
            } else {
                needsS = true;
            }
        }

        if(usesCommata) {
            optional.append(',');
        }

        // Shuffle our SPO --> OPS --> SOP --> PSO etc. creating random optional grammar
        List<String> letters = Arrays.asList(grammar.split(""));
        Collections.shuffle(letters);
        for (String letter : letters) {
            if(!needsO && letter.equals("O")) {
                continue;
            }
            if(!needsS && letter.equals("S")) {
                continue;
            }
            optional.append(letter);
        }

        if(usesCommata) {
            optional.append(',');
        }

        optional.append(")");

        return optional.toString();
    }

    /**
     * Generating a random pool of consonants for our language
     */
    private void generateCONSONANTS() {
        String consonants = "bcdfghklmnpqrstvwxyz";
        String[] modifier = {"sk", "sch", "st", "kh", "ch", "sh", "j"};
        List<String> c = new ArrayList<>();

        // Stop earliest after 10 consonants have been added, afterwords, consult random chance to get more consonsonants
        while(c.size() <= 15 || rng.nextBoolean()) {
            String add;
            // We want very few of the above "modifiers", so we lessen the probability by running the improbable check twice
            if (rng.nextLikelyFalse() && rng.nextLikelyFalse()) {
                add = "" + modifier[rng.nextInt(modifier.length)];
            } else {
                add = "" + consonants.charAt(rng.nextInt(consonants.length()));
            }


            // Check if we already choose this consonant or modifier, add only if needs be;
            boolean contained = false;
            for(String check:c) {
                if(Objects.equals(check, add)) {
                    contained = true;
                }
            }
            if (!contained) {
                c.add(add);
            }
        }

        CONSONANTS = c.toArray(new String[0]);
    }

    /**
     * Generating a random pool of vowels to use for the language
     */
    private void generateVOWELS() {
        String vowels = "aeiou";
        String[] diphthongs = {"au", "eu", "ou", "uo", "ao", "oe", "ä", "ö", "ü", "å", "ū", "ō"};
        List<String> v = new ArrayList<>();

        // Stop earliest after 4 vowels have been added, after that, consult random chance to get more vowels
        while(v.size() <= 4 || rng.nextBoolean()) {
            String add;
            // We want very few of the above "diphthongs", so we lessen the probability by running the improbable check twice
            if (rng.nextLikelyFalse() && rng.nextLikelyFalse()) {
                add = "" + diphthongs[rng.nextInt(diphthongs.length)];
            } else {
                add = "" + vowels.charAt(rng.nextInt(vowels.length()));
            }
            // Check if we already choose this consonant or modifier, add only if needs be;

            boolean notContained = true;
            for(String check:v) {
                if(Objects.equals(check, add)) {
                    notContained = false;
                }
            }
            if (notContained) {
                v.add(add);
            }
        }

        VOWELS = v.toArray(new String[0]);

    }

    /**
     * Generating a random pool of liquids to use for the language
     */
    private void generateLIQUIDS() {
        String Liquids = "rl";
        String[] modifier = {"sch", "sh"};
        List<String> l = new ArrayList<>();

        // Liquids will only be added by chance, and if, than only few. One or two should be enough
        while(rng.nextLikelyFalse()) {
            String add;
            // We want very few of the above "modifiers", so we lessen the probability by running the improbable check twice
            if (rng.nextLikelyFalse() && rng.nextLikelyFalse()) {
                add = "" + modifier[rng.nextInt(modifier.length)];
            } else {
                add = "" + Liquids.charAt(rng.nextInt(Liquids.length()));
            }
            // Check if we already choose this consonant or modifier, add only if needs be;
            if (!l.contains(add)) {
                l.add(add);
            }
        }

        if(l.size() <= 0) {
            LIQUIDS = new String[]{};
            return;
        }
        LIQUIDS = l.toArray(new String[0]);
    }

    /**
     * Generating a random pool of sibilants to use in our language
     */
    private void generateSIBILANTS() {
        String sibilants = "szj";
        String[] s_modifier = {"sch", "sh", "st", "zh"};
        List<String> s = new ArrayList<>();

        // sibilants will only be added by chance, and if, than only few. One or two should be enough
        while(rng.nextLikelyFalse()) {
            String add;
            // We want a few of the above "modifiers", so we lessen the probability by running the improbable check twice
            if (rng.nextLikelyFalse()) {
                add = "" + s_modifier[rng.nextInt(s_modifier.length)];
            } else {
                add = "" + sibilants.charAt(rng.nextInt(sibilants.length()));
            }
            // Check if we already choose this sibilant or modifier, add only if needs be;
            if (!s.contains(add)) {
                s.add(add);
            }
        }

        if(s.size() <= 0) {
            SIBILANTS = new String[]{};
            return;
        }
        SIBILANTS = s.toArray(new String[0]);
    }

    /**
     * Generating a random pool of finals to use in our language
     */
    private void generateFINALS() {
        String finals = "ktg";
        String[] f_modifier = {"nd", "ng", "sk"};
        List<String> f = new ArrayList<>();

        // finals will only be added by chance, and if, than only few. One or two should be enough
        while(rng.nextLikelyFalse()) {
            String add;
            // We want a few of the above "modifiers", so we lessen the probability by running the improbable check twice
            if (rng.nextLikelyFalse()) {
                add = "" + f_modifier[rng.nextInt(f_modifier.length)];
            } else {
                add = "" + finals.charAt(rng.nextInt(finals.length()));
            }
            // Check if we already choose this final or modifier, add only if needs be;
            if (!f.contains(add)) {
                f.add(add);
            }
        }


        if(f.size() <= 0) {
            FINALS = new String[]{};
            return;
        }
        FINALS = f.toArray(new String[0]);
    }

    /**
     * Generating a pool of  banned combinations of phonemes using CONSTRUCT, CONSONANTS and VOWELS
     */
    private void generateBANNED_COMBOS() {
        List<String> bc = new ArrayList<>();

        bc.add(CONSONANTS[rng.nextInt(CONSONANTS.length)] + VOWELS[rng.nextInt(VOWELS.length)]);

        BANNED_COMBOS = bc.toArray(new String[0]);
        // This is hard
        //TODO: Implement this
    }

    /**
     * Deciding randomly if our language uses capitalization or not
     */
    private void decideCAPTITALIZATION() {
        CAPITALIZATION = false;
        // Mostly, it shall use capitalization
        if(rng.nextLikelyTrue()) {
            CAPITALIZATION = true;
        }
    }

    /**
     * Randomly choosing a maximal word length
     */
    private void generateMAX_WORD_LENGTH() {
        // Generate a maximal word length between 1 and 5
        MAX_WORD_LENGTH=rng.nextBoundInt(2,5);
    }

    /**
     * Randomly choosing an average word length
     */
    private void generateAVG_WORD_LENGTH() {
        // Generate a random average word length that is at least .3 smaller than MAX_WORD_LENGTH
        do {
            //Random Value between 0.5 and 3
            AVG_WORD_LENGTH = 0.5 + rng.nextFloat() * 2.5;
        } while(AVG_WORD_LENGTH > MAX_WORD_LENGTH-0.3);
    }

    /**
     * Randomly choose place name position
     */
    private void generatePLACE_NAME_POSITION() {
        PLACE_NAME_START = false;
        PLACE_NAME_END = false;

        if(rng.nextBoolean()) {
            PLACE_NAME_START = true;
        }
        if(rng.nextBoolean()) {
            PLACE_NAME_END= true;
        }
    }

    /**
     * Estimates the possible combinations we created
     */
    private int estimateCombinations() {
        int conCtr = 0;
        int cCtr = CONSONANTS.length;
        int vCtr = VOWELS.length;
        int fCtr = FINALS.length;
        int sCtr = SIBILANTS.length;
        int lCtr = LIQUIDS.length;

        // count all occurrences of C,V,L,S,F in CONSTRUCT
        for(char c:CONSTRUCT.toCharArray()) {
            if(c !='?' && c != '/' && c!='+') {
                conCtr++;
            }
        }

        return conCtr*(cCtr+vCtr+fCtr+sCtr+lCtr);
    }
}
