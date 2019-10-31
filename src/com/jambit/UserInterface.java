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
    private String fileName;
    private Scanner input;
    private Charset charsetUTF8;
    private String message;
    private String encryptionKey;
    private JFileChooser fileChooser;
    private FileNameExtensionFilter filterOpen = new FileNameExtensionFilter("Text Files", "txt");
    private FileNameExtensionFilter filterSave = new FileNameExtensionFilter("Directories", "/");

    UserInterface() {
        input = new Scanner(System.in, "UTF-8");
        fileChooser = new JFileChooser();
        charsetUTF8 = StandardCharsets.UTF_8;
    }

    String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    String getEncryptionKey() {
        return encryptionKey;
    }

    private void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    /**
     * starts the main menu of the encrypter
     */
    void opensTitleScreen() throws Exception {
        System.out.println("  ______ _                    _____          _           \n" +
                " |  ____(_)                  / ____|        | |          \n" +
                " | |__   _ _ __   _____  __ | |     ___   __| | ___  ___ \n" +
                " |  __| | | '_ \\ / _ \\ \\/ / | |    / _ \\ / _` |/ _ \\/ __|\n" +
                " | |    | | | | |  __/>  <  | |___| (_) | (_| |  __/ (__ \n" +
                " |_|    |_|_| |_|\\___/_/\\_\\  \\_____\\___/ \\__,_|\\___|\\___|\n" +
                "                                                         ");
        printMenu();
    }

    private void printMenu() throws Exception {
        System.out.println("\nChoose encryption method:");
        System.out.println("[1]Enter Message\n[2]Enter File");

        String menuChoice = input.next();

        switch (menuChoice) {
            case "1":
                enterACustomMessage();
                break;
            case "2":
                chooseATextFile();
                break;
            default:
                System.err.println("Invalid input");
                Thread.sleep(500);
                printMenu();
        }


    }

    /**
     * method to open the GUI file chooser
     * Can't use the same Chooser cause different Filters
     *
     * @return returns the choosen file path
     */
    private String fileChooserOpen() throws Exception {
        fileChooser.setApproveButtonText("Open");
        fileChooser.setDialogTitle("Open");
        try {
            fileChooser.setFileFilter(filterOpen);
            fileChooser.showOpenDialog(null);
            return fileChooser.getSelectedFile().getAbsolutePath();
        } catch (NullPointerException e) {
            System.err.println("\nAction cancelled!");
            Thread.sleep(500);
            printMenu();
            return null;
        }
    }

    private String fileChooserSave() throws Exception {
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setApproveButtonText("Save");
        fileChooser.setDialogTitle("Save As");
        try {
            fileChooser.setFileFilter(filterSave);
            fileChooser.showOpenDialog(null);
            return fileChooser.getSelectedFile().getAbsolutePath() + "/";
        } catch (NullPointerException e) {
            System.err.println("\nAction cancelled!");
            Thread.sleep(500);
            printMenu();
            return null;
        }
    }

    /**
     * @param fileName path of the file
     * @return returns the files content
     */
    private String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    void saveFileChooser(String message) throws Exception {
        writeToFile(message, fileChooserSave());
    }

    /**
     * creates a file and overrides existing ones
     *
     * @param encryptedMessage defines string to write to file
     */

    private void writeToFile(String encryptedMessage, String path) throws Exception {
        System.out.println("Enter a filename: ");
        fileName = input.nextLine() + ".txt";
        File file = new File(path + fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("\nFile created\n");
            } else {
                System.err.println("File already exists!\n");
                System.out.println("Do you want to override it? \n[Y]/[N]");
                String yesNo = input.next();
                if (yesNo.equals("Y") || yesNo.equals("y")) {
                    if (file.delete()) {
                        Thread.sleep(1000);
                        writeToFile(encryptedMessage, path);
                    } else {
                        System.err.println("Unable to delete!");
                        Thread.sleep(500);
                        printMenu();
                    }
                } else {
                    printMenu();
                }

            }
        } catch (IOException | InterruptedException ignored) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(path != null) {
            Files.write(Paths.get(path + fileName), Collections.singleton(encryptedMessage), charsetUTF8);
        }else {
            fileChooserSave();
        }
        System.exit(0);
    }

    /**
     * scanner for the custom user input
     */
    private void enterACustomMessage() {
        String enterMessage;
        System.out.println("Enter your message:");
        input.nextLine();
        enterMessage = input.nextLine();
        setMessage(enterMessage);
        System.out.println("Enter your encryption Key or leave it blank to randomize it: ");
        System.out.println("Use this format: [xxx:xxx]");
        String keyMessage;
        keyMessage = input.nextLine();
        setEncryptionKey(keyMessage);
    }

    /**
     * opens the file chooser and sets the users path
     */
    private void chooseATextFile() throws Exception {
        String path;
        path = fileChooserOpen();
        path = path.replaceAll("[\\u202A]", "");
        System.out.println(path);
        try {
            setMessage(readFileAsString(path));
        } catch (NoSuchFileException e) {
            System.err.println("No textfile found under that path");
            Thread.sleep(500);
            printMenu();
        } catch (InvalidPathException e) {
            System.err.println("Invalid Path Exception");
            System.out.println("Enter a valid path!");
            Thread.sleep(500);
            printMenu();
        } catch (IOException e) {
            System.err.println("IO error occured: " + e);
            System.out.println(e);
            Thread.sleep(500);
            printMenu();
        }
        System.out.println("Enter your encryption Key or enter 0 to randomize it: ");
        System.out.println("Use this format: [xxx:xxx]");
        String keyFile;
        input.nextLine();
        keyFile = input.nextLine();

        setEncryptionKey(keyFile);
    }


}
