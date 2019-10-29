package com.jambit;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class UserInterface {
    Scanner input = new Scanner(System.in);
    private String message;
    private String encryptionKey;

    public String getMessage() {
        return message;
    }

    public void setMessage(String messageToSet) {
        message = messageToSet;
    }
    public String getEncryptionKey(){
        return encryptionKey;
    }
    public void setEncryptionKey(String encryptionKeyToSet){
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
                setMessage(readFileAsString(path));
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

    }
    public String readFileAsString(String fileName)throws Exception
    {
        String data = "";

        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

}
