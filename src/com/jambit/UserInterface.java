package com.jambit;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
    Scanner input = new Scanner(System.in);
    Encrypter encrypter = new Encrypter();
    File file = new File("res/encrypted_file.txt");
    Charset utf8 = StandardCharsets.UTF_8;
    private String message;
    private String encryptionKey;

    public String getMessage() {
        return message;
    }

    public void setMessage(String messageToSet) {
        message = messageToSet;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKeyToSet) {
        encryptionKey = encryptionKeyToSet;
    }

    void startMenu() throws Exception {
        System.out.println("  ______                             _            \n" +
                " |  ____|                           | |           \n" +
                " | |__   _ __   ___ _ __ _   _ _ __ | |_ ___ _ __ \n" +
                " |  __| | '_ \\ / __| '__| | | | '_ \\| __/ _ \\ '__|\n" +
                " | |____| | | | (__| |  | |_| | |_) | ||  __/ |   \n" +
                " |______|_| |_|\\___|_|   \\__, | .__/ \\__\\___|_|   \n" +
                "                          __/ | |                 \n" +
                "                         |___/|_|                 ");
        System.out.println("\nChoose encryption method:");
        System.out.println("[1]Enter Message\n[2]Enter File");

        int menuChoice = input.nextInt();
        try {
            switch (menuChoice) {
                case 1:
                    String enterMessage;
                    System.out.println("Enter your message:");
                    input.nextLine();
                    enterMessage = input.nextLine();
                    setMessage(enterMessage);
                    System.out.println("Enter your Encryption Key or enter 0 to randomize it: ");
                    System.out.println("Use this format: [xxx:xxx]");
                    String keyMessage;
                    keyMessage = input.nextLine();
                    setEncryptionKey(keyMessage);
                    break;
                case 2:
                    System.out.println("Enter your file Path: ");
                    String path;
                    input.nextLine();
                    path = input.next();
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
                    keyFile = input.next();

                    setEncryptionKey(keyFile);

                    break;
                default:
                    System.out.println("Invalid Input");
                    Thread.sleep(500);
                    startMenu();
            }
        } catch (InputMismatchException e) {
            System.err.println("Invalid input!");
            Thread.sleep(500);
            startMenu();
        }

    }

    public String readFileAsString(String fileName) throws IOException {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public void writeToFile(String encryptedMessage) throws IOException {
        try {
            if (file.createNewFile()) {
                System.out.println("File Created");
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
        } catch (IOException | InterruptedException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = encryptedMessage;
        Files.write(Paths.get("res/encrypted_file.txt"), Collections.singleton(str), utf8);


    }

}
