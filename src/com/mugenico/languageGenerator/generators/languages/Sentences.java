package com.mugenico.languageGenerator.generators.languages;

import com.mugenico.languageGenerator.util.RNG;
import com.mugenico.languageGenerator.generators.languages.LanguageSet.Languages;

import java.util.*;

/**
 * Using the Word-Generator to create sentences following predefined patterns.
 * For this we create some words and categorise them within this class.
 * Categories we use for now: Noun, Verb, Adjective, Article, Conjunction.
 *
 * TODO: Patters are randomly generated
 *
 * Created by Gemin on 22.08.2017.
 */
public class Sentences {

    private Words words;

    // Define some grammar patterns
    private static final String GRAMMAR_PATTERN_A = "SPO(,PO)*";
    private static final String GRAMMAR_PATTERN_J = "(OP)S(OP)OP";
    private static final String GRAMMAR_PATTERN_S = "SPO(,SOP)";

    private String GRAMMAR_PATTERN;

    private RNG rng;

    // indefinite length
    private List<String> nouns = new ArrayList<>();
    // indefinite length
    private List<String> verbs = new ArrayList<>();
    // indefinite length
    private List<String> adjectives = new ArrayList<>();
    // length should be 0~4 on this for now
    private List<String> articles = new ArrayList<>();
    private int articleCount = 0;
    // should not get too long, as most languages don't know nearly as many conjunctions as nouns
    private List<String> conjunctions = new ArrayList<>();


