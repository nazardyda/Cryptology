package custom;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomMethod {
    public static Character registerCheck(char c, int i) throws IOException {
        String text = getText();
        String temp = " ";
        if(i<text.length()){
            temp = text.substring(i,i+1);
            if(temp.equals(Character.toString(c))){
                return c;
            }
        }
        return Character.toLowerCase(c);
    }

    public static String[] customSplit(String str, String delimiter) {
        List<String> substrings = new ArrayList<>();
        int start = 0;
        int end = str.indexOf(delimiter);

        while (end != -1) {
            String substr = str.substring(start, end);//1а частина
            substrings.add(substr);
            start = end + delimiter.length();
            end = str.indexOf(delimiter, start);
        }

        String lastSubstr = str.substring(start);//2а частина
        substrings.add(lastSubstr);

        String[] result = new String[substrings.size()];
        result = substrings.toArray(result);

        return result;
    }
    public static char customCharAt(String str, int index){
        if (index < 0 || index >= str.length()) {
            throw new StringIndexOutOfBoundsException(index);
        }
        char[] chars = str.toCharArray();
        return chars[index];
    }
    public static void customAdd(List list, Object element) {
        int size = list.size();
        Object[] newArray = new Object[size + 1];//+1 бо хочемо добавити новий елемент
        for (int i = 0; i < size; i++) {
            newArray[i] = list.get(i);
        }
        newArray[size] = element;
        list.clear();
        for (Object e : newArray) {
            list.add(e);
        }
    }
    public static void shuffle(List<Character> alphabet, Random rand) {
        // масив символів з alphabet
        List<Character> chars = alphabet;
        for (int i = 0; i < chars.size(); i++) {
            //для кожної позиції масиву генерує випадкове число j
            int j = rand.nextInt(chars.size());
            //буль
            char temp = chars.get(i);
            chars.set(i, chars.get(j));
            chars.set(j, temp);
        }
    }
    public static void rotate(List<Character> alphabet, Integer shift) {
        // обрізаємо значення зсуву, якщо воно більше за розмір списку
        shift = shift % alphabet.size();

        // якщо зсув дорівнює 0 або розміру списку, немає потреби виконувати зсув
        if (shift == 0 || shift == alphabet.size()) {
            return;
        }

        // створюємо копію списку
        List<Character> rotated = new ArrayList<>(alphabet);

        // видаляємо перші shift елементів з початку копії
        for (int i = 0; i < shift; i++) {
            rotated.remove(0);
        }

        // додаємо видалені елементи в кінець копії
        rotated.addAll(alphabet.subList(0, shift));

        // копіюємо елементи копії назад в оригінальний список
        for (int i = 0; i < alphabet.size(); i++) {
            alphabet.set(i, rotated.get(i));
        }
    }
    private static String getText() throws IOException {
        String inputFile = "input.txt";
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String text = reader.readLine();
        reader.close();
        return text;
    }

}
