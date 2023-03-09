import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrequencyAnalyzer {
    private final String language;
    private final String text;
    private HashMap<Character, Integer> frequencyTable = new HashMap<>();

    public FrequencyAnalyzer(String text, String language) {
        this.language = language;
        this.text = text;
    }

    public void analyze() {
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char letter = Character.toLowerCase(c);
                frequencyTable.put(letter, frequencyTable.getOrDefault(letter, 0) + 1);
            }
        }
    }


    public void writeFrequencyTableToFile(String filename, String deviation) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Frequency table for " + language + " language:\n");

            List<Map.Entry<Character, Integer>> entries = new ArrayList<>(frequencyTable.entrySet());
            entries.sort(Map.Entry.comparingByKey());

            for (Map.Entry<Character, Integer> entry : entries) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
            writer.write(deviation);

        } catch (IOException e) {
            System.out.println("Error writing frequency table to file");
            return;
        }
    }

    public double compareWithStandardTable() {
        HashMap<Character, Double> standardTable = getStandardFrequencyTable(language);
        double deviationSum = 0;
        int totalChars = 0;

        for (char letter : frequencyTable.keySet()) {
            int frequency = frequencyTable.get(letter);
            double standardFrequency = standardTable.getOrDefault(letter, 0.0);
            deviationSum += Math.abs(frequency / (double) text.length() - standardFrequency);
            totalChars += frequency;
        }

        return deviationSum / totalChars;
    }

    private HashMap<Character, Double> getStandardFrequencyTable(String language) {
        HashMap<Character, Double> standardTable = new HashMap<>();

        if (language.equals("English")) {
            standardTable.put('a', 0.08167);
            standardTable.put('b', 0.01492);
            standardTable.put('c', 0.02782);
            standardTable.put('d', 0.04253);
            standardTable.put('e', 0.12702);
            standardTable.put('f', 0.02228);
            standardTable.put('g', 0.02015);
            standardTable.put('h', 0.06094);
            standardTable.put('i', 0.06966);
            standardTable.put('j', 0.00153);
            standardTable.put('k', 0.00772);
            standardTable.put('l', 0.04025);
            standardTable.put('m', 0.02406);
            standardTable.put('n', 0.06749);
            standardTable.put('o', 0.07507);
            standardTable.put('p', 0.01929);
            standardTable.put('q', 0.00095);
            standardTable.put('r', 0.05987);
            standardTable.put('s', 0.06327);
            standardTable.put('t', 0.09056);
            standardTable.put('u', 0.02758);
            standardTable.put('v', 0.00978);
            standardTable.put('w', 0.02360);
            standardTable.put('x', 0.00150);
            standardTable.put('y', 0.01974);
            standardTable.put('z', 0.00074);
        } else if (language.equals("Ukrainian")) {
            standardTable.put('а', 0.073);
            standardTable.put('б', 0.017);
            standardTable.put('в', 0.050);
            standardTable.put('г', 0.015);
            standardTable.put('ґ', 0.001);
            standardTable.put('д', 0.034);
            standardTable.put('е', 0.094);
            standardTable.put('є', 0.007);
            standardTable.put('ж', 0.009);
            standardTable.put('з', 0.047);
            standardTable.put('и', 0.063);
            standardTable.put('і', 0.057);
            standardTable.put('ї', 0.006);
            standardTable.put('й', 0.009);
            standardTable.put('к', 0.034);
            standardTable.put('л', 0.045);
            standardTable.put('м', 0.032);
            standardTable.put('н', 0.068);
            standardTable.put('о', 0.094);
            standardTable.put('п', 0.023);
            standardTable.put('р', 0.049);
            standardTable.put('с', 0.050);
            standardTable.put('т', 0.062);
            standardTable.put('у', 0.040);
            standardTable.put('ф', 0.002);
            standardTable.put('х', 0.016);
            standardTable.put('ц', 0.008);
            standardTable.put('ч', 0.027);
            standardTable.put('ш', 0.009);
            standardTable.put('щ', 0.005);
            standardTable.put('ь', 0.031);
            standardTable.put('ю', 0.009);
            standardTable.put('я', 0.030);
        }
        return standardTable;
    }
        public static void main (String[] args){
            String englishFilePath = "englishText.txt";
            String ukrainianFilePath = "ukrainianText.txt";
            try {
                String englishText = new String(Files.readAllBytes(Paths.get(englishFilePath)));
                FrequencyAnalyzer englishAnalyzer = new FrequencyAnalyzer(englishText, "English");
                englishAnalyzer.analyze();
                double englishDeviation = englishAnalyzer.compareWithStandardTable();
                englishAnalyzer.writeFrequencyTableToFile("english_frequency_table.txt", "English deviation: " + englishDeviation);


                String ukrainianText = new String(Files.readAllBytes(Paths.get(ukrainianFilePath)));
                FrequencyAnalyzer ukrainianAnalyzer = new FrequencyAnalyzer(ukrainianText, "Ukrainian");
                ukrainianAnalyzer.analyze();
                double ukrainianDeviation = ukrainianAnalyzer.compareWithStandardTable();
                ukrainianAnalyzer.writeFrequencyTableToFile("ukrainian_frequency_table.txt", "Ukrainian deviation: " + ukrainianDeviation);

            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }

        }
    }

