import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FrequencyTable extends ArrayList<FrequencyList>{

    private ArrayList<Passage> passages;

    public FrequencyTable() {
        passages = new ArrayList<Passage>();
    }

    public static FrequencyTable buildTable(ArrayList<Passage> passages) {
        FrequencyTable table = new FrequencyTable();
        Set<String> allWords = new HashSet<>();
        table.passages = passages;
        for (Passage passage : passages) {
            allWords.addAll(passage.getWords());
        }
        for (String word : allWords){
            FrequencyList f = new FrequencyList(word, passages);
            table.add(f);
        }
        return table;
    }

    public void addPassage(Passage p){
        this.passages.add(p);
    }

    public int getFrequency(String word, Passage p){
        for (FrequencyList frequencyList : this) {
            if (frequencyList.getWord().equals(word)) {
                return (int) frequencyList.getFrequency(p);
            }
        }
        return 0;
    }
}
