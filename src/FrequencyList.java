import java.util.ArrayList;
import java.util.Hashtable;

public class FrequencyList {

    private String word;
    private ArrayList<Double> frequencies;
    private Hashtable<String, Double> passageIndices;

    public FrequencyList(String word, ArrayList<Passage> passages) {
        this.word = word;
        this.frequencies = new ArrayList<>();
        this.passageIndices = new Hashtable<>();

        for (Passage passage : passages) {
            frequencies.add(passage.getWordFrequency(word));
            if (passage.getWordFrequency(word) > 0) {
                passageIndices.put(passage.getTitle(), passage.getWordFrequency(word));
            }
        }
    }

    public void addPassage(Passage p) {
        frequencies.add(p.getWordFrequency(word));
        if (p.getWordFrequency(word) > 0) {
            passageIndices.put(p.getTitle(), p.getWordFrequency(word));
        }
    }

    public double getFrequency(Passage p) {
        if (passageIndices.containsKey(p.getTitle())){
            return passageIndices.get(p.getTitle());
        }
        return 0;
    }

    public Hashtable<String, Double> getPassageIndices() {
        return passageIndices;
    }

    public String getWord() {
        return word;
    }
}
