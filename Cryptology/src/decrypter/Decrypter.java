package decrypter;

import custom.CustomMethod;
import custom.HashMapCustom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static custom.CustomMethod.customCharAt;

public class Decrypter {
    // Оголошення масиву
    private char[][] polybiusSquare;


    public Decrypter() {
        // Ініціалізувати квадрат Полібія
        polybiusSquare = new char[][]{
                {'А', 'Б', 'В', 'Г', 'Д', 'Е'},
                {'Є', 'Ж', 'З', 'И', 'І', 'Ї'},
                {'Й', 'К', 'Л', 'М', 'Н', 'О'},
                {'П', 'Р', 'С', 'Т', 'У', 'Ф'},
                {'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ь'},
                {'Ю', 'Я', ' ', ' ', ' ', ' '}
        };
    }
    public void decrypt(String inputFile, String outputFile) throws IOException {
        // Зчитування ключів та зашифрованого тексту
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        // Зчитуємо перший рядок файлу
        String encryptedText = reader.readLine();
        // Тут буде міститись відображення між символами та їх позиціями в перемішаному квадраті Полібія
        //РashMapCustom<Character, List<Integer>> shuffledCharacterToPositionMap = new HashMapCustom<>();
        HashMap<Character, List<Integer>> shuffledCharacterToPositionMap = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null) {
            //String[] parts = CustomMethod.customSplit(line, ": ");
            String[] parts = line.split(": ");
            // Отримуємо символ, який зберігається в першій частині рядка parts
            char c = parts[0].charAt(0);
            //char c = customCharAt(parts[0], 0);
            //Розбиваємо другу частину рядка parts за допомогою пробілу на два числа та створюємо List з цих чисел
            List<Integer> pos = Arrays.asList(
                    Integer.parseInt(parts[1].split(" ")[0]),
                    Integer.parseInt(parts[1].split(" ")[1]));
            //List<Integer> pos = Arrays.asList(
                   // Integer.parseInt(CustomMethod.customSplit(parts[1], " ")[0]),
                    //Integer.parseInt(CustomMethod.customSplit(parts[1], " ")[1]));
            shuffledCharacterToPositionMap.put(c, pos);
        }
        reader.close();

        // Розшифрування повідомлення
        StringBuilder decryptedText = new StringBuilder();
        int count=0;
        for (int i = 0; i < encryptedText.length(); i += 2) {
            //Зчитуємо з зашифрованого тексту координати символа зі стрічки
            int row = Character.getNumericValue(encryptedText.charAt(i));
            int col = Character.getNumericValue(encryptedText.charAt(i + 1));
            //int row = Character.getNumericValue(customCharAt(encryptedText, i));
            //int col = Character.getNumericValue(customCharAt(encryptedText, i + 1));
            //Символ(буква) за координатами, які нам потрібні
            char c = polybiusSquare[row][col];
            //Отримуємо позицію зашифрованого символа в
            //pos зі shuffledCharacterToPositionMap, де ключем є розшифрований символ.
            List<Integer> pos = shuffledCharacterToPositionMap.get(c);
            //decryptedText.append(c);
            decryptedText.append(CustomMethod.registerCheck(c,count));
            count++;
        }

        // Запис розшифрованого тексту
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
        writer.write(decryptedText.toString());
        writer.close();
    }
}
