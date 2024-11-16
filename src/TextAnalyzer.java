import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * * @author Justin Chong
 *  * Email: justin.chong@stonybrook.edu
 *  * Student ID: 116143020
 *  * Recitation Number: CSE 214 R03
 *  * TA: Kevin Zheng
 * The TextAnalyzer class compares text files in a specified directory, calculating the similarity
 * between each pair of passages and identifying potentially similar authors based on the text content.
 */
public class TextAnalyzer {

    /**
     * The main method for the TextAnalyzer class. It reads text files from a specified directory,
     * calculates similarity percentages between texts, and identifies suspected matches with similar authors.
     *
     * @param args command-line arguments (not used in this case)
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        System.out.print("Enter the similarity percentage: ");
        double sim = input.nextDouble();
        input.nextLine();

        System.out.print("Enter the directory of a folder of text files: ");
        File directory = new File(input.nextLine());

        System.out.println("\nReading texts...");
        File[] files = directory.listFiles();
        ArrayList<Passage> passages = new ArrayList<>();

        for (File file : files) {
            if (!file.getName().equals("StopWords.txt")) {
                Passage p = new Passage(file.getName(), file);
                passages.add(p);
            }
        }

        ArrayList<HashMap> similar = new ArrayList<>();
        System.out.println("Text (title)             | Similarities (%)\n" +
                "--------------------------------------------------------------------------------");
        for (Passage pass : passages) {
            for (Passage p : passages) {
                if (!pass.getTitle().equals(p.getTitle())) {
                    pass.calculateSimilarity(p);
                }
            }
            similar.add(pass.getSimilarTitles(sim));
            System.out.println(pass);
        }
        System.out.println("Suspected Texts With Same Authors\n" +
                "--------------------------------------------------------------------------------");
        for (int i = 0; i < similar.size(); i++) {
            HashMap<String, Double> similarities = similar.get(i);
            if (similarities != null && !similarities.isEmpty()) {
                for (Map.Entry<String, Double> entry : similarities.entrySet()) {
                    String otherTitle = entry.getKey();
                    double similarityPercent = entry.getValue() * 100;
                    System.out.printf("'%s' and '%s' may have the same author (%.0f%% similar)%n",
                            passages.get(i).getTitle().replace(".txt", ""),
                            otherTitle.replace(".txt", ""),
                            similarityPercent);
                }
            }
        }
    }
}
