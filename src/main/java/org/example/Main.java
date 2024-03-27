package org.example;

import java.util.Arrays;
import java.util.Base64;

public class Main {
    public static void main(String[] args) {
        byte[] word = "1111111111111111111111111111111111111111111111111111111".getBytes();
        byte[] key  = "1111111111111111".getBytes();

        AESEncryptor aesEncryptor = new AESEncryptor();
        byte[] encryptedWord = aesEncryptor.cipher(word, key);
        // convert byte array to base 64 string
        String encryptedWordBase64 = Base64.getEncoder().encodeToString(encryptedWord);
        System.out.println("Encrypted word: " + encryptedWordBase64);

//        byte[] decryptedWord = aesEncryptor.decipher(encryptedWord, key);
//        // convert byte array to base 64 string
//        String decryptedWordBase64 = Base64.getEncoder().encodeToString(decryptedWord);
//        System.out.println("Decrypted word: " + decryptedWordBase64);

    }
}