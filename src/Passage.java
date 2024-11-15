import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Passage {

    private String title;
    private int wordCount;
    private HashMap<String, Double> similarTitles;
    private HashMap<String, Integer> frequencyLists;
    private Set<String> set;
    private Set<String> words;

    public Passage(String title, File file){
        this.title = title;
        wordCount = 0;
        set = new HashSet<>();
        frequencyLists = new HashMap<>();
        words = new HashSet<>();
        similarTitles = new HashMap<>();
        parseFile(file);
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

    public void parseFile(File file){
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                for (String word : words) {
                    String key = word.trim().toLowerCase();
                    key = key.replaceAll("[^a-zA-Z]", "");

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

    public double getWordFrequency(String word){
        if (frequencyLists.containsKey(word)){
            return (double) frequencyLists.get(word);
        }
        return 0;
    }

    public Set<String> getWords(){
        return set;
    }

    public void calculateSimilarity(Passage other) {
        Set<String> allWords = new HashSet<>(this.words);
        double numerator = 0;
        double denominator1 = 0;
        double denominator2 =0;

        for (String word : allWords) {
            double thisFrequency = getWordFrequency(word);
            double otherFrequency = other.getWordFrequency(word);

            numerator += thisFrequency * otherFrequency;
            denominator1 += Math.pow(thisFrequency, 2);
            denominator2 += Math.pow(otherFrequency, 2);
        }

        double similarity = numerator / (Math.sqrt(denominator1) * Math.sqrt(denominator2));
        similarTitles.put(other.title, similarity);
    }

    public HashMap<String, Double> getSimilarTitles(double sim) {
        HashMap<String, Double> sims = new HashMap<>();
        for (Map.Entry<String, Double> entry : similarTitles.entrySet()) {
            if (entry.getValue() >= sim){
                sims.put(entry.getKey(), entry.getValue());
            }
        }
        if (sims.isEmpty()){
            return null;
        }
        return sims;
    }

    public int getWordCount(){
        return wordCount;
    }

    public HashMap<String, Integer> getFrequencyLists() {
        return frequencyLists;
    }

    public String getTitle() {
        return title;
    }

    public String toString(){
        String similar = "";
        int count = 0;
        for (Map.Entry<String, Double> entry : similarTitles.entrySet()) {
            count++;
            String title = entry.getKey();
            double similarity = entry.getValue() * 100;
            similar += title + "(" + Math.round(similarity) + "%),";
            if (count == 2){
                similar += "\n                         | ";
            }
        }
        if (!similar.isEmpty()) {
            similar = similar.substring(0, similar.length() - 1);
        }
        String result =
                title + " ".repeat(25 - title.length()) + "| " + similar +
                        "\n--------------------------------------------------------------------------------";
        return result;
    }

}
