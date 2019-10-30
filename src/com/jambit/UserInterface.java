package com.jambit;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Collections;
import java.util.Scanner;

class UserInterface {
    private Scanner input = new Scanner(System.in);
    private String pathName = "";
    private Charset utf8 = StandardCharsets.UTF_8;
    private String message;
    private String encryptionKey;
    private JFileChooser fileChooser = new JFileChooser();
    private FileNameExtensionFilter filterOpen = new FileNameExtensionFilter("Text Files", "txt");

    String getMessage() {
        return message;
    }

    private void setMessage(String messageToSet) {
        message = messageToSet;
    }

    String getEncryptionKey() {
        return encryptionKey;
    }

    private void setEncryptionKey(String encryptionKeyToSet) {
        encryptionKey = encryptionKeyToSet;
    }

    /**
     * starts the main menu of the encrypter
     */
    void ascii() throws Exception {
        System.out.println("  ______                             _            \n" +
                " |  ____|                           | |           \n" +
                " | |__   _ __   ___ _ __ _   _ _ __ | |_ ___ _ __ \n" +
                " |  __| | '_ \\ / __| '__| | | | '_ \\| __/ _ \\ '__|\n" +
                " | |____| | | | (__| |  | |_| | |_) | ||  __/ |   \n" +
                " |______|_| |_|\\___|_|   \\__, | .__/ \\__\\___|_|   \n" +
                "                          __/ | |                 \n" +
                "                         |___/|_|                 ");
        startMenu();
    }

    private void startMenu() throws Exception {
        System.out.println("\nChoose encryption method:");
        System.out.println("[1]Enter Message\n[2]Enter File");

        int menuChoice = input.nextInt();

        switch (menuChoice) {
            case 1:
                userInputMessage();
                break;
            case 2:
                textFileMessage();

                break;
            default:
                System.out.println("Invalid Input");
                Thread.sleep(500);
                startMenu();
        }


    }

    /**
     * method to open the GUI file chooser
     *
     * @return returns the choosen file path
     */
    private String openFileChooserOpen() throws Exception {
        try {
            fileChooser.setFileFilter(filterOpen);
            fileChooser.showOpenDialog(null);
            return fileChooser.getSelectedFile().getAbsolutePath();
        } catch (NullPointerException e) {
            System.err.println("\nAction cancelled!");
            startMenu();
            return null;
        }
    }

    private String openFileChooserSave() throws Exception {
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        try {
            fileChooser.setFileFilter(filterOpen);
            fileChooser.showOpenDialog(null);
            return fileChooser.getSelectedFile().getAbsolutePath();
        } catch (NullPointerException e) {
            System.err.println("\nAction cancelled!");
            startMenu();
            return null;
        }
    }

    /**
     * reads out a textfile and saves its content into a string
     *
     * @param fileName input by the file chooser
     * @return returns the string
     */
    private String readFileAsString(String fileName) throws IOException {
        String data;
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    void fileWriterBrowser(String message) throws Exception {
        pathName = openFileChooserSave();
        writeToFile(message);
    }

    /**
     * creates a file and overrides existing ones
     *
     * @param encryptedMessage defines string to write to file
     */

    void writeToFile(String encryptedMessage) throws Exception {
        File file = new File(pathName + "/encrypted_file.txt");
        try {
            if (file.createNewFile()) {
                System.out.println("\nFile Created\n");
            } else {
                System.err.println("File already exists!\n");
                System.out.println("Do you want to override it? \n[Y]/[N]");
                String yesNo = input.next();
                if (yesNo.equals("Y") || yesNo.equals("y")) {
                    if (file.delete()) {
                        Thread.sleep(1000);
                        writeToFile(encryptedMessage);
                    } else {
                        System.err.println("Unable to delete!");
                        Thread.sleep(500);
                        startMenu();
                    }
                } else {
                    startMenu();
                }

            }
        } catch (IOException | InterruptedException ignored) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        Files.write(Paths.get(pathName + "/encrypted_file.txt"), Collections.singleton(encryptedMessage), utf8);


    }

    /**
     * scanner for the custom user input
     */
    private void userInputMessage() {
        String enterMessage;
        System.out.println("Enter your message:");
        input.nextLine();
        enterMessage = input.nextLine();
        setMessage(enterMessage);
        System.out.println("Enter your Encryption Key or leave it blank to randomize it: ");
        System.out.println("Use this format: [xxx:xxx]");
        String keyMessage;
        keyMessage = input.nextLine();
        setEncryptionKey(keyMessage);
    }

    /**
     * opens the file chooser and sets the users path
     */
    private void textFileMessage() throws Exception {
        String path;
        path = openFileChooserOpen();
        path = path.replaceAll("[\\u202A]", "");
        System.out.println(path);
        try {
            setMessage(readFileAsString(path));
        } catch (NoSuchFileException e) {
            System.err.println("No textfile found under that path");
            Thread.sleep(500);
            startMenu();
        } catch (InvalidPathException e) {
            System.err.println("Invalid Path Exception");
            System.out.println("Enter a valid path!");
            Thread.sleep(500);
            startMenu();
        } catch (IOException e) {
            System.err.println("IO error occured: " + e);
            System.out.println(e);
            Thread.sleep(500);
            startMenu();
        }
        System.out.println("Enter your Encryption Key or enter 0 to randomize it: ");
        System.out.println("Use this format: [xxx:xxx]");
        String keyFile;
        input.nextLine();
        keyFile = input.nextLine();

        setEncryptionKey(keyFile);
    }


}
