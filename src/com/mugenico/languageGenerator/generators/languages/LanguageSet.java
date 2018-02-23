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
        A("A"),
        S("S"),
        J("J");

        private final String fieldDescription;


        Languages(String value) {
            fieldDescription = value;
        }

        @Override
        public String toString() {
            return ""+fieldDescription;
        }

        public String getFieldDescription() {
            return fieldDescription;
        }

        public static Languages getField(String description) {
            for(Languages a: Languages.values()) {
                if(Objects.equals(a.getFieldDescription(), description)) {
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

    private String CODE = "BLANK";
    private String NAME = "BLANK";
    private String CONSTRUCT;
    private String GRAMMAR;

    private String[] CONSONANTS;
    private final String POSSIBLE_CONSONANTS="bcdfghjklrmnpqstvwxyz";
    private final String[] POSSIBLE_C_MODIFIERS={"sk", "sch", "st", "kh", "ch", "sh", "sc", "hk", "kt", "ct", "pr", "br", "vr", "wr", "zt", "tz", "tzt"};

    private String[] VOWELS;
    private final String POSSIBLE_VOWELS="aeiou";
    private final String[] POSSIBLE_V_MODIFIERS= {"ui", "iu", "io", "au", "eu", "ou", "uo", "ao", "oe", "ä", "ö", "ü", "å", "ū", "ō"};

    private String[] LIQUIDS;
    private final String POSSIBLE_LIQUIDS = "rl";
    private final String[] POSSIBLE_L_MODIFIERS = {"sch", "sh"};

    private String[] SIBILANTS;
    private final String POSSIBLE_SIBILANTS = "szj";
    private final String[] POSSIBLE_S_MODIFIERS = {"sch", "sh", "st", "zh"};

    private String[] FINALS;
    private final String POSSIBLE_FINALS = "ktgn";
    private final String[] POSSIBLE_F_MODIFIERS = {"nn", "nd", "ng", "sk"};

    private String[] BANNED_COMBOS;
    private boolean CAPITALIZATION;
    private boolean PLACE_NAME_START;
    private boolean PLACE_NAME_END;
    private int MAX_WORD_LENGTH;
    private double AVG_WORD_LENGTH;

    private static RNG rng = new RNG();

    public LanguageSet() {
        do {
            generateCONSONANTS();
            generateVOWELS();
            generateLIQUIDS();
            generateSIBILANTS();
            generateFINALS();
            generateBANNED_COMBOS();
            generateBANNED_COMBOS();
            decideCAPTITALIZATION();
            generatePLACE_NAME_POSITION();
            //TODO: Add functions in Words.java to work with counting characters instead of morphemes
            generateMAX_WORD_LENGTH('m');
            generateAVG_WORD_LENGTH('m');


            generateCONSTRUCT();
            generateGrammar();
        }while(estimateCombinations() < 999);
    }

    public String getCODE(Words words) {
        if (CODE.equals("BLANK")) {
            generateCODE(words);
        }
        return CODE;
    }

    public String getNAME(Words words) {
        if (NAME.equals("BLANK")) {
           generateCODE(words);
        }
        return NAME;
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
     * TODO: This is a pain, because this function creates a new words class, that has nothing to do with anything.
     * TODO: So the Shorthand it returns is meaningless, even though created by the right grammar.
     * Generating a shorthand for the language
     */
    private void generateCODE(Words words) {
        NAME = words.getLanguageName();
        CODE = NAME.substring(0,3);
    }

    /**
     * Generating a random construct to use for our language
     */
    private void generateCONSTRUCT() {

        boolean needsL = false;
        boolean needsS = false;
        boolean needsV = true;

        StringBuilder c = new StringBuilder();
        c.append('C');
        c.append('V');

        if(LIQUIDS.length != 0) {
            c.append('L');
            needsL = true;
        }
        if(SIBILANTS.length != 0) {
            c.append('S');
            needsS = true;
        }
        if(FINALS.length != 0) {
            c.append('F');
        }

        String components = c.toString();
        String[] operators = {"/","?", "??", "?+"};
        String lastOperator = "";

        StringBuilder con = new StringBuilder();

        boolean needsMore = true;
        int realLength = 0;
        // We want our construct to be at least 4 long, likely to be longer
        while((realLength <= 3) || (rng.nextLikelyFalse()&&rng.nextBoolean()) || needsMore) {
            char add = components.charAt(rng.nextInt(components.length()));

            // If we are at the first character, we only accept C or V
            if(con.length()==0) {
                if((add == 'C' || add == 'V')) {
                    con.append(add);
                    realLength++;
                    continue;
                } else {
                    continue;
                }
            }

            // Force a V within the first few letters of the word. "Gjgndjaj" just isn't workable.
            // TODO: Try to find a better solution than hardcoding
            if(realLength > 2 && !con.toString().contains("V")) {
                add='V';
            }

            // We only want Finals to be at the end, as the name implies, so if we get 'F' before our length reaches 3, we roll again until we have ANYTHING else
            // If we already reached 4, we break out of our loop, and let the final be final.
            if(con.length() >= 4 && add == 'F') {
                con.append(add);
                break;
            } else while (add == 'F') {
                // The ANYTHING referred to above can be a hard coded vowel, if the rng says so. This is to heighten the chance of comprehensible stuff.
                add = components.charAt(rng.nextInt(components.length()));
                if(rng.nextBoolean()) {
                    add = 'V';
                }
            }

            // we also don't want the same character twice in a row
            // con.charAt(con.length()-1) returns the last character of our string
            try {
                // If the code wants to randomly give us a double character, we will accept it after a few tries.
                int luckyDouble = 0;
                while (con.charAt(con.length()-1) == add && luckyDouble < 4) {
                    char newAdd = components.charAt(rng.nextInt(components.length()));
                    if(newAdd == 'F') {
                        continue;
                    }
                    add = newAdd;
                    luckyDouble++;
                }
            } catch(StringIndexOutOfBoundsException ignored) {

            }

            // Checking if we have all we wanted, while we still have letters unmentioned, but needed, we cannot end construction
            if(add == 'L') {
                needsL = false;
            }
            if(add == 'S') {
                needsS = false;
            }
            if(add == 'V') {
                needsV = false;
            }

            if(add == 'F') {
                System.out.println("ERROR OCCURED! FINAL WHERE NONE SHOULD BE. ABORT!!!");
                break;
            }

            needsMore = needsL||needsS||needsV;

            con.append(add);
            realLength++;

            // Sometimes we add operators for funsies, but only after some stability
            if(rng.nextBoolean() && realLength>2) {
                String operator = operators[rng.nextInt(operators.length)];
                if(!Objects.equals(operator, lastOperator)) {
                    con.append(operator);
                    lastOperator = operator;
                }
            }
        }

        // Finally, let our monster be created =3
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
            if (rng.nextLikelyTrue() && !Objects.equals(letter, "P")) {
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
        String consonants = POSSIBLE_CONSONANTS;
        String[] modifier = POSSIBLE_C_MODIFIERS;
        List<String> c = new ArrayList<>();

        // Stop earliest after 13 consonants have been added, afterwords, consult random chance to get more consonsonants
        while(c.size() <= 13 || rng.nextBoolean()) {
            String add;
            // We want very few of the above "modifiers", so we lessen the probability by running the improbable check twice
            if (rng.nextLikelyFalse() && rng.nextLikelyFalse()) {
                add = "" + modifier[rng.nextInt(modifier.length)];

                //"modifiers" only make sense, if the base consonant is already part of the alphabet.
                //For example, having "st" in a language but not "s" would be weird.
                if(!c.contains(add.substring(0,1))) {
                    continue;
                }
            } else {
                add = "" + consonants.charAt(rng.nextInt(consonants.length()));
            }


            // Check if we've already added this consonant or modifier, add only if needs be;
            if (!c.contains(add)) {
                c.add(add);
            }
        }

        CONSONANTS = c.toArray(new String[0]);
    }

    /**
     * Generating a random pool of vowels to use for the language
     */
    private void generateVOWELS() {
        String vowels = POSSIBLE_VOWELS;
        String[] diphthongs = POSSIBLE_V_MODIFIERS;
        List<String> v = new ArrayList<>();

        // Stop earliest after 3 vowels have been added, after that, consult random chance to get more vowels
        while(v.size() <= 3 || rng.nextBoolean()) {
            String add;
            // We want very few of the above "diphthongs", so we lessen the probability by running the improbable check twice
            if (rng.nextLikelyFalse() && rng.nextLikelyFalse()) {
                add = "" + diphthongs[rng.nextInt(diphthongs.length)];

                // We only want a two letter diphthong like "au" to be inserted, if we also have at least "a", so we add both
                if(add.length()>=2 && !v.contains(add.substring(0,1))) {
                    v.add(add.substring(0,1));
                }
            } else {
                add = "" + vowels.charAt(rng.nextInt(vowels.length()));
            }

            // Check if we already choose this consonant or modifier, add only if needs be;
            if (!v.contains(add)) {
                v.add(add);
            }
        }

        VOWELS = v.toArray(new String[0]);

    }

    /**
     * Generating a random pool of liquids to use for the language
     */
    private void generateLIQUIDS() {
        String Liquids = POSSIBLE_LIQUIDS;
        String[] modifier = POSSIBLE_L_MODIFIERS;
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
        String sibilants = POSSIBLE_SIBILANTS;
        String[] s_modifier = POSSIBLE_S_MODIFIERS;
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
        String finals = POSSIBLE_FINALS;
        String[] f_modifier = POSSIBLE_F_MODIFIERS;
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
     * Randomly choosing a maximal word length (where signal defines whether we are counting morphemes or characters)
     * Default is morphemes
     */
    private void generateMAX_WORD_LENGTH(char signal) {
        switch(signal) {
            case 'm':
                // Generate a maximal word length between 2 and 5
                MAX_WORD_LENGTH=rng.nextBoundInt(2,5);
                break;
            case 'c':
                // Generate a maximal word length between 1 and 5
                MAX_WORD_LENGTH=rng.nextBoundInt(5,20);
                break;
            default:
                // Generate a maximal word length between 1 and 5
                MAX_WORD_LENGTH=rng.nextBoundInt(2,5);
                break;
        }
    }

    /**
     * Randomly choosing an average word length
     */
    private void generateAVG_WORD_LENGTH(char signal) {
        switch(signal) {
            case 'm':
                // Generate a random average word length that is at least .3 smaller than MAX_WORD_LENGTH
                do {
                    //Random Value between 0.5 and 3
                    AVG_WORD_LENGTH = 0.5 + rng.nextFloat() * 2.5;
                } while(AVG_WORD_LENGTH > MAX_WORD_LENGTH-0.5);
                break;
            case 'c':
                // Generate a random average word length that is at least 3 smaller than MAX_WORD_LENGTH
                do {
                    //Random Value between 3 and 17
                    AVG_WORD_LENGTH = 3 + rng.nextFloat() * 14;
                } while(AVG_WORD_LENGTH > MAX_WORD_LENGTH-3);
                break;
            default:
                // Generate a random average word length that is at least .3 smaller than MAX_WORD_LENGTH
                do {
                    //Random Value between 0.5 and 3
                    AVG_WORD_LENGTH = 0.5 + rng.nextFloat() * 2.5;
                } while(AVG_WORD_LENGTH > MAX_WORD_LENGTH-0.3);
                break;
        }
    }

    /**
     * Randomly choose place name position
     */
    private void generatePLACE_NAME_POSITION() {
        PLACE_NAME_START = false;
        PLACE_NAME_END = false;

        if(rng.nextBoolean()) {
            PLACE_NAME_START = true;
            return;
        }
        if(rng.nextBoolean()) {
            PLACE_NAME_END= true;
            return;
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

        for(int i = 1;i<=cCtr;i++) {
            conCtr = conCtr*cCtr;
        }

        for(int i = 1;i<=vCtr;i++) {
            conCtr = conCtr*vCtr;
        }

        for(int i = 0;i<fCtr;i++) {
            conCtr = conCtr*fCtr;
        }

        for(int i = 0;i<sCtr;i++) {
            conCtr = conCtr*sCtr;
        }

        for(int i = 0;i<lCtr;i++) {
            conCtr = conCtr*lCtr;
        }

        return conCtr;
    }
}
