package com.mugenico.languageGenerator.generators;

import com.mugenico.languageGenerator.generators.Words;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    private Words words = new Words();


    // Define some grammar patterns for later use.
    // TODO: Actually use  these patterns
    private static final String GRAMMAR_PATTERN_A = "SPO(PO)*";
    private static final String GRAMMAR_PATTERN_J = "(OP)S(OP)OP";
    private static final String GRAMMAR_PATTERN_S = "SPO(SOP)";


    // indefinite length
    private List<String> nouns = new ArrayList<>();
    // indefinite length
    private List<String> verbs = new ArrayList<>();
    // indefinite length
    private List<String> adjectives = new ArrayList<>();
    // length should be 0~4 on this for now
    private String[] articles = new String[4];
    private int articleCount = 0;
    // should not get too long, as most languages don't know nearly as many conjunctions as nouns
    private List<String> conjunctions = new ArrayList<>();


    // default constructor to prefill some nouns, verbs and adjectives
    public Sentences() {
        Random rng = new Random();

        // we create 100 words
        for(int i = 0; i<=50; i++) {
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
            if(!next.matches("[a-z]*")) {
                nouns.add(next);
            } else if(rng.nextBoolean()) {
                verbs.add(next);
            } else {
                adjectives.add(next);
            }
        }

        // Creating 0-4 articles to use in our language
        for(int i = 0; i<=3; i++) {

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
            articles[i] = next;
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

            // Create at least 10 conjunctions, then check for more
            if(rng.nextBoolean() && conjunctions.size()>=10) {
                break;
            }
        }

    }


    private boolean wordExists(String word) {
        List<String> allWords = new ArrayList<>();
        allWords.addAll(nouns);
        allWords.addAll(verbs);
        allWords.addAll(adjectives);
        allWords.addAll(conjunctions);
        Collections.addAll(allWords, articles);

        return allWords.contains(word);
    }

    public String createSentence() {
        Random rng = new Random();

        StringBuilder sentence = new StringBuilder();

        sentence.append(nouns.get(rng.nextInt(nouns.size())));
        sentence.append(" ");
        sentence.append(verbs.get(rng.nextInt(verbs.size())));
        sentence.append(" ");
        if(articleCount != 0) {
            sentence.append(articles[rng.nextInt(articleCount)]);
            sentence.append(" ");
        }
        if(rng.nextBoolean()) {
            sentence.append(adjectives.get(rng.nextInt(adjectives.size())));
            sentence.append(" ");
        }
        sentence.append(nouns.get(rng.nextInt(nouns.size())));
        if(rng.nextBoolean()) {
            sentence.append(", ");
            sentence.append(conjunctions.get(rng.nextInt(conjunctions.size())));
            sentence.append(" ");
            sentence.append(nouns.get(rng.nextInt(nouns.size())));
            sentence.append(" ");
            sentence.append(verbs.get(rng.nextInt(verbs.size())));
            sentence.append(" ");
            if(articleCount != 0) {
                sentence.append(articles[rng.nextInt(articleCount)]);
                sentence.append(" ");
            }
            if(rng.nextBoolean()) {
                sentence.append(adjectives.get(rng.nextInt(adjectives.size())));
                sentence.append(" ");
            }
            sentence.append(nouns.get(rng.nextInt(nouns.size())));
            sentence.append(".");
        } else {
            sentence.append(".");
        }

        return sentence.toString();
    }

    public char getUsedLanguage() {
        return words.getUsedLanguage();
    }

}
