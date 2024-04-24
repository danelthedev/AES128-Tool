package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.SecureRandom;

public class AESInterface extends JFrame {
    private JTextArea inputTextArea, outputTextArea;
    private JTextField keyTextField, inputFileField, outputFileField;
    private JButton encryptButton, decryptButton, browseInputButton, browseOutputButton, generateButton;


    private AESEncryptor aesEncryptor = new AESEncryptor();
    private AESDecryptor aesDecryptor = new AESDecryptor();
    public AESInterface() {
        setTitle("AES Encryption/Decryption");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Direct String Encryption Components
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Input Text:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        inputTextArea = new JTextArea(10, 50);
        mainPanel.add(new JScrollPane(inputTextArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Output Text:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        outputTextArea = new JTextArea(10, 50);
        outputTextArea.setEditable(false);
        mainPanel.add(new JScrollPane(outputTextArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Key:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        keyTextField = new JTextField(50);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(keyTextField, gbc);

        // Add Generate button
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        generateButton = new JButton("Generate");
        mainPanel.add(generateButton, gbc);


        // File Encryption/Decryption Components
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Input File:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        inputFileField = new JTextField(50);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(inputFileField, gbc);

        gbc.gridx = 5;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        browseInputButton = new JButton("Browse Input");
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(browseInputButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Output File:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        outputFileField = new JTextField(50);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(outputFileField, gbc);

        gbc.gridx = 5;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        browseOutputButton = new JButton("Browse Output");
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(browseOutputButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 6;
        encryptButton = new JButton("Encrypt");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(encryptButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 6;
        decryptButton = new JButton("Decrypt");
        mainPanel.add(decryptButton, gbc);

        // Add the main panel to the frame
        add(mainPanel);
        pack();

        // Action Listeners
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputTextArea.getText();
                String key = keyTextField.getText();
                byte[] byteKey  = AES.hexStringToByteArr(key);
                String inputFile = inputFileField.getText();
                String outputFile = outputFileField.getText();
                if (!inputFile.isEmpty() && !outputFile.isEmpty()) {
                    File file = new File(inputFile);
                    try {
                        byte[] fileContent = Files.readAllBytes(file.toPath());
                        byte[] encryptedFileContent = aesEncryptor.cipher(fileContent, byteKey);

                        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                            fos.write(encryptedFileContent);
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                } else {
                    // Perform direct string encryption
                    byte[] encryptedBytes = aesEncryptor.cipher(input.getBytes(), byteKey);
                    outputTextArea.setText(AES.byteArrToHexString(encryptedBytes, false));
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputTextArea.getText();
                String key = keyTextField.getText();
                byte[] byteKey  = AES.hexStringToByteArr(key);
                String inputFile = inputFileField.getText();
                String outputFile = outputFileField.getText();
                if (!inputFile.isEmpty() && !outputFile.isEmpty()) {
                    File file = new File(inputFile);
                    try {
                        byte[] fileContent = Files.readAllBytes(file.toPath());
                        byte[] decryptedFileContent = aesDecryptor.decipher(fileContent, byteKey);

                        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                            fos.write(decryptedFileContent);
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                } else {
                    // Perform direct string decryption
                    byte[] decryptedWord = aesDecryptor.decipher(AES.hexStringToByteArr(input), byteKey);
                    outputTextArea.setText(AES.byteArrToString(decryptedWord));
                }
            }
        });

        browseInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    inputFileField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        browseOutputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    outputFileField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String generatedKey = generateKey();
                keyTextField.setText(generatedKey);
            }
        });
    }

    public static String generateKey() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            String hex = Integer.toHexString(random.nextInt(16));
            sb.append(hex);
        }
        sb.append(" ");
        for (int i = 0; i < 8; i++) {
            String hex = Integer.toHexString(random.nextInt(16));
            sb.append(hex);
        }
        sb.append(" ");
        for (int i = 0; i < 8; i++) {
            String hex = Integer.toHexString(random.nextInt(16));
            sb.append(hex);
        }
        sb.append(" ");
        for (int i = 0; i < 8; i++) {
            String hex = Integer.toHexString(random.nextInt(16));
            sb.append(hex);
        }
        return sb.toString().toUpperCase();
    }

}