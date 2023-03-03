package domain;

import decrypter.Decrypter;
import encrypter.Encrypter;

import java.io.IOException;

public class Dispatcher {
    public static void main(String[] args) {
        String inputFile = "input.txt";
        String encryptedFile = "encrypted.txt";
        String decryptedFile = "decrypted.txt";
        Encrypter encrypter = new Encrypter();
        Decrypter decrypter = new Decrypter();

        try {
            encrypter.encrypt(inputFile, encryptedFile);
            decrypter.decrypt(encryptedFile, decryptedFile);
            System.out.println("Encryption and decryption completed successfully!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}