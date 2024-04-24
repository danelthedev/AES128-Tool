package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.*;
import java.awt.*;
import java.nio.file.Files;
import javax.swing.*;

public class Main {

    public static void userGivenStringDemo(){
        byte[] word = "Ana Are Mer".getBytes();

        byte[] key  = AES.hexStringToByteArr("2B7E1516 28AED2A6 ABF71588 09CF4F3C");

        System.out.println("Original word:\t" + AES.byteArrToHexString(word, true));

        AESEncryptor aesEncryptor = new AESEncryptor();
        AESDecryptor aesDecryptor = new AESDecryptor();

        byte[] encryptedWord = aesEncryptor.cipher(word, key);
        System.out.println("Encrypted word:\t" + AES.byteArrToHexString(encryptedWord, true));

        byte[] decryptedWord = aesDecryptor.decipher(encryptedWord, key);
        System.out.println("Decrypted word:\t" + AES.byteArrToHexString(decryptedWord, true));

        System.out.println(AES.byteArrToString(decryptedWord));
    }

    public static void fileEncryptionDemo(){
        AESEncryptor aesEncryptor = new AESEncryptor();
        AESDecryptor aesDecryptor = new AESDecryptor();
        byte[] key  = AES.hexStringToByteArr("2B7E1516 28AED2A6 ABF71588 09CF4F3C");

        ClassLoader classLoader = Main.class.getClassLoader();
        File file = new File(classLoader.getResource("TestFiles/testImg.jpeg").getFile());

        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            byte[] encryptedFileContent = aesEncryptor.cipher(fileContent, key);

            try (FileOutputStream fos = new FileOutputStream("EncryptedImg.jpeg")) {
                fos.write(encryptedFileContent);
            }

            byte[] decryptedFileContent = aesDecryptor.decipher(encryptedFileContent, key);

            try (FileOutputStream fos = new FileOutputStream("DecryptedImg.jpeg")) {
                fos.write(decryptedFileContent);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void UIDemo(){
        AESInterface aesInterface = new AESInterface();
        aesInterface.setVisible(true);
    }

    public static void main(String[] args) {
//        userGivenStringDemo();
//        fileEncryptionDemo();
        UIDemo();
    }
}