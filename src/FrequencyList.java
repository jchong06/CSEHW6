import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author Justin Chong
 * Email: justin.chong@stonybrook.edu
 * Student ID: 116143020
 * Recitation Number: CSE 214 R03
 * TA: Kevin Zheng
 * The FrequencyList class tracks the frequency of a specific word across multiple passages.
 * It stores the frequencies and the passage indices where the word appears with a non-zero frequency.
 */
public class FrequencyList {

    private String word;
    private ArrayList<Double> frequencies;
    private Hashtable<String, Double> passageIndices;

    /**
     * Constructs a FrequencyList for a given word and a list of passages.
     *
     * @param word      the word whose frequency is being tracked
     * @param passages  the list of passages in which the word's frequency will be calculated
     */
    public FrequencyList(String word, ArrayList<Passage> passages) {
        this.word = word;
        this.frequencies = new ArrayList<>();
        this.passageIndices = new Hashtable<>();

        // Iterate over each passage to calculate the word frequency
        for (Passage passage : passages) {
            frequencies.add(passage.getWordFrequency(word));
            if (passage.getWordFrequency(word) > 0) {
                passageIndices.put(passage.getTitle(), passage.getWordFrequency(word));
            }
        }
    }

    /**
     * Adds a new passage to the FrequencyList and updates the frequencies and passage indices.
     *
     * @param p the passage to add
     */
    public void addPassage(Passage p) {
        frequencies.add(p.getWordFrequency(word));
        if (p.getWordFrequency(word) > 0) {
            passageIndices.put(p.getTitle(), p.getWordFrequency(word));
        }
    }

    /**
     * Retrieves the frequency of the word in a specific passage.
     *
     * @param p the passage to check
     * @return the frequency of the word in the passage, or 0 if the word doesn't appear in the passage
     */
    public double getFrequency(Passage p) {
        if (passageIndices.containsKey(p.getTitle())) {
            return passageIndices.get(p.getTitle());
        }
        return 0;
    }

    /**
     * Retrieves the indices of the passages where the word has a non-zero frequency.
     *
     * @return a Hashtable containing the titles of passages and their respective word frequencies
     */
    public Hashtable<String, Double> getPassageIndices() {
        return passageIndices;
    }

    /**
     * Retrieves the word being tracked in the FrequencyList.
     *
     * @return the word being tracked
     */
    public String getWord() {
        return word;
    }
}
