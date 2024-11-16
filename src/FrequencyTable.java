import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * * @author Justin Chong
 *  * Email: justin.chong@stonybrook.edu
 *  * Student ID: 116143020
 *  * Recitation Number: CSE 214 R03
 *  * TA: Kevin Zheng
 * The FrequencyTable class represents a table that tracks word frequencies across multiple passages.
 * It extends ArrayList to hold FrequencyList objects, which contain word frequencies for each passage.
 */
public class FrequencyTable extends ArrayList<FrequencyList> {

    private ArrayList<Passage> passages;

    /**
     * Constructs an empty FrequencyTable.
     */
    public FrequencyTable() {
        passages = new ArrayList<>();
    }

    /**
     * Builds a FrequencyTable from a given list of passages. It constructs FrequencyList objects
     * for each unique word found across all the passages and adds them to the table.
     *
     * @param passages The list of Passage objects to build the frequency table from.
     * @return A FrequencyTable containing FrequencyList objects for all unique words.
     */
    public static FrequencyTable buildTable(ArrayList<Passage> passages) {
        FrequencyTable table = new FrequencyTable();
        Set<String> allWords = new HashSet<>();
        table.passages = passages;

        // Collect all unique words from all passages
        for (Passage passage : passages) {
            allWords.addAll(passage.getWords());
        }

        // Create and add FrequencyList for each unique word
        for (String word : allWords) {
            FrequencyList f = new FrequencyList(word, passages);
            table.add(f);
        }
        return table;
    }

    /**
     * Adds a new passage to the frequency table.
     *
     * @param p The Passage object to add.
     */
    public void addPassage(Passage p) {
        this.passages.add(p);
    }

    /**
     * Retrieves the frequency of a specific word in a given passage.
     *
     * @param word The word for which the frequency is being retrieved.
     * @param p The Passage in which the word frequency is being checked.
     * @return The frequency of the word in the passage as an integer, or 0 if not found.
     */
    public int getFrequency(String word, Passage p) {
        for (FrequencyList frequencyList : this) {
            if (frequencyList.getWord().equals(word)) {
                return (int) frequencyList.getFrequency(p);
            }
        }
        return 0;
    }
}