    // default constructor to prefill some nouns, verbs and adjectives
    public Sentences() {
        this.rng = new RNG();
        try {
            words = new Words();
            definePattern();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        initializeWords(100);
    }

    public Sentences(LanguageSet ls) {
        this.rng = ls.getRng();
        words = new Words(ls);

        initializeWords(1000);

        GRAMMAR_PATTERN = ls.getGRAMMAR();
    }

    public Sentences(String code) {
        try {
            words = new Words(code);
            definePattern();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        initializeWords(100);
    }

    private void initializeWords(int amt) {
        Random rng = new Random();

        // we create 100 words
        for(int i = 0; i<=amt; i++) {
            String next = words.createWord();

            // For now we only want each word once.
            // TODO: Allow ambiguity
            if(wordExists(next)) {
                i--;
                continue;
            }

            // If our language doesn't ever use capital letters,
            // we can just randomly assign words created to one of our categories
            if(!words.usesCAPITALIZATION()) {
                switch (rng.nextInt(3)) {
                    case 1:
                        verbs.add(next);
                        break;
                    case 2:
                        adjectives.add(next);
                        break;
                    default:
                        nouns.add(next);
                }
                continue;
            }

            // Languages that use capitalization will most likely capitalize nouns
            // So if our word starts with a lower case, we assign it as verb or adjective
            if(!next.equals(next.toLowerCase())) {
                nouns.add(next);
            } else if(rng.nextBoolean()) {
                verbs.add(next);
            } else {
                adjectives.add(next);
            }
        }

        // Creating 0-4 articles to use in our language
        for(int i = 0; i<4; i++) {

            // We make it improbable to have 4 articles, by checking every time if we need another one
            // TODO: Find better distribution
            if(rng.nextBoolean()) {
                break;
            }

            String next = words.createArticle();
            if(wordExists(next)) {
                i--;
                continue;
            }
            articles.add(next);
            articleCount++;
        }

        // Creating some conjuntions to use
        for(int i = 0; i<=20;i++) {
            String next = words.createConjunction();

            if(wordExists(next)) {
                i--;
                continue;
            }
            conjunctions.add(next);

            if(Objects.equals(next, "")) {
                break;
            }

            // Create at least 10 conjunctions, then check for more
            if(rng.nextBoolean() && conjunctions.size()>=10) {
                break;
            }
        }
    }

    private void definePattern() {
        if(Objects.equals(getUsedLanguage(), Languages.A.getFieldDescription())) {
            GRAMMAR_PATTERN = GRAMMAR_PATTERN_A;
        } else if(Objects.equals(getUsedLanguage(), Languages.J.getFieldDescription())) {
            GRAMMAR_PATTERN = GRAMMAR_PATTERN_J;
        } else if(Objects.equals(getUsedLanguage(), Languages.S.getFieldDescription())) {
            GRAMMAR_PATTERN = GRAMMAR_PATTERN_S;
        }
    }


    private boolean wordExists(String word) {
        List<String> allWords = new ArrayList<>();
        allWords.addAll(nouns);
        allWords.addAll(verbs);
        allWords.addAll(adjectives);
        allWords.addAll(conjunctions);
        allWords.addAll(articles);

        return allWords.contains(word);
    }

    public String createSentence() {
        StringBuilder sentence = new StringBuilder();
        GrammarParser gp = new GrammarParser(GRAMMAR_PATTERN);

        while(gp.hasNext() && sentence.length() <= 10000) {
            char next = gp.getNextGrammar();
            switch (next) {
                case 'S':
                    if (articleCount != 0 && rng.nextBoolean()) {
                        sentence.append(" ");
                        sentence.append(articles.get(rng.nextInt(articleCount)));
                    }
                    if(rng.nextBoolean()) {
                        sentence.append(" ");
                        sentence.append(adjectives.get(rng.nextInt(adjectives.size())));
                    }
                    sentence.append(" ");
                    sentence.append(nouns.get(rng.nextInt(nouns.size())));
                    break;
                case 'O':
                    if (articleCount != 0 && rng.nextLikelyFalse()) {
                        sentence.append(" ");
                        sentence.append(articles.get(rng.nextInt(articleCount)));
                    }
                    if(rng.nextBoolean()) {
                        sentence.append(" ");
                        sentence.append(adjectives.get(rng.nextInt(adjectives.size())));
                    }
                    sentence.append(" ");
                    sentence.append(nouns.get(rng.nextInt(nouns.size())));
                    break;
                case 'P':
                    sentence.append(" ");
                    String verb = verbs.get(rng.nextInt(verbs.size()));
                    sentence.append(verb);
                    break;
                case ',':
                    if(conjunctions.size()==0) {
                        sentence.append(",");
                        break;
                    }
                    sentence.append(", ");
                    sentence.append(conjunctions.get(rng.nextInt(conjunctions.size())));
                    break;
                case 'e':
                    return sentence.toString();
                default:
                    break;
            }
        }
        return toUpperCase(sentence.append(".").toString().trim());
    }

    public String createParagraph() {
        StringBuilder paragraph = new StringBuilder();

        boolean tooMuchAlready = false;
        int steps = 0;

        while (!tooMuchAlready) {
            paragraph.append(createSentence());
            paragraph.append("\n");
            steps++;
            if(steps<3) {
                continue;
            }
            tooMuchAlready = rng.nextBoolean();
        }

        return toUpperCase(paragraph.toString().trim());
    }

    public String createParagraph(int length) {
        StringBuilder paragraph = new StringBuilder();

        for(int i = 0; i<length;i++) {
            paragraph.append(createSentence());
            paragraph.append("\n");
        }
        return toUpperCase(paragraph.toString().trim());
    }

    public String createLoremIpsum(int length) {
        StringBuilder loremIpsum = new StringBuilder();

        while (loremIpsum.length()<length) {
            loremIpsum.append(createSentence()+" ");
        }

        return toUpperCase(loremIpsum.toString().trim());
    }

    private String toUpperCase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public String getUsedLanguage() {
        return words.getUsedLanguage();
    }

    public Words getWords() {
        return this.words;
    }

    private boolean isNoun(String word) {
        return nouns.contains(word);
    }

    private boolean isVerb(String word) {
        return verbs.contains(word);
    }

    private boolean isAdjective(String word) {
        return adjectives.contains(word);
    }

    private boolean isArticle(String word) {
        return new ArrayList<String>(articles).contains(word);
    }

    private boolean isConjunction(String word) {
        return conjunctions.contains(word);
    }

    private void log(String s) {
        System.out.println(s);
    }

    private class GrammarParser {
        private RNG rng = new RNG();
        private char next;
        private String optional = "";
        private int pos = 0;
        private int pos_opt = 0;

        private final String GRAMMAR;

        GrammarParser(String grammar) {
            this.GRAMMAR = grammar;
        }

        char getNextGrammar() {
            if(!optional.equals("")) {
                if(pos_opt >= optional.length()) {
                    if((pos+1) >= GRAMMAR.length()) {
                        optional = "";
                        return getNextGrammar();
                    } else if(GRAMMAR.charAt(pos+1) != '*') {
                        optional = "";
                        return getNextGrammar();
                    } else if(rng.nextBoolean()) {
                        optional = "";
                        return getNextGrammar();
                    }
                    pos_opt = 0;
                }
                next = optional.charAt(pos_opt);
                pos_opt++;
            } else {
                if(!hasNext()) {
                    return 'e';
                }
                next = GRAMMAR.charAt(pos);
                if(next == '(') {
                    optional = GRAMMAR.substring(pos+1,GRAMMAR.indexOf(")",pos));
                    pos_opt = 0;
                    pos = GRAMMAR.indexOf(")",pos);
                    if(rng.nextBoolean()) {
                        optional = "";
                    }
                    return getNextGrammar();
                }
                pos++;
            }

            return next;
        }

        private boolean hasNext() {
            return pos < GRAMMAR.length();
        }

    }
}
