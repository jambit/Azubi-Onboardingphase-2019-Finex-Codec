package com.jambit;

public class Core {
  public static void main(String[] args) throws Exception {
    UserInterface ui = new UserInterface();
    Encrypter encrypter = new Encrypter();
    ui.startMenu();
    String message = ui.getMessage();
    System.out.println(message);
    String encryptedMessage = encrypter.encrypt(message);
    System.out.println("Message: " + encryptedMessage);
    System.out.println("Key: " + encrypter.getEncryptionKey());
    ui.writeToFile(encryptedMessage);
  }
}
