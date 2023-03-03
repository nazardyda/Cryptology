package encrypter;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Encrypter {
    //Оголошення змінних
    private char[][] polybiusSquare;
    private HashMapCustom<Character, List<Integer>> characterToPositionMap;
    private Scanner scanner = new Scanner(System.in);


    public Encrypter() {
        // Ініціалізувати квадрат Полібія та карту символів
        // Двовимірний масив polybiusSquare, який містить символи латинської абетки (A-Z), крім літери J
        polybiusSquare = new char[][]{
                {'А', 'Б', 'В', 'Г', 'Д', 'Е'},
                {'Є', 'Ж', 'З', 'И', 'І', 'Ї'},
                {'Й', 'К', 'Л', 'М', 'Н', 'О'},
                {'П', 'Р', 'С', 'Т', 'У', 'Ф'},
                {'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ь'},
                {'Ю', 'Я', ' ', ' ', ' ', ' '}
        };
        characterToPositionMap = new HashMapCustom<>();
        char c;
        List<Integer> pos;
        for (int row = 0; row < polybiusSquare.length; row++) {
            //У внутрішньому циклі масиву polybiusSquare проходиться кожен елемент
            // і записується в мапу characterToPositionMap як ключ разом зі списком його позицій
            for (int col = 0; col < polybiusSquare.length; col++) {
                c = polybiusSquare[row][col];
                pos = Arrays.asList(row, col);
                characterToPositionMap.put(c, pos);
            }
        }
        //Результат, ми маємо characterToPositionMap, який містить 25 ключів (A-Z)
        // зі списками їх позицій в масиві polybiusSquare
    }

    public void encrypt(String inputFile, String outputFile) throws IOException {
        // Зчитування вхідного файлу(рядок)
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String input = reader.readLine();
        reader.close();

        // Створити розташування символів алфавіту випадковим чином
        //Random rand = new Random();
        System.out.print("Input SHIFT: ");
        Integer shift = scanner.nextInt();
        List<Character> alphabet = new ArrayList<>();
        for (int i = 0; i < polybiusSquare.length; i++) {
            for (int j = 0; j < polybiusSquare[i].length; j++) {
                alphabet.add(polybiusSquare[i][j]);
            }
        }
        //Перемішування списку alphabet за допомогою методу shuffle з класу Collections і передання
        // параметром об'єкту rand класу Random.
        // Результатом цієї операції є рандомне переставлення всіх елементів списку
        //Collections.rotate(alphabet, shift);
        //CustomMethod.shuffle(alphabet,rand);
        CustomMethod.rotate(alphabet, shift);

        // Створити мапу символів алфавіту до їх розташування в квадраті Полібія

        //Для збереження перемішаної позиції символу зі стандартного квадрату Полібія.
        Map<Character, List<Integer>> shuffledCharacterToPositionMap = new HashMap<>();
        for (int i = 0; i < alphabet.size(); i++) {
            char c = alphabet.get(i);
            //витягує позицію символу зі стандартного квадрату Полібія
            List<Integer> pos = characterToPositionMap.get(c);
            //додаєм символ та його перемішану позицію
            shuffledCharacterToPositionMap.put(c, pos);
        }

        // Шифрування повідомлення
        //Зберігання зашифрованого тексту
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            char uppercaseC = Character.toUpperCase(c);
            //char c = CustomMethod.customCharAt(input,i);
            //шукаємо позицію символу у перемішаному алфавіті
            List<Integer> pos = shuffledCharacterToPositionMap.getOrDefault(Character.toUpperCase(uppercaseC), null);
            //Якщо позиція символу була знайдена, додаємо координати символу до змінної
            if (pos != null) {
                encryptedText.append(pos.get(0));
                encryptedText.append(pos.get(1));
            }
        }

        // Запис зашифрованого тексту та ключі
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
        writer.write(encryptedText.toString());
        writer.newLine();
        for (char c : alphabet) {
            List<Integer> pos = shuffledCharacterToPositionMap.get(c);
            if (pos != null) {
                writer.write(c + ": " + pos.get(0) + " " + pos.get(1));
                writer.newLine();
            }
        }
        writer.close();
    }
}