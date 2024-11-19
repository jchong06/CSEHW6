import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The Passage class represents a text passage and provides functionality for parsing,
 * counting word frequencies, and calculating similarity with other passages.
 */
public class Passage {

    private String title;
    private int wordCount;
    private HashMap<String, Double> similarTitles;
    private HashMap<String, Integer> frequencyLists;
    private Set<String> set;
    private Set<String> words;

    /**
     * Constructs a Passage object by parsing a given file and loading stop words.
     *
     * @param title the title of the passage
     * @param file  the file containing the text of the passage
     */
    public Passage(String title, File file) {
        this.title = title;
        wordCount = 0;
        set = new HashSet<>();
        frequencyLists = new HashMap<>();
        words = new HashSet<>();
        similarTitles = new HashMap<>();
        parseFile(file);
        loadStopWords();
    }

    /**
     * Parses the given text file to extract words and build a frequency list.
     *
     * @param file the file to be parsed
     */
    public void parseFile(File file) {
        loadStopWords();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                for (String word : words) {
                    String key = word.trim().toLowerCase().replaceAll("[^a-zA-Z]", "");
                    if (!key.isEmpty() && !set.contains(key)) {
                        frequencyLists.put(key, frequencyLists.getOrDefault(key, 0) + 1);
                        wordCount++;
                        this.words.add(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads stop words from the "StopWords.txt" file into a set.
     */
    private void loadStopWords() {
        try (BufferedReader reader = new BufferedReader(new FileReader("StopWords.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String key = line.trim();
                if (!key.isEmpty()) {
                    set.add(key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates the frequency of a given word in the passage.
     *
     * @param word the word to calculate the frequency for
     * @return the frequency of the word as a proportion of the total word count
     */
    public double getWordFrequency(String word) {
        return frequencyLists.containsKey(word) ? (double) frequencyLists.get(word) : 0;
    }

    /**
     * * @author Justin Chong
     *  * Email: justin.chong@stonybrook.edu
     *  * Student ID: 116143020
     *  * Recitation Number: CSE 214 R03
     *  * TA: Kevin Zheng
     * Calculates the similarity between this passage and another passage using cosine similarity.
     *
     * @param other the other passage to compare against
     */
    public static double calculateSimilarity(Passage p1, Passage p2) {
        double dotProd = 0;
        double thisNorm = 0;
        double otherNorm = 0;

        for (String word : p1.words) {
            double thisFrequency = p1.getWordFrequency(word);
            double otherFrequency = p2.getWordFrequency(word);
            if (p2.words.contains(word)) {
                dotProd += thisFrequency * otherFrequency;
            }
            thisNorm += thisFrequency * thisFrequency;
        }

        for (String word : p2.words){
            double otherFrequency = p2.getWordFrequency(word);
            otherNorm += otherFrequency * otherFrequency;
        }

        double similarity = dotProd / (Math.sqrt(thisNorm * otherNorm));
        return similarity;
    }

    public void setSimilarTitles(String title, Double similarity) {
        similarTitles.put(title, similarity);
    }

    /**
     * Returns a map of titles and their similarity scores that meet or exceed the specified threshold.
     *
     * @param sim the similarity threshold
     * @return a map of titles and similarity scores
     */
    public HashMap<String, Double> getSimilarTitles(double sim) {
        HashMap<String, Double> sims = new HashMap<>();
        for (Map.Entry<String, Double> entry : similarTitles.entrySet()) {
            if (entry.getValue() >= sim) {
                sims.put(entry.getKey(), entry.getValue());
            }
        }
        return sims.isEmpty() ? null : sims;
    }

    /**
     * Returns the total word count in the passage.
     *
     * @return the total word count
     */
    public int getWordCount() {
        return wordCount;
    }

    /**
     * Returns the words in the passage.
     *
     * @return the words
     */
    public Set<String> getWords() {
        return words;
    }

    /**
     * Returns the word frequency list as a map.
     *
     * @return the word frequency map
     */
    public HashMap<String, Integer> getFrequencyLists() {
        return frequencyLists;
    }

    /**
     * Returns the title of the passage.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns a formatted string representation of the passage and its similarity results.
     *
     * @return a formatted string representation
     */
    public String toString() {
        String similar = "";
        int count = 0;
        for (Map.Entry<String, Double> entry : similarTitles.entrySet()) {
            count++;
            String title = entry.getKey().replace(".txt", "");
            double similarity = entry.getValue() * 100;
            similar += title + "(" + Math.round(similarity) + "%), ";
            if (count == 2) {
                similar += "\n                         | ";
            }
        }
        if (!similar.isEmpty()) {
            similar = similar.substring(0, similar.length() - 2);
        }
        String result =
                title.replace(".txt", "") + " ".repeat(25 - (title.length() - 4)) + "| " + similar +
                        "\n--------------------------------------------------------------------------------";
        return result;
    }
}
